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
import com.buenSabor.BackEnd.repositories.producto.ArticuloInsumoRepository;
import com.buenSabor.BackEnd.repositories.producto.ArticuloManufacturadoRepository;
import com.buenSabor.BackEnd.repositories.producto.StockArticuloInsumoRepository;
import com.buenSabor.BackEnd.services.company.SucursalService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
public class StockService {

    @Autowired
    private ArticuloInsumoRepository insumoRepository; // Usar repositorio directamente suele ser mejor para evitar dependencias circulares

    @Autowired
    private ArticuloManufacturadoRepository manufacturadoRepository;

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private StockArticuloInsumoRepository stockRepository;

    @Autowired
    private StockMapper stockMapper;

    // --- MÉTODOS PARA PEDIDOS (Lógica Centralizada) ---

    /**
     * Valida si hay stock suficiente en una sucursal específica.
     * Usado por PedidoService antes de crear el pedido.
     */
    @Transactional(readOnly = true)
    public StockCheckResponse validarStock(Map<Long, Integer> insumosNecesarios, Long sucursalId) {
        StockCheckResponse response = new StockCheckResponse(true, "Stock suficiente");

        for (Map.Entry<Long, Integer> entry : insumosNecesarios.entrySet()) {
            Long insumoId = entry.getKey();
            Integer cantidadRequerida = entry.getValue();

            // FIX: Búsqueda específica por Sucursal e Insumo
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

    /**
     * Descuenta el stock. Se asume que la validación ya pasó.
     * Usado por PedidoService al guardar el pedido.
     */
    @Transactional
    public void descontarStock(Map<Long, Integer> insumosNecesarios, Long sucursalId) {
        for (Map.Entry<Long, Integer> entry : insumosNecesarios.entrySet()) {
            Long insumoId = entry.getKey();
            Integer cantidadADescontar = entry.getValue();

            // FIX: Búsqueda específica por Sucursal
            StockArticuloInsumo stock = stockRepository.findByArticuloInsumo_IdAndSucursal_Id(insumoId, sucursalId);

            if (stock != null) {
                int nuevoStock = stock.getCantidad() - cantidadADescontar;
                if (nuevoStock < 0) {
                     throw new RuntimeException("Stock negativo detectado al procesar insumo ID " + insumoId + ". Transacción abortada.");
                }
                stock.setCantidad(nuevoStock);

                // Lógica "Existe": Si baja del mínimo, marcar globalmente o alertar
                // Nota: insumo.setExiste(false) afecta a todas las sucursales si la entidad es compartida.
                // Si la lógica de negocio es así, mantenemos esto:
                if (nuevoStock < stock.getMinStock()) {
                    ArticuloInsumo insumo = stock.getArticuloInsumo();
                    insumo.setExiste(false);
                    insumoRepository.save(insumo);
                }

                stockRepository.save(stock);
                log.info("Stock descontado. Insumo: {}, Sucursal: {}, Nuevo Stock: {}", insumoId, sucursalId, nuevoStock);
            } else {
                // Esto es crítico: significa que entre la validación y el guardado, alguien borró el stock.
                throw new EntityNotFoundException("El stock para el insumo " + insumoId + " en la sucursal " + sucursalId + " no existe.");
            }
        }
    }

    // --- MÉTODOS DE GESTIÓN (CRUD) ---

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

        if (request.getCantidad() != null) stock.setCantidad(request.getCantidad());
        if (request.getStockMinimo() != null) stock.setMinStock(request.getStockMinimo());

        StockArticuloInsumo updatedStock = stockRepository.save(stock);
        return convertToDto(updatedStock);
    }

    @Transactional
    public void deleteStock(Long id) {
        if (!stockRepository.existsById(id)) {
            throw new EntityNotFoundException("Stock no encontrado con ID: " + id);
        }
        stockRepository.deleteById(id);
    }

    // --- LOGICA DE ACTUALIZACIÓN MANUAL (Corregida) ---

    private static final int MAX_RETRIES = 3;

    @Transactional
    public boolean agregarStock(AddStockRequest request) {
        int intento = 0;
        while (intento < MAX_RETRIES) {
            try {
                ArticuloInsumo insumo = insumoRepository.findById(request.getIdInsumo())
                        .orElseThrow(() -> new EntityNotFoundException("Insumo no encontrado con ID: " + request.getIdInsumo()));

                // FIX: Buscar stock existente PARA ESTA SUCURSAL ESPECÍFICA
                StockArticuloInsumo stock = stockRepository.findByArticuloInsumo_IdAndSucursal_Id(request.getIdInsumo(), request.getSucursalId());

                if (stock == null) {
                    // Crear nuevo stock para esta sucursal
                    stock = new StockArticuloInsumo();
                    stock.setCantidad(0);
                    stock.setMinStock(0);
                    stock.setArticuloInsumo(insumo);
                    Sucursal sucursal = sucursalService.findById(request.getSucursalId()); // Asegura que lanza excepción si no existe
                    stock.setSucursal(sucursal);
                    // No hacemos insumo.setStock... porque es una lista OneToMany, simplemente guardamos el stock
                }

                double cantidadAAgregar = request.getCantidad();
                // Conversión de unidad si aplica...

                int nuevaCantidad = stock.getCantidad() + (int) Math.round(cantidadAAgregar);
                if (nuevaCantidad < 0) {
                    throw new IllegalStateException("Stock resultante no puede ser negativo.");
                }

                stock.setCantidad(nuevaCantidad);

                if (nuevaCantidad >= stock.getMinStock() && !insumo.getExiste()) {
                    insumo.setExiste(true);
                    insumoRepository.save(insumo);
                }

                stockRepository.save(stock);
                log.info("Stock actualizado (agregar). Insumo: {}, Sucursal: {}, Nuevo: {}", request.getIdInsumo(), request.getSucursalId(), nuevaCantidad);
                return true;

            } catch (OptimisticLockException e) {
                intento++;
                if (intento >= MAX_RETRIES) throw new RuntimeException("Error concurrencia tras reintentos", e);
                try { Thread.sleep(100); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
            } catch (Exception e) {
                log.error("Error al agregar stock", e);
                throw new RuntimeException("Error al actualizar stock: " + e.getMessage(), e);
            }
        }
        return false;
    }

    // --- LÓGICA DE VERIFICACIÓN PRE-CHECKOUT (Corregida) ---
    // Este método suele usarse desde el Controller para que el Frontend valide antes de enviar el pedido

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
            // FIX: Usar el repo de stock, no el insumo directamente
            StockArticuloInsumo stock = stockRepository.findByArticuloInsumo_IdAndSucursal_Id(producto.getId(), sucursalId);
            
            // Para obtener el nombre, si buscamos el insumo
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

            // Verificar si el manufacturado pertenece a la sucursal (si aplica tu lógica de negocio)
            if (!manufacturado.getSucursal().getId().equals(sucursalId)) {
                // Dependiendo de tu lógica, esto puede ser error o simplemente que no se vende ahí
                response.agregarProductoFaltante(manufacturado.getId(), manufacturado.getNombre() + " (Sucursal incorrecta)", producto.getCantidad());
                response.setHayStockSuficiente(false);
                return;
            }

            for (var detalle : manufacturado.getDetalleInsumos()) {
                ArticuloInsumo insumo = detalle.getArticuloInsumo();
                int cantidadNecesariaTotal = (int) Math.ceil(detalle.getCantidad() * producto.getCantidad()); // Ojo con tipos de datos (Double vs Int)

                // FIX: Buscar stock del insumo EN LA SUCURSAL
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

    // Helper
    private StockResponse convertToDto(StockArticuloInsumo stock) {
        if (stock == null) return null;
        try {
            return stockMapper.toDtoSafe(stock);
        } catch (Exception e) {
            log.error("Error converting Stock to DTO", e);
            return null;
        }
    }
}