package com.buenSabor.BackEnd.services.stock;

import com.buenSabor.BackEnd.dto.stock.StockCheckRequest;
import com.buenSabor.BackEnd.dto.stock.StockCheckResponse;
import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturado;
import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import com.buenSabor.BackEnd.dto.stock.AddStockRequest;
import com.buenSabor.BackEnd.dto.stock.StockResponse;
import com.buenSabor.BackEnd.dto.stock.UpdateStockRequest;
import com.buenSabor.BackEnd.mapper.StockMapper;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.producto.HistoricoStockArticuloInsumo;
import com.buenSabor.BackEnd.repositories.producto.ArticuloInsumoRepository;
import com.buenSabor.BackEnd.repositories.producto.ArticuloManufacturadoRepository;
import com.buenSabor.BackEnd.repositories.producto.HistoricoStockArticuloInsumoRepository;
import com.buenSabor.BackEnd.repositories.producto.StockArticuloInsumoRepository;
import com.buenSabor.BackEnd.services.company.SucursalService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@Service
public class StockService {

    @Autowired
    private ArticuloInsumoRepository insumoRepository;

    @Autowired
    private ArticuloManufacturadoRepository manufacturadoRepository;

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private StockArticuloInsumoRepository stockRepository;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private HistoricoStockArticuloInsumoRepository historicoStockRepository;

    // --- MÉTODOS PARA PEDIDOS (Lógica Centralizada) ---

    @Transactional(readOnly = true)
    public StockCheckResponse validarStock(Map<Long, Integer> insumosNecesarios, Long sucursalId) {
        StockCheckResponse response = new StockCheckResponse(true, "Stock suficiente");

        for (Map.Entry<Long, Integer> entry : insumosNecesarios.entrySet()) {
            Long insumoId = entry.getKey();
            Integer cantidadRequerida = entry.getValue();

            StockArticuloInsumo stock = stockRepository.findByArticuloInsumo_IdAndSucursal_Id(insumoId, sucursalId);

            String nombreInsumo = insumoRepository.findById(insumoId)
                    .map(ArticuloInsumo::getNombre).orElse("Insumo ID " + insumoId);

            if (stock == null) {
                response.agregarProductoFaltante(insumoId, nombreInsumo, cantidadRequerida);
                response.setHayStockSuficiente(false);
            } else if (stock.getCantidad() < cantidadRequerida) {
                response.agregarProductoFaltante(insumoId, nombreInsumo, cantidadRequerida - stock.getCantidad());
                response.setHayStockSuficiente(false);
            }
        }

        if (!response.isHayStockSuficiente()) {
            response.setMensaje("No hay stock suficiente para realizar el pedido");
        }
        return response;
    }

    @Transactional
    public void descontarStock(Map<Long, Integer> insumosNecesarios, Long sucursalId) {
        // 1. ORDENAR LOS IDs PARA EVITAR DEADLOCKS
        // Convertimos las llaves a una lista y ordenamos de menor a mayor.
        List<Long> insumosOrdenados = new ArrayList<>(insumosNecesarios.keySet());
        Collections.sort(insumosOrdenados);

        for (Long insumoId : insumosOrdenados) {
            Integer cantidadADescontar = insumosNecesarios.get(insumoId);

            StockArticuloInsumo stock = stockRepository.findByArticuloInsumo_IdAndSucursal_Id(insumoId, sucursalId);

            if (stock != null) {
                int nuevoStock = stock.getCantidad() - cantidadADescontar;
                if (nuevoStock < 0) {
                    throw new RuntimeException("Stock negativo detectado al procesar insumo ID " + insumoId + ". Transacción abortada.");
                }
                stock.setCantidad(nuevoStock);

                if (nuevoStock < stock.getMinStock()) {
                    ArticuloInsumo insumo = stock.getArticuloInsumo();
                    insumo.setExiste(false);
                
                }

                // 2. Guardamos y CAPTURAMOS la instancia actualizada 
                StockArticuloInsumo stockActualizado = stockRepository.save(stock);

                // 3. Usamos el objeto actualizado para el histórico
                registrarHistorico(stockActualizado);

                log.info("Stock descontado. Insumo: {}, Sucursal: {}, Nuevo Stock: {}", insumoId, sucursalId, nuevoStock);
            } else {
                throw new EntityNotFoundException("El stock para el insumo " + insumoId + " en la sucursal " + sucursalId + " no existe.");
            }
        }
    }

