package com.buenSabor.BackEnd.controllers.estadistica;

import com.buenSabor.BackEnd.dto.estadisticas.TodosHistoricosDTO;
import com.buenSabor.BackEnd.mapper.HistoricoMapper;
import com.buenSabor.BackEnd.models.producto.HistoricoPrecioCostoArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.HistoricoPrecioVentaArticulo;
import com.buenSabor.BackEnd.models.producto.HistoricoStockArticuloInsumo;
import com.buenSabor.BackEnd.models.venta.HistoricoEstadoPedido;
import com.buenSabor.BackEnd.services.historico.HistoricoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historico")
@Tag(name = "Histórico", description = "Operaciones relacionadas con el histórico de precios, stock y estados de pedidos")
public class HistoricoController {

    private static final Logger logger = LoggerFactory.getLogger(HistoricoController.class);

    @Autowired
    private HistoricoService historicoService;

    @Autowired
    private HistoricoMapper historicoMapper;

    @Operation(summary = "Obtener histórico de precio costo de un insumo", description = "Retorna el histórico completo de precios de costo de un artículo insumo específico ordenado por fecha descendente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico obtenido exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/precio-costo-insumo/{articuloInsumoId}")
    public ResponseEntity<?> obtenerHistoricoPrecioCostoInsumo(
            @Parameter(description = "ID del artículo insumo", required = true) @PathVariable Long articuloInsumoId) {
        try {
            if (articuloInsumoId == null || articuloInsumoId <= 0) {
                logger.error("ID de artículo insumo inválido: {}", articuloInsumoId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\":\"ID de artículo insumo inválido\"}");
            }
            List<HistoricoPrecioCostoArticuloInsumo> historico = historicoService
                    .obtenerHistoricoPrecioCostoInsumo(articuloInsumoId);
            return ResponseEntity.ok(historicoMapper.toPrecioCostoDTOList(historico));
        } catch (Exception e) {
            logger.error("Error al obtener histórico de precio costo para insumo {}: {}", articuloInsumoId,
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener el histórico de precio costo\"}");
        }
    }

    @Operation(summary = "Obtener histórico de precio venta de un artículo", description = "Retorna el histórico completo de precios de venta de un artículo específico ordenado por fecha descendente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico obtenido exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/precio-venta-articulo/{articuloId}")
    public ResponseEntity<?> obtenerHistoricoPrecioVentaArticulo(
            @Parameter(description = "ID del artículo", required = true) @PathVariable Long articuloId) {
        try {
            if (articuloId == null || articuloId <= 0) {
                logger.error("ID de artículo inválido: {}", articuloId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\":\"ID de artículo inválido\"}");
            }
            List<HistoricoPrecioVentaArticulo> historico = historicoService
                    .obtenerHistoricoPrecioVentaArticulo(articuloId);
            return ResponseEntity.ok(historicoMapper.toPrecioVentaDTOList(historico));
        } catch (Exception e) {
            logger.error("Error al obtener histórico de precio venta para artículo {}: {}", articuloId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener el histórico de precio venta\"}");
        }
    }

    @Operation(summary = "Obtener histórico de stock de un insumo", description = "Retorna el histórico completo de stock de un artículo insumo específico ordenado por fecha de actualización descendente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico obtenido exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/stock-insumo/{stockId}")
    public ResponseEntity<?> obtenerHistoricoStockInsumo(
            @Parameter(description = "ID del stock", required = true) @PathVariable Long stockId) {
        try {
            if (stockId == null || stockId <= 0) {
                logger.error("ID de stock inválido: {}", stockId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\":\"ID de stock inválido\"}");
            }
            List<HistoricoStockArticuloInsumo> historico = historicoService.obtenerHistoricoStockInsumo(stockId);
            return ResponseEntity.ok(historicoMapper.toStockDTOList(historico));
        } catch (Exception e) {
            logger.error("Error al obtener histórico de stock para stock {}: {}", stockId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener el histórico de stock\"}");
        }
    }

