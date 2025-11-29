package com.buenSabor.BackEnd.services.estadisticas;

import com.buenSabor.BackEnd.dto.estadisticas.IngresoDataDTO;
import com.buenSabor.BackEnd.dto.estadisticas.InsumoStockDTO;
import com.buenSabor.BackEnd.dto.estadisticas.ProductoVendidoDTO;
import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.HistoricoStockArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import com.buenSabor.BackEnd.models.venta.DetallePedido;
import com.buenSabor.BackEnd.models.venta.DetallePromocion;
import com.buenSabor.BackEnd.models.venta.Pedido;
import com.buenSabor.BackEnd.models.venta.Promocion;
import com.buenSabor.BackEnd.models.venta.PromocionArticulo;
import com.buenSabor.BackEnd.repositories.producto.ArticuloInsumoRepository;
import com.buenSabor.BackEnd.repositories.producto.HistoricoStockArticuloInsumoRepository;
import com.buenSabor.BackEnd.repositories.venta.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;



@Service
@Transactional(readOnly = true)
public class EstadisticasService {

    @Autowired
    private ArticuloInsumoRepository articuloInsumoRepository;

    @Autowired
    private HistoricoStockArticuloInsumoRepository historicoStockRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    // --- CLASE AUXILIAR 
    private static class VentaRegistro {
        LocalDate fecha;
        int cantidad;

        public VentaRegistro(LocalDate fecha, int cantidad) {
            this.fecha = fecha;
            this.cantidad = cantidad;
        }

        public LocalDate getFecha() { return fecha; }
        public int getCantidad() { return cantidad; }
    }
    // -----------------------------------------------------------------------

    public List<InsumoStockDTO> obtenerInsumosConStock(Long sucursalId) {
        List<ArticuloInsumo> insumos = articuloInsumoRepository.findAll();
        
        return insumos.stream()
            .filter(insumo -> insumo.getStockArticuloInsumo() != null)
            .filter(insumo -> sucursalId == null || 
                    (insumo.getStockArticuloInsumo().getSucursal() != null && 
                     insumo.getStockArticuloInsumo().getSucursal().getId().equals(sucursalId)))
            .map(insumo -> {
                StockArticuloInsumo stock = insumo.getStockArticuloInsumo();
                InsumoStockDTO dto = new InsumoStockDTO();
                dto.setId(insumo.getId());
                dto.setNombre(insumo.getNombre());
                dto.setNivelActual(stock.getCantidad());
                dto.setNivelMinimo(stock.getMinStock());
                
                dto.setUnidad(insumo.getUnidadMedida() != null ? 
                    insumo.getUnidadMedida().getUnidad().toString() : "unidad");
                
                List<HistoricoStockArticuloInsumo> historico = 
                    historicoStockRepository.findByIdstockarticuloInsumo_IdOrderByFechaActualizacionDesc(stock.getId());
                
                List<Integer> stockHistorico = historico.stream()
                    .limit(24)
                    .map(HistoricoStockArticuloInsumo::getCantidad)
                    .collect(Collectors.toList());
                Collections.reverse(stockHistorico);
                
                if (stockHistorico.isEmpty()) {
                    stockHistorico = Collections.singletonList(stock.getCantidad());
                }
                
                dto.setStockHistorico(stockHistorico);
                return dto;
            })
            .collect(Collectors.toList());
    }