    @Transactional
    public void reponerStock(Map<Long, Integer> insumosADevolver, Long sucursalId) {
        // 1. ORDENAR LOS IDs PARA EVITAR DEADLOCKS
        List<Long> insumosOrdenados = new ArrayList<>(insumosADevolver.keySet());
        Collections.sort(insumosOrdenados);

        for (Long insumoId : insumosOrdenados) {
            Integer cantidadADevolver = insumosADevolver.get(insumoId);

            StockArticuloInsumo stock = stockRepository.findByArticuloInsumo_IdAndSucursal_Id(insumoId, sucursalId);

            if (stock != null) {
                int nuevoStock = stock.getCantidad() + cantidadADevolver;
                stock.setCantidad(nuevoStock);

                if (nuevoStock >= stock.getMinStock() && !stock.getArticuloInsumo().getExiste()) {
                    ArticuloInsumo insumo = stock.getArticuloInsumo();
                    insumo.setExiste(true);
                }

                // Capturamos el actualizado y registramos histórico
                StockArticuloInsumo stockActualizado = stockRepository.save(stock);
                registrarHistorico(stockActualizado);

                log.info("Stock repuesto. Insumo: {}, Cantidad: {}", insumoId, cantidadADevolver);
            } else {
                log.warn("Intento de devolver stock a un insumo sin registro en sucursal. ID: {}", insumoId);
            }
        }
    }

    // --- MÉTODOS DE GESTIÓN (CRUD y Manuales) ---

