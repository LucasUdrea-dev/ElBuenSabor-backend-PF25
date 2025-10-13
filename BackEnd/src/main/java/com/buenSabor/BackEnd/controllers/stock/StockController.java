package com.buenSabor.BackEnd.controllers.stock;

import com.buenSabor.BackEnd.dto.stock.AddStockRequest;
import com.buenSabor.BackEnd.dto.stock.StockCheckRequest;
import com.buenSabor.BackEnd.dto.stock.StockCheckResponse;
import com.buenSabor.BackEnd.dto.stock.StockResponse;
import com.buenSabor.BackEnd.dto.stock.UpdateStockRequest;
import com.buenSabor.BackEnd.services.stock.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@Tag(name = "Stock", description = "Operaciones relacionadas con la gestión de stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    @Operation(
        summary = "Obtener todos los registros de stock",
        description = "Retorna una lista paginada de todos los registros de stock",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de stock obtenida exitosamente",
                content = @Content(schema = @Schema(implementation = Page.class))
            )
        }
    )
    public ResponseEntity<Page<StockResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(stockService.findAll(pageable));
    }

    @GetMapping("/all")
    @Operation(
        summary = "Obtener todos los registros de stock (sin paginación)",
        description = "Retorna una lista de todos los registros de stock",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de stock obtenida exitosamente",
                content = @Content(schema = @Schema(implementation = List.class))
            )
        }
    )
    public ResponseEntity<List<StockResponse>> getAll() {
        return ResponseEntity.ok(stockService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener stock por ID",
        description = "Retorna un registro de stock por su ID",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Registro de stock encontrado",
                content = @Content(schema = @Schema(implementation = StockResponse.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Stock no encontrado"
            )
        }
    )
    public ResponseEntity<StockResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(stockService.findById(id));
    }

    @GetMapping("/sucursal/{sucursalId}")
    @Operation(
        summary = "Obtener stock por sucursal",
        description = "Retorna una lista de registros de stock para una sucursal específica",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de stock obtenida exitosamente",
                content = @Content(schema = @Schema(implementation = List.class))
            )
        }
    )
    public ResponseEntity<List<StockResponse>> getBySucursalId(@PathVariable Long sucursalId) {
        return ResponseEntity.ok(stockService.findBySucursalId(sucursalId));
    }

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
    
    @PostMapping("/agregar")
    @Operation(
        summary = "Agrega stock a un artículo de insumo",
        description = "Agrega una cantidad específica de stock a un artículo de insumo, considerando su unidad de medida.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Stock actualizado exitosamente",
                content = @Content(schema = @Schema(implementation = Boolean.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Datos de entrada inválidos o insumo no encontrado"
            )
        }
    )
    public ResponseEntity<Boolean> agregarStock(@RequestBody AddStockRequest request) {
        boolean resultado = stockService.agregarStock(request);
        if (resultado) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PutMapping
    @Operation(
        summary = "Actualiza un registro de stock",
        description = "Actualiza la cantidad o el stock mínimo de un registro de stock existente",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Stock actualizado exitosamente",
                content = @Content(schema = @Schema(implementation = StockResponse.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Registro de stock no encontrado"
            )
        }
    )
    public ResponseEntity<StockResponse> actualizarStock(@RequestBody UpdateStockRequest request) {
        return ResponseEntity.ok(stockService.updateStock(request));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Elimina un registro de stock",
        description = "Elimina un registro de stock por su ID",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Registro de stock eliminado exitosamente"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Registro de stock no encontrado"
            )
        }
    )
    public ResponseEntity<Void> eliminarStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }
}
