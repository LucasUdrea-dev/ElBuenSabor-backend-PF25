package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.dto.producto.insumo.InsumoDTO;
import com.buenSabor.BackEnd.mapper.ArticuloMapper;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturado;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturadoDetalleInsumo;
import com.buenSabor.BackEnd.models.producto.HistoricoPrecioCostoArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.HistoricoStockArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.Subcategoria;
import com.buenSabor.BackEnd.models.producto.UnidadMedida;
import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import com.buenSabor.BackEnd.models.venta.Promocion;
import com.buenSabor.BackEnd.models.venta.PromocionArticulo;
import com.buenSabor.BackEnd.repositories.producto.ArticuloInsumoRepository;
import com.buenSabor.BackEnd.repositories.producto.ArticuloManufacturadoDetalleInsumoRepository;
import com.buenSabor.BackEnd.repositories.producto.SubcategorioRepository;
import com.buenSabor.BackEnd.repositories.producto.UnidadMedidaRepository;
import com.buenSabor.BackEnd.repositories.venta.PromocionRepository;
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
    private PromocionRepository promocionRepository;
@Autowired
    private ArticuloManufacturadoDetalleInsumoRepository manufacturadoDetalleRepository;
    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private com.buenSabor.BackEnd.repositories.company.SucursalRepository sucursalRepository;

    public ArticuloInsumoService(ArticuloInsumoRepository articuloInsumoRepository) {
        super(articuloInsumoRepository);
        this.articuloInsumoRepository = articuloInsumoRepository;
        this.manufacturadoDetalleRepository = manufacturadoDetalleRepository;
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

            // --- A. CAPTURA DE VALORES  ---
            
            // 1. Capturamos Precio de VENTA 
            Double precioVentaAnterior = insumo.getPrecio() != null ? insumo.getPrecio() : 0.0;
            Double precioVentaNuevo = dto.getPrecio() != null ? dto.getPrecio() : 0.0;

            Double precioCostoAnterior = insumo.getPrecioCompra() != null ? insumo.getPrecioCompra() : 0.0;
            Double precioCostoNuevo = dto.getPrecioCompra() != null ? dto.getPrecioCompra() : 0.0;

            System.out.println("--- ACTUALIZANDO INSUMO: " + insumo.getNombre() + " ---");
            System.out.println("Precio Venta Anterior: $" + precioVentaAnterior + " -> Nuevo: $" + precioVentaNuevo);

            // 3. Actualizar la entidad con el Mapper
            articuloMapper.updateFromDto(dto, insumo);
            
            insumo.setPrecio(precioVentaNuevo); 
            insumo.setPrecioCompra(precioCostoNuevo);

            // --- B. HISTÓRICO DE PRECIO DE COSTO ---
            if (!precioCostoNuevo.equals(precioCostoAnterior)) {
                HistoricoPrecioCostoArticuloInsumo historicoPrecio = new HistoricoPrecioCostoArticuloInsumo();
                historicoPrecio.setPrecio(precioCostoNuevo);
                historicoPrecio.setFecha(new Date());
                historicoPrecio.setIdArticuloInsumo(insumo);

                if (insumo.getHistoricoPrecioCosto() == null) {
                    insumo.setHistoricoPrecioCosto(new ArrayList<>());
                }
                insumo.getHistoricoPrecioCosto().add(historicoPrecio);
            }

            // --- C. ACTUALIZACIÓN RELACIONES SIMPLES ---
            if (dto.getSubcategoria() != null && dto.getSubcategoria().getId() != null) {
                insumo.setSubcategoria(subcategorioRepository.findById(dto.getSubcategoria().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Subcategoria no encontrada")));
            }
            if (dto.getUnidadMedida() != null && dto.getUnidadMedida().getId() != null) {
                insumo.setUnidadMedida(unidadMedidaRepository.findById(dto.getUnidadMedida().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Unidad de medida no encontrada")));
            }

            // --- D. ACTUALIZACIÓN DE STOCK ---
            if (dto.getStockArticuloInsumo() != null) {
                StockArticuloInsumo stock = insumo.getStockArticuloInsumo();
                if (stock == null) {
                    stock = new StockArticuloInsumo();
                    insumo.setStockAndLink(stock);
                }
                
                // Histórico de Stock
                Integer stockAnt = stock.getCantidad() != null ? stock.getCantidad() : 0;
                Integer stockNuevo = dto.getStockArticuloInsumo().getCantidad();
                
                if (!stockNuevo.equals(stockAnt)) {
                    HistoricoStockArticuloInsumo hStock = new HistoricoStockArticuloInsumo();
                    hStock.setCantidad(stockNuevo);
                    hStock.setFechaActualizacion(new Date());
                    hStock.setIdstockarticuloInsumo(stock);
                    if (stock.getHistoricoStockArticuloInsumoList() == null) 
                        stock.setHistoricoStockArticuloInsumoList(new ArrayList<>());
                    stock.getHistoricoStockArticuloInsumoList().add(hStock);
                }

                stock.setCantidad(stockNuevo);
                stock.setMinStock(dto.getStockArticuloInsumo().getMinStock());
                
                if (dto.getStockArticuloInsumo().getSucursalId() != null) {
                     stock.setSucursal(sucursalRepository.findById(dto.getStockArticuloInsumo().getSucursalId())
                            .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada")));
                }
            }
            //seccion promp 
            double diferenciaVenta = precioVentaNuevo - precioVentaAnterior;

            if (Math.abs(diferenciaVenta) > 0.0001) {
                System.out.println(">> DETECTADO CAMBIO EN PRECIO VENTA: $" + diferenciaVenta);

                List<PromocionArticulo> promosDirectas = insumo.getPromocionArticuloList();
                if (promosDirectas != null) {
                    for (PromocionArticulo pa : promosDirectas) {
                        actualizarPrecioPromo(pa.getIdPromocion(), diferenciaVenta, pa.getCantidad());
                    }
                }

              
                List<ArticuloManufacturadoDetalleInsumo> usosEnRecetas = 
                        manufacturadoDetalleRepository.findByArticuloInsumo_Id(insumo.getId());
                
                if (usosEnRecetas != null && !usosEnRecetas.isEmpty()) {
                    System.out.println(">> El insumo afecta a " + usosEnRecetas.size() + " manufacturados padres.");

                    for (ArticuloManufacturadoDetalleInsumo receta : usosEnRecetas) {
                        int cantidadInsumoEnReceta = receta.getCantidad(); 
                        
                        double impactoEnManufacturado = diferenciaVenta * cantidadInsumoEnReceta;

                        ArticuloManufacturado manufacturadoPadre = receta.getArticuloManufacturado();

                        List<PromocionArticulo> promosDelManufacturado = manufacturadoPadre.getPromocionArticuloList();
                        //se llama a la clase
                        if (promosDelManufacturado != null) {
                            for (PromocionArticulo paMan : promosDelManufacturado) {
                                actualizarPrecioPromo(paMan.getIdPromocion(), impactoEnManufacturado, paMan.getCantidad());
                            }
                        }
                    }
                }
            } else {
                System.out.println(">> El precio de venta no cambi no se actualizan promociones.");
            }

            return articuloInsumoRepository.save(insumo);

        } catch (Exception e) {
            e.printStackTrace(); 
            throw new RuntimeException("Error al actualizar el insumo: " + e.getMessage(), e);
        }
    }

    private void actualizarPrecioPromo(Promocion promo, double impactoUnitario, double cantidadEnPromo) {
        if (cantidadEnPromo <= 0) return;

        double aumentoTotal = impactoUnitario * cantidadEnPromo;
        double precioActual = promo.getPrecioRebajado() != null ? promo.getPrecioRebajado() : 0.0;
        double nuevoPrecio = precioActual + aumentoTotal;

        nuevoPrecio = Math.round(nuevoPrecio * 100.0) / 100.0;

        promo.setPrecioRebajado(nuevoPrecio);
        promocionRepository.save(promo);

        System.out.println("   [AUTO-UPDATE] Promo '" + promo.getDenominacion() + "' actualizada.");
        System.out.println("   Precio Venta Base: " + precioActual + " -> " + nuevoPrecio);
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