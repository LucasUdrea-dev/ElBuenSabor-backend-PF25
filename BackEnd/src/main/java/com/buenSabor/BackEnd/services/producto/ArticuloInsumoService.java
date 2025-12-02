package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.dto.producto.insumo.InsumoDTO;
import com.buenSabor.BackEnd.mapper.ArticuloMapper;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.HistoricoPrecioCostoArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.HistoricoStockArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.Subcategoria;
import com.buenSabor.BackEnd.models.producto.UnidadMedida;
import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import com.buenSabor.BackEnd.repositories.producto.ArticuloInsumoRepository;
import com.buenSabor.BackEnd.repositories.producto.SubcategorioRepository;
import com.buenSabor.BackEnd.repositories.producto.UnidadMedidaRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArticuloInsumoService extends BeanServiceImpl<ArticuloInsumo, Long> {
    @Autowired
    private ArticuloMapper articuloMapper;

    @Autowired
    private ArticuloInsumoRepository articuloInsumoRepository;

    @Autowired
    private SubcategorioRepository subcategorioRepository;

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private com.buenSabor.BackEnd.repositories.company.SucursalRepository sucursalRepository;

    public ArticuloInsumoService(ArticuloInsumoRepository articuloInsumoRepository) {
        super(articuloInsumoRepository);
        this.articuloInsumoRepository = articuloInsumoRepository;
    }

  @Transactional
    public ArticuloInsumo crearInsumo(InsumoDTO dto, Long sucursalId) {
        try {
            if (dto.getStockArticuloInsumo() == null) {
                throw new IllegalArgumentException("El stock del insumo es requerido");
            }
            if (sucursalId == null) {
                throw new IllegalArgumentException("El ID de la sucursal es requerido");
            }
            if (dto.getStockArticuloInsumo().getCantidad() < 0) {
                throw new IllegalArgumentException("El stock no puede ser negativo");
            }

            // 1. Mapeo DTO -> Entidad
            ArticuloInsumo insumo = articuloMapper.toEntity(dto);
            
            insumo.setId(null); 
            insumo.setExiste(true);
            insumo.setPrecioCompra(dto.getPrecioCompra());

            // 2. Resolver Relaciones Simples
            Subcategoria sub = subcategorioRepository.findById(dto.getSubcategoria().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Subcategoria no encontrada"));
            insumo.setSubcategoria(sub);

            UnidadMedida um = unidadMedidaRepository.findById(dto.getUnidadMedida().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Unidad de medida no encontrada"));
            insumo.setUnidadMedida(um);

            // 3. Creación y Vinculación del Stock Manualmente
            StockArticuloInsumo stock = new StockArticuloInsumo();
            stock.setCantidad(dto.getStockArticuloInsumo().getCantidad());
            stock.setMinStock(dto.getStockArticuloInsumo().getMinStock());

            Sucursal sucursal = sucursalRepository.findById(sucursalId)
                    .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada con ID: " + sucursalId));
            stock.setSucursal(sucursal);

            // Vinculación bidireccional segura
            insumo.setStockAndLink(stock);

            return articuloInsumoRepository.save(insumo);

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el insumo: " + e.getMessage(), e);
        }
    }

    @Transactional
    public ArticuloInsumo actualizar(Long id, InsumoDTO dto) {
        try {
            ArticuloInsumo insumo = articuloInsumoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Insumo con id " + id + " no existe"));

            // --- A. HISTÓRICO DE PRECIO ---
            Double precioAnterior = insumo.getPrecioCompra();
            Double precioNuevo = dto.getPrecioCompra();

            // 1. Actualizar campos básicos
            articuloMapper.updateFromDto(dto, insumo);
            insumo.setPrecioCompra(precioNuevo);

            // Guardar histórico si el precio cambió
            if (precioNuevo != null && !precioNuevo.equals(precioAnterior)) {
                HistoricoPrecioCostoArticuloInsumo historicoPrecio = new HistoricoPrecioCostoArticuloInsumo();
                historicoPrecio.setPrecio(precioNuevo);
                historicoPrecio.setFecha(new Date());
                historicoPrecio.setIdArticuloInsumo(insumo); // Relación con el padre

                if (insumo.getHistoricoPrecioCosto() == null) {
                    insumo.setHistoricoPrecioCosto(new ArrayList<>());
                }
                insumo.getHistoricoPrecioCosto().add(historicoPrecio);
            }

            // 2. Actualizar Relaciones Simples
            if (dto.getSubcategoria() != null && dto.getSubcategoria().getId() != null) {
                Subcategoria sub = subcategorioRepository.findById(dto.getSubcategoria().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Subcategoria no encontrada"));
                insumo.setSubcategoria(sub);
            }
            if (dto.getUnidadMedida() != null && dto.getUnidadMedida().getId() != null) {
                UnidadMedida um = unidadMedidaRepository.findById(dto.getUnidadMedida().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Unidad de medida no encontrada"));
                insumo.setUnidadMedida(um);
            }

            // 3. Actualización del Stock y su Histórico
            if (dto.getStockArticuloInsumo() != null) {
                if (dto.getStockArticuloInsumo().getCantidad() < 0) {
                    throw new IllegalArgumentException("El stock no puede ser negativo");
                }

                StockArticuloInsumo stock = insumo.getStockArticuloInsumo();

                if (stock == null) {
                    stock = new StockArticuloInsumo();
                    insumo.setStockAndLink(stock);
                }

                // --- B. HISTÓRICO DE STOCK ---
                Integer cantidadAnterior = stock.getCantidad() != null ? stock.getCantidad() : 0;
                Integer cantidadNueva = dto.getStockArticuloInsumo().getCantidad();

                // Guardar histórico si la cantidad cambió
                if (!cantidadNueva.equals(cantidadAnterior)) {
                    HistoricoStockArticuloInsumo historicoStock = new HistoricoStockArticuloInsumo();
                    historicoStock.setCantidad(cantidadNueva);
                    historicoStock.setFechaActualizacion(new Date());
                    historicoStock.setIdstockarticuloInsumo(stock); // Relación con el padre (stock)

                    if (stock.getHistoricoStockArticuloInsumoList() == null) {
                        stock.setHistoricoStockArticuloInsumoList(new ArrayList<>());
                    }
                    stock.getHistoricoStockArticuloInsumoList().add(historicoStock);
                }

                // Actualizar valores actuales del stock
                stock.setMinStock(dto.getStockArticuloInsumo().getMinStock());
                stock.setCantidad(cantidadNueva);

                // Verificar cambio de Sucursal
                Long sucursalIdDto = dto.getStockArticuloInsumo().getSucursalId();
                if (sucursalIdDto != null) {
                    if (stock.getSucursal() == null || !stock.getSucursal().getId().equals(sucursalIdDto)) {
                        Sucursal suc = sucursalRepository.findById(sucursalIdDto)
                                .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada"));
                        stock.setSucursal(suc);
                    }
                }
            }

            return articuloInsumoRepository.save(insumo);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el insumo: " + e.getMessage(), e);
        }
    }

    @Transactional
    public ArticuloInsumo eliminarLogico(Long id) {
        ArticuloInsumo insumo = articuloInsumoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insumo con id " + id + " no existe"));

        if (!insumo.getExiste()) {
            throw new IllegalStateException("El insumo con id " + id + " ya está eliminado");
        }

        insumo.setExiste(false);
        return articuloInsumoRepository.save(insumo);
    }

    public List<ArticuloInsumo> buscarPorNombre(String nombre) {
        return articuloInsumoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<ArticuloInsumo> buscarPorSubcategoria(Long subCategoriaId) {
        return articuloInsumoRepository.findBySubcategoria_Id(subCategoriaId);
    }

    public List<ArticuloInsumo> findByExisteTrue() {
        return articuloInsumoRepository.findByExisteTrue();
    }
}