package com.buenSabor.BackEnd.services.estadisticas;

import com.buenSabor.BackEnd.dto.estadisticas.IngresoDataDTO;
import com.buenSabor.BackEnd.dto.estadisticas.InsumoStockDTO;
import com.buenSabor.BackEnd.dto.estadisticas.ProductoVendidoDTO;
import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.HistoricoStockArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import com.buenSabor.BackEnd.models.venta.DetallePedido;
import com.buenSabor.BackEnd.models.venta.Pedido;
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

    public List<InsumoStockDTO> obtenerInsumosConStock(Long sucursalId) {
        List<ArticuloInsumo> insumos = articuloInsumoRepository.findAll();
        
        return insumos.stream()
            .filter(insumo -> insumo.getStockArticuloInsumo() != null)
            .filter(insumo -> sucursalId == null || 
                    insumo.getStockArticuloInsumo().getSucursal().getId().equals(sucursalId))
            .map(insumo -> {
                StockArticuloInsumo stock = insumo.getStockArticuloInsumo();
                InsumoStockDTO dto = new InsumoStockDTO();
                dto.setId(insumo.getId());
                dto.setNombre(insumo.getNombre());
                dto.setNivelActual(stock.getCantidad());
                dto.setNivelMinimo(stock.getMinStock());
                dto.setNivelMaximo(100);
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
        Date fechaFin = Date.from(ahora.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        List<Pedido> pedidos = sucursalId != null ?
            pedidoRepository.findBySucursal_IdAndFechaBetween(sucursalId, fechaInicio, fechaFin) :
            pedidoRepository.findByFechaBetween(fechaInicio, fechaFin);
        
        if (pedidos == null || pedidos.isEmpty()) {
            return new ArrayList<>();
        }
        
        Map<String, List<DetallePedido>> detallesPorArticulo = new HashMap<>();
        
        for (Pedido pedido : pedidos) {
            if (pedido.getDetallePedidoList() == null || pedido.getDetallePedidoList().isEmpty()) {
                continue;
            }
            
            for (DetallePedido detalle : pedido.getDetallePedidoList()) {
                if (detalle.getArticulo() == null || detalle.getArticulo().getNombre() == null) {
                    continue;
                }
                
                String nombreArticulo = detalle.getArticulo().getNombre();
                detallesPorArticulo.computeIfAbsent(nombreArticulo, k -> new ArrayList<>()).add(detalle);
            }
        }
        
        if (detallesPorArticulo.isEmpty()) {
            return new ArrayList<>();
        }
        
        return detallesPorArticulo.entrySet().stream()
            .sorted((e1, e2) -> Integer.compare(
                e2.getValue().stream().mapToInt(DetallePedido::getCantidad).sum(),
                e1.getValue().stream().mapToInt(DetallePedido::getCantidad).sum()
            ))
            .limit(limite != null ? limite : 3)
            .map(entry -> {
                String nombreArticulo = entry.getKey();
                List<DetallePedido> detalles = entry.getValue();
                
                ProductoVendidoDTO dto = new ProductoVendidoDTO();
                dto.setNombre(nombreArticulo);
                dto.setVentasDiarias(calcularVentasDiarias(detalles, ahora));
                dto.setVentasSemanales(calcularVentasSemanales(detalles, ahora));
                dto.setVentasMensuales(calcularVentasMensuales(detalles, ahora));
                
                return dto;
            })
            .collect(Collectors.toList());
    }

    private List<Integer> calcularVentasDiarias(List<DetallePedido> detalles, LocalDate ahora) {
        List<Integer> ventasDiarias = new ArrayList<>();
        for (int i = 23; i >= 0; i--) {
            LocalDate fecha = ahora.minusDays(i);
            int ventas = detalles.stream()
                .filter(d -> d.getPedido() != null && d.getPedido().getFecha() != null)
                .filter(d -> {
                    LocalDate fechaPedido = d.getPedido().getFecha().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                    return fechaPedido.equals(fecha);
                })
                .mapToInt(DetallePedido::getCantidad)
                .sum();
            ventasDiarias.add(ventas);
        }
        return ventasDiarias;
    }

    private List<Integer> calcularVentasSemanales(List<DetallePedido> detalles, LocalDate ahora) {
        List<Integer> ventasSemanales = new ArrayList<>();
        for (int i = 11; i >= 0; i--) {
            LocalDate finSemana = ahora.minusWeeks(i);
            LocalDate inicioSemana = finSemana.minusDays(6);
            
            int ventas = detalles.stream()
                .filter(d -> d.getPedido() != null && d.getPedido().getFecha() != null)
                .filter(d -> {
                    LocalDate fechaPedido = d.getPedido().getFecha().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                    return !fechaPedido.isBefore(inicioSemana) && !fechaPedido.isAfter(finSemana);
                })
                .mapToInt(DetallePedido::getCantidad)
                .sum();
            ventasSemanales.add(ventas);
        }
        return ventasSemanales;
    }

    private List<Integer> calcularVentasMensuales(List<DetallePedido> detalles, LocalDate ahora) {
        List<Integer> ventasMensuales = new ArrayList<>();
        for (int i = 11; i >= 0; i--) {
            LocalDate mes = ahora.minusMonths(i);
            LocalDate inicioMes = mes.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate finMes = mes.with(TemporalAdjusters.lastDayOfMonth());
            
            int ventas = detalles.stream()
                .filter(d -> d.getPedido() != null && d.getPedido().getFecha() != null)
                .filter(d -> {
                    LocalDate fechaPedido = d.getPedido().getFecha().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                    return !fechaPedido.isBefore(inicioMes) && !fechaPedido.isAfter(finMes);
                })
                .mapToInt(DetallePedido::getCantidad)
                .sum();
            ventasMensuales.add(ventas);
        }
        return ventasMensuales;
    }

    public List<IngresoDataDTO> obtenerIngresosDiarios(Long sucursalId) {
        LocalDate ahora = LocalDate.now();
        LocalDate inicioSemana = ahora.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        
        List<IngresoDataDTO> ingresos = new ArrayList<>();
        String[] diasSemana = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        
        for (int i = 0; i < 7; i++) {
            LocalDate fecha = inicioSemana.plusDays(i);
            Date fechaDate = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
            
            List<Pedido> pedidos = sucursalId != null ?
                pedidoRepository.findBySucursal_IdAndFecha(sucursalId, fechaDate) :
                pedidoRepository.findByFecha(fechaDate);
            
            int totalOrdenes = pedidos.size();
            double totalGanancias = pedidos.stream()
                .flatMap(p -> p.getDetallePedidoList().stream())
                .mapToDouble(d -> d.getArticulo().getPrecio() * d.getCantidad())
                .sum();
            
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
            Date fechaFin = Date.from(finSemana.atStartOfDay(ZoneId.systemDefault()).toInstant());
            
            List<Pedido> pedidos = sucursalId != null ?
                pedidoRepository.findBySucursal_IdAndFechaBetween(sucursalId, fechaInicio, fechaFin) :
                pedidoRepository.findByFechaBetween(fechaInicio, fechaFin);
            
            int totalOrdenes = pedidos.size();
            double totalGanancias = pedidos.stream()
                .flatMap(p -> p.getDetallePedidoList().stream())
                .mapToDouble(d -> d.getArticulo().getPrecio() * d.getCantidad())
                .sum();
            
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
            Date fechaFin = Date.from(finMes.atStartOfDay(ZoneId.systemDefault()).toInstant());
            
            List<Pedido> pedidos = sucursalId != null ?
                pedidoRepository.findBySucursal_IdAndFechaBetween(sucursalId, fechaInicio, fechaFin) :
                pedidoRepository.findByFechaBetween(fechaInicio, fechaFin);
            
            int totalOrdenes = pedidos.size();
            double totalGanancias = pedidos.stream()
                .flatMap(p -> p.getDetallePedidoList().stream())
                .mapToDouble(d -> d.getArticulo().getPrecio() * d.getCantidad())
                .sum();
            
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
            Date fechaFin = Date.from(finAnio.atStartOfDay(ZoneId.systemDefault()).toInstant());
            
            List<Pedido> pedidos = sucursalId != null ?
                pedidoRepository.findBySucursal_IdAndFechaBetween(sucursalId, fechaInicio, fechaFin) :
                pedidoRepository.findByFechaBetween(fechaInicio, fechaFin);
            
            int totalOrdenes = pedidos.size();
            double totalGanancias = pedidos.stream()
                .flatMap(p -> p.getDetallePedidoList().stream())
                .mapToDouble(d -> d.getArticulo().getPrecio() * d.getCantidad())
                .sum();
            
            ingresos.add(new IngresoDataDTO(String.valueOf(anio), totalOrdenes, totalGanancias));
        }
        
        return ingresos;
    }
}
