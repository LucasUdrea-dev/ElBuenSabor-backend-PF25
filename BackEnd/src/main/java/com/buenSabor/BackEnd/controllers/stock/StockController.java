package com.buenSabor.BackEnd.controllers.stock;

import com.buenSabor.BackEnd.dto.stock.StockCheckRequest;
import com.buenSabor.BackEnd.dto.stock.StockCheckResponse;
import com.buenSabor.BackEnd.services.stock.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock")
@Tag(name = "Stock", description = "Operaciones relacionadas con la gestión de stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("/verificar")
    @Operation(
        summary = "Verifica si hay stock suficiente para una lista de productos",
        description = "Verifica el stock de múltiples productos (insumos o manufacturados) y devuelve true si hay suficiente stock para todos.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Verificación de stock exitosa",
                content = @Content(schema = @Schema(implementation = Boolean.class))
            )
        }
    )
    public ResponseEntity<Boolean> verificarStock(@RequestBody StockCheckRequest request) {
        StockCheckResponse response = stockService.verificarStock(request);
        return ResponseEntity.ok(response.isHayStockSuficiente());
    }

    @PostMapping("/verificar-detallado")
    @Operation(
        summary = "Verifica el stock de productos y devuelve detalles de faltantes",
        description = "Verifica el stock de múltiples productos y devuelve un detalle de los productos que no tienen stock suficiente.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Verificación de stock detallada exitosa",
                content = @Content(schema = @Schema(implementation = StockCheckResponse.class))
            )
        }
    )
    public ResponseEntity<StockCheckResponse> verificarStockDetallado(@RequestBody StockCheckRequest request) {
        StockCheckResponse response = stockService.verificarStock(request);
        return ResponseEntity.ok(response);
    }
}