    @Transactional(readOnly = true)
    public List<StockResponse> findAll() {
        return stockRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<StockResponse> findAll(Pageable pageable) {
        return stockRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public StockResponse findById(Long id) {
        return stockRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("Stock no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<StockResponse> findBySucursalId(Long sucursalId) {
        return stockRepository.findBySucursalId(sucursalId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public StockResponse updateStock(UpdateStockRequest request) {
        StockArticuloInsumo stock = stockRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Stock no encontrado con ID: " + request.getId()));

        if (request.getCantidad() != null)
            stock.setCantidad(request.getCantidad());
        if (request.getStockMinimo() != null)
            stock.setMinStock(request.getStockMinimo());

        StockArticuloInsumo updatedStock = stockRepository.save(stock);
        registrarHistorico(updatedStock); 

        return convertToDto(updatedStock);
    }

    @Transactional
    public void deleteStock(Long id) {
        if (!stockRepository.existsById(id)) {
            throw new EntityNotFoundException("Stock no encontrado con ID: " + id);
        }
        stockRepository.deleteById(id);
    }

    // --- LÓGICA DE ACTUALIZACIÓN MANUAL (AGREGAR STOCK) ---

    private static final int MAX_RETRIES = 3;

    @Transactional
    public boolean agregarStock(AddStockRequest request) {
        int intento = 0;
        while (intento < MAX_RETRIES) {
            try {
                ArticuloInsumo insumo = insumoRepository.findById(request.getIdInsumo())
                        .orElseThrow(() -> new EntityNotFoundException("Insumo no encontrado con ID: " + request.getIdInsumo()));

                StockArticuloInsumo stock = stockRepository.findByArticuloInsumo_IdAndSucursal_Id(request.getIdInsumo(), request.getSucursalId());

                if (stock == null) {
                    stock = new StockArticuloInsumo();
                    stock.setCantidad(0);
                    stock.setMinStock(0);
                    stock.setArticuloInsumo(insumo);
                    Sucursal sucursal = sucursalService.findById(request.getSucursalId());
                    stock.setSucursal(sucursal);
                }

                double cantidadAAgregar = request.getCantidad();
                int nuevaCantidad = stock.getCantidad() + (int) Math.round(cantidadAAgregar);
                
                if (nuevaCantidad < 0) {
                    throw new IllegalStateException("Stock resultante no puede ser negativo.");
                }

                stock.setCantidad(nuevaCantidad);

                if (nuevaCantidad >= stock.getMinStock() && !insumo.getExiste()) {
                    insumo.setExiste(true);
                }

                // Guardamos Stock y Histórico (Capturando el objeto actualizado)
                StockArticuloInsumo stockActualizado = stockRepository.save(stock);
                registrarHistorico(stockActualizado);

                log.info("Stock actualizado (agregar). Insumo: {}, Sucursal: {}, Nuevo: {}", request.getIdInsumo(), request.getSucursalId(), nuevaCantidad);
                return true;

            } catch (OptimisticLockException e) {
                intento++;
                if (intento >= MAX_RETRIES)
                    throw new RuntimeException("Error concurrencia tras reintentos", e);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            } catch (Exception e) {
                log.error("Error al agregar stock", e);
                throw new RuntimeException("Error al actualizar stock: " + e.getMessage(), e);
            }
        }
        return false;
    }

    // --- LÓGICA DE VERIFICACIÓN PRE-CHECKOUT ---

    @Transactional(readOnly = true)
    public StockCheckResponse verificarStock(StockCheckRequest request) {
        StockCheckResponse response = new StockCheckResponse(true, "Stock suficiente para todos los productos");

        for (StockCheckRequest.ProductoCheck producto : request.getProductos()) {
            if ("INSUMO".equalsIgnoreCase(producto.getTipo())) {
                verificarStockInsumo(producto, response, request.getSucursalId());
            } else if ("MANUFACTURADO".equalsIgnoreCase(producto.getTipo())) {
                verificarStockManufacturado(producto, response, request.getSucursalId());
            }

            if (!response.isHayStockSuficiente()) {
                response.setMensaje("No hay stock suficiente para algunos productos");
            }
        }
        return response;
    }

    private void verificarStockInsumo(StockCheckRequest.ProductoCheck producto, StockCheckResponse response, Long sucursalId) {
        try {
            StockArticuloInsumo stock = stockRepository.findByArticuloInsumo_IdAndSucursal_Id(producto.getId(), sucursalId);
            ArticuloInsumo insumo = insumoRepository.findById(producto.getId()).orElse(null);
            String nombre = insumo != null ? insumo.getNombre() : "Desconocido";

            if (stock == null) {
                response.agregarProductoFaltante(producto.getId(), nombre, producto.getCantidad());
                response.setHayStockSuficiente(false);
                return;
            }

            if (stock.getCantidad() < producto.getCantidad()) {
                response.agregarProductoFaltante(producto.getId(), nombre, producto.getCantidad() - stock.getCantidad());
                response.setHayStockSuficiente(false);
            }
        } catch (Exception e) {
            log.error("Error verificando insumo", e);
            response.setHayStockSuficiente(false);
        }
    }

    private void verificarStockManufacturado(StockCheckRequest.ProductoCheck producto, StockCheckResponse response, Long sucursalId) {
        try {
            ArticuloManufacturado manufacturado = manufacturadoRepository.findById(producto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Manufacturado no encontrado"));

            if (!manufacturado.getSucursal().getId().equals(sucursalId)) {
                response.agregarProductoFaltante(manufacturado.getId(), manufacturado.getNombre() + " (Sucursal incorrecta)", producto.getCantidad());
                response.setHayStockSuficiente(false);
                return;
            }

            for (var detalle : manufacturado.getDetalleInsumos()) {
                ArticuloInsumo insumo = detalle.getArticuloInsumo();
                int cantidadNecesariaTotal = (int) Math.ceil(detalle.getCantidad() * producto.getCantidad());

                StockArticuloInsumo stockInsumo = stockRepository.findByArticuloInsumo_IdAndSucursal_Id(insumo.getId(), sucursalId);

                if (stockInsumo == null) {
                    response.agregarProductoFaltante(insumo.getId(), insumo.getNombre() + " (para " + manufacturado.getNombre() + ")", cantidadNecesariaTotal);
                    response.setHayStockSuficiente(false);
                } else if (stockInsumo.getCantidad() < cantidadNecesariaTotal) {
                    int faltante = cantidadNecesariaTotal - stockInsumo.getCantidad();
                    response.agregarProductoFaltante(insumo.getId(), insumo.getNombre() + " (para " + manufacturado.getNombre() + ")", faltante);
                    response.setHayStockSuficiente(false);
                }
            }
        } catch (Exception e) {
            log.error("Error verificando manufacturado", e);
            response.setHayStockSuficiente(false);
        }
    }

    private StockResponse convertToDto(StockArticuloInsumo stock) {
        if (stock == null) return null;
        try {
            return stockMapper.toDtoSafe(stock);
        } catch (Exception e) {
            log.error("Error converting Stock to DTO", e);
            return null;
        }
    }

    private void registrarHistorico(StockArticuloInsumo stock) {
        HistoricoStockArticuloInsumo historico = new HistoricoStockArticuloInsumo();
        historico.setCantidad(stock.getCantidad());
        historico.setFechaActualizacion(new Date());
        historico.setIdstockarticuloInsumo(stock);

        historicoStockRepository.save(historico);
    }
}