    @Operation(summary = "Obtener histórico de estados de un pedido", description = "Retorna el histórico completo de cambios de estado de un pedido específico ordenado por fecha de cambio descendente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico obtenido exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/estado-pedido/{pedidoId}")
    public ResponseEntity<?> obtenerHistoricoEstadosPedido(
            @Parameter(description = "ID del pedido", required = true) @PathVariable Long pedidoId) {
        try {
            if (pedidoId == null || pedidoId <= 0) {
                logger.error("ID de pedido inválido: {}", pedidoId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\":\"ID de pedido inválido\"}");
            }
            List<HistoricoEstadoPedido> historico = historicoService.obtenerHistoricoEstadosPedido(pedidoId);
            return ResponseEntity.ok(historicoMapper.toEstadoPedidoDTOList(historico));
        } catch (Exception e) {
            logger.error("Error al obtener histórico de estados para pedido {}: {}", pedidoId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener el histórico de estados del pedido\"}");
        }
    }

    @Operation(summary = "Obtener último estado de un pedido", description = "Retorna el estado más reciente de un pedido específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado obtenido exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado o sin histórico", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/ultimo-estado-pedido/{pedidoId}")
    public ResponseEntity<?> obtenerUltimoEstadoPedido(
            @Parameter(description = "ID del pedido", required = true) @PathVariable Long pedidoId) {
        try {
            if (pedidoId == null || pedidoId <= 0) {
                logger.error("ID de pedido inválido: {}", pedidoId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\":\"ID de pedido inválido\"}");
            }
            HistoricoEstadoPedido historico = historicoService.obtenerUltimoEstadoPedido(pedidoId);
            if (historico != null) {
                return ResponseEntity.ok(historicoMapper.toEstadoPedidoDTO(historico));
            } else {
                logger.warn("No se encontró histórico para el pedido {}", pedidoId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"No se encontró histórico para el pedido especificado\"}");
            }
        } catch (Exception e) {
            logger.error("Error al obtener último estado del pedido {}: {}", pedidoId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener el último estado del pedido\"}");
        }
    }

    @Operation(summary = "Obtener último precio costo de un insumo", description = "Retorna el precio de costo más reciente de un artículo insumo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Precio obtenido exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Artículo insumo no encontrado o sin histórico", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/ultimo-precio-costo-insumo/{articuloInsumoId}")
    public ResponseEntity<?> obtenerUltimoPrecioCostoInsumo(
            @Parameter(description = "ID del artículo insumo", required = true) @PathVariable Long articuloInsumoId) {
        try {
            if (articuloInsumoId == null || articuloInsumoId <= 0) {
                logger.error("ID de artículo insumo inválido: {}", articuloInsumoId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\":\"ID de artículo insumo inválido\"}");
            }
            HistoricoPrecioCostoArticuloInsumo historico = historicoService
                    .obtenerUltimoPrecioCostoInsumo(articuloInsumoId);
            if (historico != null) {
                return ResponseEntity.ok(historicoMapper.toPrecioCostoDTO(historico));
            } else {
                logger.warn("No se encontró histórico de precio costo para el insumo {}", articuloInsumoId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"No se encontró histórico de precio costo para el insumo especificado\"}");
            }
        } catch (Exception e) {
            logger.error("Error al obtener último precio costo del insumo {}: {}", articuloInsumoId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener el último precio costo\"}");
        }
    }

    @Operation(summary = "Obtener último precio venta de un artículo", description = "Retorna el precio de venta más reciente de un artículo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Precio obtenido exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Artículo no encontrado o sin histórico", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/ultimo-precio-venta-articulo/{articuloId}")
    public ResponseEntity<?> obtenerUltimoPrecioVentaArticulo(
            @Parameter(description = "ID del artículo", required = true) @PathVariable Long articuloId) {
        try {
            if (articuloId == null || articuloId <= 0) {
                logger.error("ID de artículo inválido: {}", articuloId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\":\"ID de artículo inválido\"}");
            }
            HistoricoPrecioVentaArticulo historico = historicoService.obtenerUltimoPrecioVentaArticulo(articuloId);
            if (historico != null) {
                return ResponseEntity.ok(historicoMapper.toPrecioVentaDTO(historico));
            } else {
                logger.warn("No se encontró histórico de precio venta para el artículo {}", articuloId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"No se encontró histórico de precio venta para el artículo especificado\"}");
            }
        } catch (Exception e) {
            logger.error("Error al obtener último precio venta del artículo {}: {}", articuloId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener el último precio venta\"}");
        }
    }

    @Operation(summary = "Obtener último registro de stock de un insumo", description = "Retorna el registro de stock más reciente de un artículo insumo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock obtenido exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Stock no encontrado o sin histórico", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/ultimo-stock-insumo/{stockId}")
    public ResponseEntity<?> obtenerUltimoStockInsumo(
            @Parameter(description = "ID del stock", required = true) @PathVariable Long stockId) {
        try {
            if (stockId == null || stockId <= 0) {
                logger.error("ID de stock inválido: {}", stockId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\":\"ID de stock inválido\"}");
            }
            HistoricoStockArticuloInsumo historico = historicoService.obtenerUltimoStockInsumo(stockId);
            if (historico != null) {
                return ResponseEntity.ok(historicoMapper.toStockDTO(historico));
            } else {
                logger.warn("No se encontró histórico de stock para el stock {}", stockId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"No se encontró histórico de stock para el stock especificado\"}");
            }
        } catch (Exception e) {
            logger.error("Error al obtener último stock del stock {}: {}", stockId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener el último registro de stock\"}");
        }
    }

    @Operation(summary = "Obtener todos los históricos de precio costo", description = "Retorna todos los registros históricos de precios de costo de todos los artículos insumo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Históricos obtenidos exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/precio-costo-insumo")
    public ResponseEntity<?> obtenerTodosHistoricosPrecioCosto() {
        try {
            List<HistoricoPrecioCostoArticuloInsumo> historicos = historicoService.obtenerTodosHistoricosPrecioCosto();
            return ResponseEntity.ok(historicoMapper.toPrecioCostoDTOList(historicos));
        } catch (Exception e) {
            logger.error("Error al obtener todos los históricos de precio costo: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener los históricos de precio costo\"}");
        }
    }

    @Operation(summary = "Obtener todos los históricos de precio venta", description = "Retorna todos los registros históricos de precios de venta de todos los artículos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Históricos obtenidos exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/precio-venta-articulo")
    public ResponseEntity<?> obtenerTodosHistoricosPrecioVenta() {
        try {
            List<HistoricoPrecioVentaArticulo> historicos = historicoService.obtenerTodosHistoricosPrecioVenta();
            return ResponseEntity.ok(historicoMapper.toPrecioVentaDTOList(historicos));
        } catch (Exception e) {
            logger.error("Error al obtener todos los históricos de precio venta: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener los históricos de precio venta\"}");
        }
    }

    @Operation(summary = "Obtener todos los históricos de stock", description = "Retorna todos los registros históricos de stock de todos los artículos insumo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Históricos obtenidos exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/stock-insumo")
    public ResponseEntity<?> obtenerTodosHistoricosStock() {
        try {
            List<HistoricoStockArticuloInsumo> historicos = historicoService.obtenerTodosHistoricosStock();
            return ResponseEntity.ok(historicoMapper.toStockDTOList(historicos));
        } catch (Exception e) {
            logger.error("Error al obtener todos los históricos de stock: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener los históricos de stock\"}");
        }
    }

    @Operation(summary = "Obtener todos los históricos de estados de pedidos", description = "Retorna todos los registros históricos de cambios de estado de todos los pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Históricos obtenidos exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/estado-pedido")
    public ResponseEntity<?> obtenerTodosHistoricosEstadoPedido() {
        try {
            List<HistoricoEstadoPedido> historicos = historicoService.obtenerTodosHistoricosEstadoPedido();
            return ResponseEntity.ok(historicoMapper.toEstadoPedidoDTOList(historicos));
        } catch (Exception e) {
            logger.error("Error al obtener todos los históricos de estados de pedido: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener los históricos de estados de pedido\"}");
        }
    }

    @Operation(summary = "Obtener todos los históricos agrupados", description = "Retorna todos los registros históricos de precios, stock y estados de pedidos agrupados en un solo objeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Históricos obtenidos exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/todos")
    public ResponseEntity<?> obtenerTodosLosHistoricos() {
        try {
            TodosHistoricosDTO todosHistoricos = historicoService.obtenerTodosLosHistoricos();
            return ResponseEntity.ok(todosHistoricos);
        } catch (Exception e) {
            logger.error("Error al obtener todos los históricos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener todos los históricos\"}");
        }
    }
}