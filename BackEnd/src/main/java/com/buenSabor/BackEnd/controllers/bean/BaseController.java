
package com.buenSabor.BackEnd.controllers.bean;

import com.buenSabor.BackEnd.mapper.BaseMapper;
import com.buenSabor.BackEnd.models.Base;
import com.buenSabor.BackEnd.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Controlador Base Genérico para operaciones CRUD de Entidades y DTOs.
 * * @param <E> Entidad (ej: Empresa)
 * @param <R> Response DTO Simple (ej: Listados, Paginación)
 * @param <D> Detail DTO (ej: GetById, Respuesta con Cascada/Detalle)
 * @param <C> Create DTO
 * @param <U> Update DTO
 * @param <S> Servicio (Debe extender BaseService)
 * @param <M> Mapper (Debe extender BaseMapper)
 */
public abstract class BaseController<
    E extends Base, 
    R,              
    D,              
    C,              
    U,              
    S extends BaseService<E, ? extends Serializable>, 
    M extends BaseMapper<E, C, R, D, U> 
> {

    @Autowired
    protected S service;
    
    @Autowired
    protected M mapper;

    // --- 1. OBTENER POR ID (DTO DE DETALLE) ---
    
    @Operation(summary = "Obtener por ID (Detail DTO - Cascada)", responses = {
            @ApiResponse(responseCode = "200", description = "Encontrado"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            E entity = service.findById(id); 
            // Usa el DTO de Detalle (D)
            return ResponseEntity.ok(mapper.toDetailDto(entity)); 
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Registro no encontrado con ID: " + id + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error interno al obtener el registro.\"}");
        }
    }

    // --- 2. LISTAR TODOS (DTO SIMPLE) ---
    
    @Operation(summary = "Listar todos (Response DTO - Simple)")
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<E> entities = service.findAll(); 
            // Usa el DTO de Respuesta Simple (R)
            List<R> dtos = mapper.toResponseDtoList(entities);
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error interno al listar los registros.\"}");
        }
    }
    
    // --- 3. CREAR (CREATE DTO) ---
    
    @Operation(summary = "Crear registro (CreateDTO)", responses = {
            @ApiResponse(responseCode = "201", description = "Creado")
    })
    @PostMapping("") 
    public ResponseEntity<?> save(@Valid @RequestBody C createDto) { 
        try {
            // DTO -> Entidad
            E entity = mapper.toEntity(createDto); 
            E savedEntity = service.save(entity);
            // Retorna la Entidad Guardada como DTO de Detalle (D)
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDetailDto(savedEntity)); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al crear el registro: " + e.getMessage() + "\"}");
        }
    }

    // --- 4. ACTUALIZAR (UPDATE DTO) ---
    
    @Operation(summary = "Actualizar registro (UpdateDTO)", responses = {
            @ApiResponse(responseCode = "200", description = "Actualizado"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody U updateDto) { 
        try {
            // Busca la Entidad Existente
            E entity = service.findById(id); 
            
            // Mapea Update DTO sobre la Entidad Existente (usando @MappingTarget)
            mapper.updateFromDto(updateDto, entity);

            E updatedEntity = service.save(entity); 
            
            // Retorna la Entidad Actualizada como DTO de Detalle (D)
            return ResponseEntity.ok(mapper.toDetailDto(updatedEntity));
        } catch (NoSuchElementException e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Registro no encontrado para actualizar con ID: " + id + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al actualizar el registro: " + e.getMessage() + "\"}");
        }
    }
    
    // --- 5. ELIMINAR ---
    
    @Operation(summary = "Eliminar por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Eliminado"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.delete(id); 
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoSuchElementException e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"No se puede eliminar. Registro no encontrado con ID: " + id + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error interno al eliminar el registro.\"}");
        }
    }

    // --- 6. PAGINACIÓN (DTO SIMPLE) ---
    
    @Operation(summary = "Listar paginado (Response DTO - Simple)")
    @GetMapping("/paged")
    public ResponseEntity<?> getPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            
            // Obtener Page<Entidad>
            Page<E> entitiesPage = service.findAll(pageable);
            
            // Convertir Page<Entidad> a Page<DTO Simple (R)>
            Page<R> dtosPage = entitiesPage.map(mapper::toResponseDto);
            
            return ResponseEntity.ok(dtosPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error interno al obtener los registros paginados.\"}");
        }
    }
}