    public List<ProductoVendidoDTO> obtenerProductosMasVendidos(Long sucursalId, Integer limite) {
        LocalDate ahora = LocalDate.now();
        LocalDate hace30Dias = ahora.minusDays(30);
        
        Date fechaInicio = Date.from(hace30Dias.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaFin = Date.from(ahora.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()); 
        
        List<Pedido> pedidos = sucursalId != null ?
            pedidoRepository.findBySucursal_IdAndFechaBetween(sucursalId, fechaInicio, fechaFin) :
            pedidoRepository.findByFechaBetween(fechaInicio, fechaFin);
        
        if (pedidos == null || pedidos.isEmpty()) {
            return new ArrayList<>();
        }
                Map<String, List<VentaRegistro>> ventasPorArticulo = new HashMap<>();
        
        for (Pedido pedido : pedidos) {
            try {
                if (pedido.getFecha() == null) continue;
                
                // Fix for java.sql.Date throwing UnsupportedOperationException on toInstant()
                LocalDate fechaPedido;
                if (pedido.getFecha() instanceof java.sql.Date) {
                    fechaPedido = ((java.sql.Date) pedido.getFecha()).toLocalDate();
                } else {
                    fechaPedido = pedido.getFecha().toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate();
                }

                // 1. Procesa Ventas Directas 
                if (pedido.getDetallePedidoList() != null) {
                    for (DetallePedido detalle : pedido.getDetallePedidoList()) {
                        if (detalle.getArticulo() != null && detalle.getArticulo().getNombre() != null) {
                            String nombre = detalle.getArticulo().getNombre();
                            int cantidad = detalle.getCantidad() != null ? detalle.getCantidad() : 0;
                            
                            ventasPorArticulo.computeIfAbsent(nombre, k -> new ArrayList<>())
                                .add(new VentaRegistro(fechaPedido, cantidad));
                        }
                    }
                }

                // 2. Procesar Promociones (DetallePromocion -> Desglosar Artículos)
                if (pedido.getDetallePromocionList() != null) {
                    for (DetallePromocion detallePromo : pedido.getDetallePromocionList()) {
                        Promocion promocion = detallePromo.getPromocion();
                        int cantidadPromosVendidas = detallePromo.getCantidad();

                        if (promocion != null && promocion.getPromocionArticuloList() != null) {
                            // Recorremos la "receta" de la promoción
                            for (PromocionArticulo promoArticulo : promocion.getPromocionArticuloList()) {
                                if (promoArticulo.getIdArticulo() != null) {
                                    String nombreArticulo = promoArticulo.getIdArticulo().getNombre();
                                    // Cantidad total = (cantidad de promos vendidas) * (cantidad del artículo dentro de esa promo)
                                    int cantidadTotalArticulo = cantidadPromosVendidas * promoArticulo.getCantidad();

                                    ventasPorArticulo.computeIfAbsent(nombreArticulo, k -> new ArrayList<>())
                                        .add(new VentaRegistro(fechaPedido, cantidadTotalArticulo));
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Error procesando pedido ID " + pedido.getId() + ": " + e.toString(), e);
            }
        }
        
        if (ventasPorArticulo.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Ordenar por cantidad total vendida y limitar
        return ventasPorArticulo.entrySet().stream()
            .sorted((e1, e2) -> Integer.compare(
                e2.getValue().stream().mapToInt(VentaRegistro::getCantidad).sum(),
                e1.getValue().stream().mapToInt(VentaRegistro::getCantidad).sum()
            ))
            .limit(limite != null ? limite : 3)
            .map(entry -> {
                String nombreArticulo = entry.getKey();
                List<VentaRegistro> registros = entry.getValue();
                
                try {
                    ProductoVendidoDTO dto = new ProductoVendidoDTO();
                    dto.setNombre(nombreArticulo);
                    dto.setVentasDiarias(calcularVentasDiarias(registros, ahora));
                    dto.setVentasSemanales(calcularVentasSemanales(registros, ahora));
                    dto.setVentasMensuales(calcularVentasMensuales(registros, ahora));
                    return dto;
                } catch (Exception e) {
                    throw new RuntimeException("Error al procesar el artículo '" + nombreArticulo + "': " + e.toString(), e);
                }
            })
            .collect(Collectors.toList());
    }

    // --- Métodos de cálculo de períodos actualizados para usar VentaRegistro ---

    private List<Integer> calcularVentasDiarias(List<VentaRegistro> registros, LocalDate ahora) {
        List<Integer> ventasDiarias = new ArrayList<>();
        for (int i = 23; i >= 0; i--) {
            LocalDate fechaObjetivo = ahora.minusDays(i);
            int ventas = registros.stream()
                .filter(r -> r.getFecha().equals(fechaObjetivo))
                .mapToInt(VentaRegistro::getCantidad)
                .sum();
            ventasDiarias.add(ventas);
        }
        return ventasDiarias;
    }

    private List<Integer> calcularVentasSemanales(List<VentaRegistro> registros, LocalDate ahora) {
        List<Integer> ventasSemanales = new ArrayList<>();
        for (int i = 11; i >= 0; i--) {
            LocalDate finSemana = ahora.minusWeeks(i);
            LocalDate inicioSemana = finSemana.minusDays(6);
            
            int ventas = registros.stream()
                .filter(r -> !r.getFecha().isBefore(inicioSemana) && !r.getFecha().isAfter(finSemana))
                .mapToInt(VentaRegistro::getCantidad)
                .sum();
            ventasSemanales.add(ventas);
        }
        return ventasSemanales;
    }

    private List<Integer> calcularVentasMensuales(List<VentaRegistro> registros, LocalDate ahora) {
        List<Integer> ventasMensuales = new ArrayList<>();
        for (int i = 11; i >= 0; i--) {
            LocalDate mes = ahora.minusMonths(i);
            LocalDate inicioMes = mes.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate finMes = mes.with(TemporalAdjusters.lastDayOfMonth());
            
            int ventas = registros.stream()
                .filter(r -> !r.getFecha().isBefore(inicioMes) && !r.getFecha().isAfter(finMes))
                .mapToInt(VentaRegistro::getCantidad)
                .sum();
            ventasMensuales.add(ventas);
        }
        return ventasMensuales;
    }

    // --- Métodos de Ingresos (Revenue) ---

    public List<IngresoDataDTO> obtenerIngresosDiarios(Long sucursalId) {
        LocalDate ahora = LocalDate.now();
        LocalDate inicioSemana = ahora.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        
        List<IngresoDataDTO> ingresos = new ArrayList<>();
        String[] diasSemana = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        
        for (int i = 0; i < 7; i++) {
            LocalDate fecha = inicioSemana.plusDays(i);
            Date fechaDateInicio = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date fechaDateFin = Date.from(fecha.plusDays(1).atStartOfDay(ZoneId.systemDefault()).minusNanos(1).toInstant());
            
            List<Pedido> pedidos = sucursalId != null ?
                pedidoRepository.findBySucursal_IdAndFechaBetween(sucursalId, fechaDateInicio, fechaDateFin) :
                pedidoRepository.findByFechaBetween(fechaDateInicio, fechaDateFin);
            
            int totalOrdenes = pedidos.size();
            double totalGanancias = calcularTotalGanancias(pedidos);
            
            ingresos.add(new IngresoDataDTO(diasSemana[i], totalOrdenes, totalGanancias));
        }
        
        return ingresos;
    }

    public List<IngresoDataDTO> obtenerIngresosSemanales(Long sucursalId) {
        LocalDate ahora = LocalDate.now();
        List<IngresoDataDTO> ingresos = new ArrayList<>();
        
        for (int i = 3; i >= 0; i--) {
            LocalDate finSemana = ahora.minusWeeks(i);
            LocalDate inicioSemana = finSemana.minusDays(6);
            
            Date fechaInicio = Date.from(inicioSemana.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date fechaFin = Date.from(finSemana.plusDays(1).atStartOfDay(ZoneId.systemDefault()).minusNanos(1).toInstant());
            
            List<Pedido> pedidos = sucursalId != null ?
                pedidoRepository.findBySucursal_IdAndFechaBetween(sucursalId, fechaInicio, fechaFin) :
                pedidoRepository.findByFechaBetween(fechaInicio, fechaFin);
            
            int totalOrdenes = pedidos.size();
            double totalGanancias = calcularTotalGanancias(pedidos);
            
            ingresos.add(new IngresoDataDTO("Sem " + (4 - i), totalOrdenes, totalGanancias));
        }
        
        return ingresos;
    }

    public List<IngresoDataDTO> obtenerIngresosMensuales(Long sucursalId) {
        LocalDate ahora = LocalDate.now();
        List<IngresoDataDTO> ingresos = new ArrayList<>();
        String[] nombresMeses = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
        
        for (int i = 5; i >= 0; i--) {
            LocalDate mes = ahora.minusMonths(i);
            LocalDate inicioMes = mes.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate finMes = mes.with(TemporalAdjusters.lastDayOfMonth());
            
            Date fechaInicio = Date.from(inicioMes.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date fechaFin = Date.from(finMes.plusDays(1).atStartOfDay(ZoneId.systemDefault()).minusNanos(1).toInstant());
            
            List<Pedido> pedidos = sucursalId != null ?
                pedidoRepository.findBySucursal_IdAndFechaBetween(sucursalId, fechaInicio, fechaFin) :
                pedidoRepository.findByFechaBetween(fechaInicio, fechaFin);
            
            int totalOrdenes = pedidos.size();
            double totalGanancias = calcularTotalGanancias(pedidos);
            
            String nombreMes = nombresMeses[mes.getMonthValue() - 1];
            ingresos.add(new IngresoDataDTO(nombreMes, totalOrdenes, totalGanancias));
        }
        
        return ingresos;
    }

    public List<IngresoDataDTO> obtenerIngresosAnuales(Long sucursalId) {
        LocalDate ahora = LocalDate.now();
        List<IngresoDataDTO> ingresos = new ArrayList<>();
        
        for (int i = 3; i >= 0; i--) {
            int anio = ahora.getYear() - i;
            LocalDate inicioAnio = LocalDate.of(anio, 1, 1);
            LocalDate finAnio = LocalDate.of(anio, 12, 31);
            
            Date fechaInicio = Date.from(inicioAnio.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date fechaFin = Date.from(finAnio.plusDays(1).atStartOfDay(ZoneId.systemDefault()).minusNanos(1).toInstant());
            
            List<Pedido> pedidos = sucursalId != null ?
                pedidoRepository.findBySucursal_IdAndFechaBetween(sucursalId, fechaInicio, fechaFin) :
                pedidoRepository.findByFechaBetween(fechaInicio, fechaFin);
            
            int totalOrdenes = pedidos.size();
            double totalGanancias = calcularTotalGanancias(pedidos);
            
            ingresos.add(new IngresoDataDTO(String.valueOf(anio), totalOrdenes, totalGanancias));
        }
        
        return ingresos;
    }

    /**
     * Calcula la ganancia total sumando artículos individuales y promociones completas.
     */
    private double calcularTotalGanancias(List<Pedido> pedidos) {
        double gananciaArticulos = pedidos.stream()
            .flatMap(p -> p.getDetallePedidoList() != null ? p.getDetallePedidoList().stream() : java.util.stream.Stream.empty())
            .filter(d -> d.getArticulo() != null && d.getArticulo().getPrecio() != null)
            .mapToDouble(d -> d.getArticulo().getPrecio() * (d.getCantidad() != null ? d.getCantidad() : 0))
            .sum();

        double gananciaPromociones = pedidos.stream()
            .flatMap(p -> p.getDetallePromocionList() != null ? p.getDetallePromocionList().stream() : java.util.stream.Stream.empty())
            .filter(dp -> dp.getPromocion() != null && dp.getPromocion().getPrecioRebajado() != null)
            .mapToDouble(dp -> dp.getPromocion().getPrecioRebajado() * dp.getCantidad())
            .sum();

        return gananciaArticulos + gananciaPromociones;
    }
}