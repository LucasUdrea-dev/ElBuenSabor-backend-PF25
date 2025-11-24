/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.ubicacion;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionDTO;
import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionResponseDTO;
import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import com.buenSabor.BackEnd.mapper.DireccionMapper;
import com.buenSabor.BackEnd.services.ubicacion.DireccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author oscarloha
 */
@RestController
@RequestMapping("api/Direccion")
@Tag(name = "Direccion", description = "Operaciones relacionadas con entidad Direccion")
public class DireccionController extends BeanControllerImpl<Direccion, DireccionService> {

    private final DireccionService direccionService;
    private final DireccionMapper direccionMapper;

    @Autowired
    public DireccionController(DireccionService direccionService, DireccionMapper direccionMapper) {
        this.direccionService = direccionService;
        this.direccionMapper = direccionMapper;
    }

    @Operation(summary = "Obtener todas las direcciones de un usuario por su ID")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> getDireccionesByUserId(@PathVariable Long idUsuario) {
        try {
            List<Direccion> direcciones = direccionService.findDireccionesByUserId(idUsuario);
            List<DireccionResponseDTO> dtos = direcciones.stream()
                    .map(direccionMapper::toResponseDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            if (e.getMessage().contains("no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"" + e.getMessage() + "\"}");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error interno al obtener las direcciones.\"}");
        }
    }

    @Operation(summary = "Crear una dirección para un usuario")
    @PostMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> crearDireccion(@PathVariable Long idUsuario, @RequestBody DireccionDTO dto) {
        try {
            DireccionDTO creada = direccionService.crearDireccionParaUsuario(idUsuario, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (Exception e) {
            if (e.getMessage().contains("no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"" + e.getMessage() + "\"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Obtener una dirección específica de un usuario")
    @GetMapping("/usuario/{idUsuario}/{idDireccion}")
    public ResponseEntity<?> obtenerDireccion(@PathVariable Long idUsuario, @PathVariable Long idDireccion) {
        try {
            DireccionDTO dto = direccionService.obtenerDireccionDeUsuario(idUsuario, idDireccion);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            if (e.getMessage().contains("no encontrado") || e.getMessage().contains("no pertenece")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"" + e.getMessage() + "\"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Actualizar una dirección de un usuario")
    @PutMapping("/usuario/{idUsuario}/{idDireccion}")
    public ResponseEntity<?> actualizarDireccion(@PathVariable Long idUsuario,
            @PathVariable Long idDireccion,
            @RequestBody DireccionDTO dto) {
        try {
            DireccionDTO actualizada = direccionService.actualizarDireccionDeUsuario(idUsuario, idDireccion, dto);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            if (e.getMessage().contains("no encontrado") || e.getMessage().contains("no pertenece")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"" + e.getMessage() + "\"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Eliminar una dirección de un usuario")
    @DeleteMapping("/usuario/{idUsuario}/{idDireccion}")
    public ResponseEntity<?> eliminarDireccion(@PathVariable Long idUsuario, @PathVariable Long idDireccion) {
        try {
            direccionService.eliminarDireccionDeUsuario(idUsuario, idDireccion);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            if (e.getMessage().contains("no encontrado") || e.getMessage().contains("no pertenece")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"" + e.getMessage() + "\"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}