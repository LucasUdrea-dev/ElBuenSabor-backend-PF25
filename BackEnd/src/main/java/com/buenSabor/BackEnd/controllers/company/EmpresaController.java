/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.company;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.company.empresa.EmpresaDTO;
import com.buenSabor.BackEnd.mapper.EmpresaMapper;
import com.buenSabor.BackEnd.models.company.Empresa;
import com.buenSabor.BackEnd.services.company.EmpresaService;
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
@RequestMapping("api/empresas")
@Tag(name = "Empresas", description = "Operaciones relacionadas con entidad Empresa")
public class EmpresaController {

    @Autowired
    private EmpresaMapper empresaMapper;

    @Autowired
    private EmpresaService empresaService;

    @Operation(summary = "Ver una empresa por ID (DTO)")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Empresa empresa = empresaService.findById(id);
            if (empresa == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Empresa no encontrada.\"}");
            }
            return ResponseEntity.ok(empresaMapper.toDto(empresa));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("{\"error\":\"Error al obtener la empresa.\"}");
        }
    }

    @Operation(summary = "Listar todas las empresas (DTO)")
    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        try {
            List<EmpresaDTO> empresas = empresaMapper.toDtoList(empresaService.findAll());
            return ResponseEntity.ok(empresas);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("{\"error\":\"Error al listar las empresas.\"}");
        }
    }

    @Operation(summary = "Guardar una nueva empresa")
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody EmpresaDTO dto) {
        try {
            Empresa nuevaEmpresa = empresaMapper.toEntity(dto);
            Empresa guardada = empresaService.save(nuevaEmpresa);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(empresaMapper.toDto(guardada));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("{\"error\":\"Error al guardar la empresa.\"}");
        }
    }

    @Operation(summary = "Eliminar una empresa por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            empresaService.delete(id);
            return ResponseEntity.ok("{\"message\":\"Empresa eliminada con éxito.\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("{\"error\":\"Error al eliminar la empresa.\"}");
        }
    }

    @Operation(summary = "Actualizar una empresa")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EmpresaDTO dto) {
        try {
            Empresa empresaExistente = empresaService.findById(id);
            if (empresaExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Empresa no encontrada.\"}");
            }

            // Actualización controlada solo de campos modificables
            if (dto.getNombre() != null) empresaExistente.setNombre(dto.getNombre());
            if (dto.getRazonSocial() != null) empresaExistente.setRazonSocial(dto.getRazonSocial());
            if (dto.getCuil() != null) empresaExistente.setCuil(dto.getCuil());
            empresaExistente.setExiste(dto.isExiste());

            Empresa actualizada = empresaService.save(empresaExistente);
            return ResponseEntity.ok(empresaMapper.toDto(actualizada));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("{\"error\":\"Error al actualizar la empresa.\"}");
        }
    }

    @Operation(summary = "Listar empresas paginadas (DTO)")
    @GetMapping("/paged")
    public ResponseEntity<?> getPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<EmpresaDTO> pageResult = empresaService.findAll(pageable)
                    .map(empresaMapper::toDto);
            return ResponseEntity.ok(pageResult);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("{\"error\":\"Error al obtener las empresas paginadas.\"}");
        }
    }
    
    
}
