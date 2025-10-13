/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.producto;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.producto.manufacturado.ArticuloManufacturadoDTO;

import com.buenSabor.BackEnd.models.producto.ArticuloManufacturado;
import com.buenSabor.BackEnd.services.producto.ArticuloManufacturadoService;
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
 * Controlador REST para gestionar operaciones CRUD de Artículos Manufacturados.
 * Utiliza DTOs y mappers para la transferencia de datos.
 * 
 * @author oscarloha
 */
@RestController
@RequestMapping("api/ArticuloManufacturado")
@Tag(name = "ArticuloManufacturado", description = "Operaciones relacionadas con entidad ArticuloManufacturado")
public class ArticuloManufacturadoController extends
        BeanControllerImpl<ArticuloManufacturado,ArticuloManufacturadoService>{

    private static final Logger logger = LoggerFactory.getLogger(ArticuloManufacturadoController.class);

    @Autowired
    private ArticuloManufacturadoService articuloManufacturadoService;

    @Operation(summary = "Crear un nuevo artículo manufacturado", 
               description = "Crea un artículo manufacturado con sus insumos y detalles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Manufacturado creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/crear")
    public ResponseEntity<?> saveFromDTO(
            @Parameter(description = "Datos del manufacturado a crear", required = true)
            @Valid @RequestBody ArticuloManufacturadoDTO dto) {
        try {
            logger.info("Creando artículo manufacturado: {}", dto.getNombre());
            ArticuloManufacturadoDTO savedManufacturado = articuloManufacturadoService.crearManufacturado(dto);
            logger.info("Manufacturado creado exitosamente con ID: {}", savedManufacturado.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedManufacturado);
        } catch (RuntimeException e) {
            logger.warn("Error de validación al crear manufacturado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al guardar manufacturado: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            logger.error("Error interno al crear manufacturado: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error interno al crear el manufacturado: " + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Actualizar un artículo manufacturado", 
               description = "Actualiza los datos de un manufacturado existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Manufacturado actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Manufacturado no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateFromDTO(
            @Parameter(description = "ID del manufacturado a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados del manufacturado", required = true)
            @Valid @RequestBody ArticuloManufacturadoDTO dto
    ) {
        try {
            logger.info("Actualizando manufacturado con ID: {}", id);
            ArticuloManufacturadoDTO updatedDTO =
                    articuloManufacturadoService.actualizarManufacturado(id, dto);
            logger.info("Manufacturado con ID {} actualizado exitosamente", id);
            return ResponseEntity.ok(updatedDTO);
        } catch (RuntimeException e) {
            logger.warn("Error al actualizar manufacturado ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al actualizar manufacturado: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            logger.error("Error interno al actualizar manufacturado ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error interno al actualizar el manufacturado: " + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Eliminar un manufacturado (soft delete)", 
               description = "Realiza una eliminación lógica marcando existe=false")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Manufacturado eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Manufacturado no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del manufacturado a eliminar", required = true)
            @PathVariable Long id) {
        try {
            logger.info("Eliminando manufacturado con ID: {}", id);
            ArticuloManufacturado manufacturado = articuloManufacturadoService.eliminarLogico(id);
            logger.info("Manufacturado con ID {} eliminado exitosamente", id);
            return ResponseEntity.ok(manufacturado);
        } catch (RuntimeException e) {
            logger.warn("Manufacturado con ID {} no encontrado: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            logger.error("Error al eliminar manufacturado ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al eliminar el manufacturado: " + e.getMessage() + "\"}");
        }
    }
    @Operation(summary = "Listar manufacturados activos", 
               description = "Retorna todos los manufacturados marcados como existentes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/existentes")
    public ResponseEntity<?> getManufacturadosExistentes() {
        try {
            logger.info("Obteniendo manufacturados existentes");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(articuloManufacturadoService.findByExisteTrue());
        } catch (Exception e) {
            logger.error("Error al obtener manufacturados existentes: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener manufacturados existentes.\"}");
        }
    }

    @Operation(summary = "Buscar manufacturados por subcategoría", 
               description = "Retorna manufacturados filtrados por ID de subcategoría")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/subcategoria/{idSubcategoria}")
    public ResponseEntity<?> getManufacturadoBySubcategoria(
            @Parameter(description = "ID de la subcategoría", required = true)
            @PathVariable Long idSubcategoria) {
        try {
            logger.info("Buscando manufacturados por subcategoría ID: {}", idSubcategoria);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(articuloManufacturadoService.buscarPorSubcategoria(idSubcategoria));
        } catch (Exception e) {
            logger.error("Error al buscar manufacturados por subcategoría: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener manufacturados por subcategoría.\"}");
        }
    }

    @Operation(summary = "Buscar manufacturados por nombre", 
               description = "Retorna manufacturados que contengan el texto en su nombre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/nombre")
    public ResponseEntity<?> getManufacturadosByNombre(
            @Parameter(description = "Nombre o parte del nombre a buscar", required = true)
            @RequestParam("nombre") String nombre) {
        try {
            logger.info("Buscando manufacturados por nombre: {}", nombre);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(articuloManufacturadoService.buscarPorNombre(nombre));
        } catch (Exception e) {
            logger.error("Error al buscar manufacturados por nombre: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener manufacturados por nombre.\"}");
        }
    }

}