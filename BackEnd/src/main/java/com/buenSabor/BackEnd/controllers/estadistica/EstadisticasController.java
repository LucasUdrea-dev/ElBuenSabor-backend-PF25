package com.buenSabor.BackEnd.controllers.estadistica;

import com.buenSabor.BackEnd.dto.estadisticas.IngresoDataDTO;
import com.buenSabor.BackEnd.dto.estadisticas.InsumoStockDTO;
import com.buenSabor.BackEnd.dto.estadisticas.ProductoVendidoDTO;
import com.buenSabor.BackEnd.services.estadisticas.EstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estadisticas")
@CrossOrigin("*")
public class EstadisticasController {

    @Autowired
    private EstadisticasService estadisticasService;

    @GetMapping("/insumos-stock")
    public ResponseEntity<List<InsumoStockDTO>> obtenerInsumosConStock(
            @RequestParam(required = false) Long sucursalId) {
        try {
            List<InsumoStockDTO> insumos = estadisticasService.obtenerInsumosConStock(sucursalId);
            return ResponseEntity.ok(insumos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/productos-mas-vendidos")
    public ResponseEntity<List<ProductoVendidoDTO>> obtenerProductosMasVendidos(
            @RequestParam(required = false) Long sucursalId,
            @RequestParam(required = false, defaultValue = "3") Integer limite) {
        try {
            List<ProductoVendidoDTO> productos = 
                estadisticasService.obtenerProductosMasVendidos(sucursalId, limite);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/ingresos/diarios")
    public ResponseEntity<List<IngresoDataDTO>> obtenerIngresosDiarios(
            @RequestParam(required = false) Long sucursalId) {
        try {
            List<IngresoDataDTO> ingresos = estadisticasService.obtenerIngresosDiarios(sucursalId);
            return ResponseEntity.ok(ingresos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/ingresos/semanales")
    public ResponseEntity<List<IngresoDataDTO>> obtenerIngresosSemanales(
            @RequestParam(required = false) Long sucursalId) {
        try {
            List<IngresoDataDTO> ingresos = estadisticasService.obtenerIngresosSemanales(sucursalId);
            return ResponseEntity.ok(ingresos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/ingresos/mensuales")
    public ResponseEntity<List<IngresoDataDTO>> obtenerIngresosMensuales(
            @RequestParam(required = false) Long sucursalId) {
        try {
            List<IngresoDataDTO> ingresos = estadisticasService.obtenerIngresosMensuales(sucursalId);
            return ResponseEntity.ok(ingresos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/ingresos/anuales")
    public ResponseEntity<List<IngresoDataDTO>> obtenerIngresosAnuales(
            @RequestParam(required = false) Long sucursalId) {
        try {
            List<IngresoDataDTO> ingresos = estadisticasService.obtenerIngresosAnuales(sucursalId);
            return ResponseEntity.ok(ingresos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
