package com.buenSabor.BackEnd.controllers.estadistica;

import com.buenSabor.BackEnd.models.producto.HistoricoPrecioCostoArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.HistoricoPrecioVentaArticulo;
import com.buenSabor.BackEnd.models.producto.HistoricoStockArticuloInsumo;
import com.buenSabor.BackEnd.models.venta.HistoricoEstadoPedido;
import com.buenSabor.BackEnd.services.historico.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historico")
@CrossOrigin("*")
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

    @GetMapping("/precio-costo-insumo/{articuloInsumoId}")
    public ResponseEntity<List<HistoricoPrecioCostoArticuloInsumo>> obtenerHistoricoPrecioCostoInsumo(
            @PathVariable Long articuloInsumoId) {
        try {
            List<HistoricoPrecioCostoArticuloInsumo> historico = 
                historicoService.obtenerHistoricoPrecioCostoInsumo(articuloInsumoId);
            return ResponseEntity.ok(historico);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/precio-venta-articulo/{articuloId}")
    public ResponseEntity<List<HistoricoPrecioVentaArticulo>> obtenerHistoricoPrecioVentaArticulo(
            @PathVariable Long articuloId) {
        try {
            List<HistoricoPrecioVentaArticulo> historico = 
                historicoService.obtenerHistoricoPrecioVentaArticulo(articuloId);
            return ResponseEntity.ok(historico);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/stock-insumo/{stockId}")
    public ResponseEntity<List<HistoricoStockArticuloInsumo>> obtenerHistoricoStockInsumo(
            @PathVariable Long stockId) {
        try {
            List<HistoricoStockArticuloInsumo> historico = 
                historicoService.obtenerHistoricoStockInsumo(stockId);
            return ResponseEntity.ok(historico);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/estado-pedido/{pedidoId}")
    public ResponseEntity<List<HistoricoEstadoPedido>> obtenerHistoricoEstadosPedido(
            @PathVariable Long pedidoId) {
        try {
            List<HistoricoEstadoPedido> historico = 
                historicoService.obtenerHistoricoEstadosPedido(pedidoId);
            return ResponseEntity.ok(historico);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/ultimo-estado-pedido/{pedidoId}")
    public ResponseEntity<HistoricoEstadoPedido> obtenerUltimoEstadoPedido(
            @PathVariable Long pedidoId) {
        try {
            HistoricoEstadoPedido historico = 
                historicoService.obtenerUltimoEstadoPedido(pedidoId);
            if (historico != null) {
                return ResponseEntity.ok(historico);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/ultimo-precio-costo-insumo/{articuloInsumoId}")
    public ResponseEntity<HistoricoPrecioCostoArticuloInsumo> obtenerUltimoPrecioCostoInsumo(
            @PathVariable Long articuloInsumoId) {
        try {
            HistoricoPrecioCostoArticuloInsumo historico = 
                historicoService.obtenerUltimoPrecioCostoInsumo(articuloInsumoId);
            if (historico != null) {
                return ResponseEntity.ok(historico);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/ultimo-precio-venta-articulo/{articuloId}")
    public ResponseEntity<HistoricoPrecioVentaArticulo> obtenerUltimoPrecioVentaArticulo(
            @PathVariable Long articuloId) {
        try {
            HistoricoPrecioVentaArticulo historico = 
                historicoService.obtenerUltimoPrecioVentaArticulo(articuloId);
            if (historico != null) {
                return ResponseEntity.ok(historico);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/ultimo-stock-insumo/{stockId}")
    public ResponseEntity<HistoricoStockArticuloInsumo> obtenerUltimoStockInsumo(
            @PathVariable Long stockId) {
        try {
            HistoricoStockArticuloInsumo historico = 
                historicoService.obtenerUltimoStockInsumo(stockId);
            if (historico != null) {
                return ResponseEntity.ok(historico);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
