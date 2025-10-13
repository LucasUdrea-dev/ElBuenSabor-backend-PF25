/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.producto;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.producto.insumo.InsumoDTO;
import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.services.producto.ArticuloInsumoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestionar operaciones CRUD de Artículos Insumo.
 * Utiliza DTOs y mappers para la transferencia de datos.
 * 
 * @author oscarloha
 */
@RestController
@RequestMapping("api/insumos")
@Tag(name = "ArticuloInsumo", description = "Operaciones relacionadas con entidad ArticuloInsumo")
public class ArticuloInsumoController extends BeanControllerImpl<ArticuloInsumo,ArticuloInsumoService>{

    private static final Logger logger = LoggerFactory.getLogger(ArticuloInsumoController.class);

    @Autowired
    private ArticuloInsumoService articuloInsumoService;

    @Operation(summary = "Crear un nuevo artículo insumo", 
               description = "Crea un artículo insumo con su stock inicial")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Insumo creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/crear")
    public ResponseEntity<?> saveFromDTO(
            @Parameter(description = "Datos del insumo a crear", required = true)
            @Valid @RequestBody InsumoDTO dto) {
        try {
            logger.info("Creando artículo insumo: {}", dto.getNombre());
            Long sucursalId = dto.getStockArticuloInsumo().getSucursalId();
            ArticuloInsumo savedInsumo = articuloInsumoService.crearInsumo(dto, sucursalId);
            
            // Actualizar el DTO con los IDs generados
            dto.setId(savedInsumo.getId());
            if (savedInsumo.getStockArticuloInsumo() != null) {
                dto.getStockArticuloInsumo().setId(savedInsumo.getStockArticuloInsumo().getId());
            }
            logger.info("Insumo creado exitosamente con ID: {}", dto.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (RuntimeException e) {
            logger.warn("Error de validación al crear insumo: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al guardar el insumo: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            logger.error("Error interno al crear insumo: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error interno al crear el insumo: " + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Actualizar un artículo insumo", 
               description = "Actualiza los datos de un insumo existente incluyendo su stock")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Insumo actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Insumo no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateInsumo(
            @Parameter(description = "ID del insumo a actualizar", required = true)
            @PathVariable Long id, 
            @Parameter(description = "Datos actualizados del insumo", required = true)
            @Valid @RequestBody InsumoDTO dto) {
        try {
            logger.info("Actualizando insumo con ID: {}", id);
            ArticuloInsumo updatedInsumo = articuloInsumoService.actualizar(id, dto);
            logger.info("Insumo con ID {} actualizado exitosamente", id);
            return ResponseEntity.ok(updatedInsumo);
        } catch (RuntimeException e) {
            logger.warn("Error al actualizar insumo ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al actualizar el insumo: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            logger.error("Error interno al actualizar insumo ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error interno al actualizar el insumo: " + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Eliminar un insumo (soft delete)", 
               description = "Realiza una eliminación lógica marcando existe=false")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Insumo eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Insumo no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del insumo a eliminar", required = true)
            @PathVariable Long id) {
        try {
            logger.info("Eliminando insumo con ID: {}", id);
            ArticuloInsumo actualizado = articuloInsumoService.eliminarLogico(id);
            logger.info("Insumo con ID {} eliminado exitosamente", id);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            logger.warn("Insumo con ID {} no encontrado: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            logger.error("Error al eliminar insumo ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al eliminar el insumo: " + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Listar insumos activos", 
               description = "Retorna todos los insumos marcados como existentes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/existentes")
    public ResponseEntity<?> getInsumosExistentes() {
        try {
            logger.info("Obteniendo insumos existentes");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(articuloInsumoService.findByExisteTrue());
        } catch (Exception e) {
            logger.error("Error al obtener insumos existentes: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener insumos existentes.\"}");
        }
    }

    @Operation(summary = "Buscar insumos por subcategoría", 
               description = "Retorna insumos filtrados por ID de subcategoría")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/subcategoria")
    public ResponseEntity<?> getInsumosBySubcategoria(
            @Parameter(description = "ID de la subcategoría", required = true)
            @RequestParam("idSubcategoria") Long idSubcategoria) {
        try {
            logger.info("Buscando insumos por subcategoría ID: {}", idSubcategoria);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(articuloInsumoService.buscarPorSubcategoria(idSubcategoria));
        } catch (Exception e) {
            logger.error("Error al buscar insumos por subcategoría: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener insumos por subcategoría.\"}");
        }
    }

    @Operation(summary = "Buscar insumos por nombre", 
               description = "Retorna insumos que contengan el texto en su nombre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/nombre")
    public ResponseEntity<?> getInsumosByNombre(
            @Parameter(description = "Nombre o parte del nombre a buscar", required = true)
            @RequestParam("nombre") String nombre) {
        try {
            logger.info("Buscando insumos por nombre: {}", nombre);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(articuloInsumoService.buscarPorNombre(nombre));
        } catch (Exception e) {
            logger.error("Error al buscar insumos por nombre: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener insumos por nombre.\"}");
        }
    }

}