/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.company;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.company.sucursal.SucursalDTO;
import com.buenSabor.BackEnd.mapper.SucursalMapper;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.services.company.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author oscarloha
 */
@RestController
@RequestMapping("api/sucursales")
@Tag(name = "Sucursales", description = "Operaciones relacionadas con la entidad Sucursal")
public class SucursalController {

    private final SucursalService sucursalService;
    private final SucursalMapper sucursalMapper;

    @Autowired
    public SucursalController(SucursalService sucursalService, SucursalMapper sucursalMapper) {
        this.sucursalService = sucursalService;
        this.sucursalMapper = sucursalMapper;
    }

    @Operation(summary = "Guardar una nueva sucursal a partir de un DTO")
    @PostMapping("/crear")
    public ResponseEntity<?> crearSucursal(@RequestBody SucursalDTO dto) {
        try {
            SucursalDTO sucursalGuardada = sucursalService.crearSucursal(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(sucursalGuardada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error inesperado al crear la sucursal.\"}");
        }
    }

    @Operation(summary = "Listar todas las sucursales")
    @GetMapping("/listar")
    public ResponseEntity<?> findAllSucursales() {
        try {
            List<SucursalDTO> dtos = sucursalMapper.toDtoList(sucursalService.findAll());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener las sucursales.\"}");
        }
    }

    @Operation(summary = "Obtener una sucursal por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getSucursalById(@PathVariable Long id) {
        try {
            Sucursal sucursal = sucursalService.findById(id);
            if (sucursal == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Sucursal no encontrada.\"}");
            }
            return ResponseEntity.ok(sucursalMapper.toDto(sucursal));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener la sucursal.\"}");
        }
    }

    @Operation(summary = "Actualizar una sucursal")
    @PutMapping("/{id}")
    public ResponseEntity<SucursalDTO> actualizarSucursal(@PathVariable Long id, @RequestBody SucursalDTO dto) {
        SucursalDTO actualizada = sucursalService.actualizarSucursal(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @Operation(summary = "Eliminar una sucursal")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSucursal(@PathVariable Long id) {
        try {
            Sucursal sucursal = sucursalService.findById(id);
            if (sucursal == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Sucursal no encontrada.\"}");
            }

<<<<<<< HEAD
            sucursalService.eliminarSucursal(id);
=======
            sucursalService.delete(id);
>>>>>>> 971fdac1994dc049088e1c959c85a580132c3c6a
            return ResponseEntity.ok("{\"message\":\"Sucursal eliminada con éxito.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al eliminar la sucursal.\"}");
        }
    }

    @Operation(summary = "Listar sucursales paginadas")
    @GetMapping("/paged")
    public ResponseEntity<?> getPagedSucursales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<SucursalDTO> dtoPage = sucursalService.findAll(pageable)
                    .map(sucursalMapper::toDto);
            return ResponseEntity.ok(dtoPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener las sucursales paginadas.\"}");
        }
    }
}
