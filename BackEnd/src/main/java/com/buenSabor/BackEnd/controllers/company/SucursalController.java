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
@Tag(name = "Sucursales", description = "Operaciones relacionadas con entidad Sucursal")
public class SucursalController extends BeanControllerImpl<Sucursal, SucursalService> {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalMapper sucursalMapper;

    @Operation(summary = "Guardar una nueva sucursal a partir de un DTO")
    @PostMapping("/crear")
    public ResponseEntity<?> saveSucursal(@RequestBody SucursalDTO dto) {
        try {
            Long empresaId = dto.getEmpresaId();
            Sucursal savedSucursal = sucursalService.crearSucursal(sucursalMapper.toEntity(dto), empresaId);
            return ResponseEntity.ok(savedSucursal);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"Error al guardar la sucursal.\"}");
        }
    }

    @Operation(summary = "Listar todas las sucursales")
    @GetMapping("/listar")
    public ResponseEntity<?> findAllSucursales() {
        try {
            List<SucursalDTO> sucursalDtos = sucursalMapper.toDtoList(sucursalService.findAll());
            return ResponseEntity.ok(sucursalDtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"Error al obtener las sucursales.\"}");
        }
    }

    @Operation(summary = "Obtener una sucursal por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getSucursalById(@PathVariable Long id) {
        try {
            SucursalDTO sucursal = sucursalMapper.toDto(sucursalService.findById(id));
            return sucursal != null
                    ? ResponseEntity.ok(sucursal)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Sucursal no encontrada.\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"Error al obtener la sucursal.\"}");
        }
    }

    @Operation(summary = "Actualizar sucursal")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSucursal(@PathVariable Long id, @RequestBody SucursalDTO dto) {
        try {
            Sucursal sucursalExistente = sucursalService.findById(id);
            if (sucursalExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Sucursal no encontrada.\"}");
            }

            // Partial Update (avoid null overwrites)
            if (dto.getNombre() != null) sucursalExistente.setNombre(dto.getNombre());
            if (dto.getHoraApertura() != null) sucursalExistente.setHoraApertura(dto.getHoraApertura());
            if (dto.getHoraCierre() != null) sucursalExistente.setHoraCierre(dto.getHoraCierre());
            if (dto.getExiste() != null) sucursalExistente.setExiste(dto.getExiste());

            sucursalService.save(sucursalExistente);
            return ResponseEntity.ok("{\"message\":\"Sucursal actualizada con éxito.\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"Error al actualizar la sucursal.\"}");
        }
    }

    @Operation(summary = "Eliminar una sucursal")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSucursal(@PathVariable Long id) {
        try {
            sucursalService.delete(id);
            return ResponseEntity.ok("{\"message\":\"Sucursal eliminada con éxito.\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"Error al eliminar la sucursal.\"}");
        }
    }

    @Operation(summary = "Listar sucursales paginadas")
    @GetMapping("/paged")
    public ResponseEntity<?> getPagedSucursales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<SucursalDTO> sucursalDTOPage = sucursalService.findAll(pageable).map(sucursalMapper::toDto);
            return ResponseEntity.ok(sucursalDTOPage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"Error al obtener las sucursales paginadas.\"}");
        }
    }
}
