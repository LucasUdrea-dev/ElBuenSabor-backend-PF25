/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.company;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.company.empresa.EmpresaDTO;
import com.buenSabor.BackEnd.dto.company.empresa.EmpresaResponseDTO;
import com.buenSabor.BackEnd.mapper.EmpresaMapper;
import com.buenSabor.BackEnd.models.company.Empresa;
import com.buenSabor.BackEnd.services.company.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("api/empresas")
@Tag(name = "Empresas", description = "Operaciones relacionadas con entidad Empresa")
public class EmpresaController extends BeanControllerImpl<Empresa, EmpresaService> {

    //constructor sobrecargado 
    @Autowired
    private EmpresaMapper empresaMapper;
    @Autowired
    private EmpresaService empresaService;

    @Operation(summary = "Guardar una nueva empresa a partir de un DTO")
    @PostMapping("/crear")
    public ResponseEntity<?> saveFromDTO(@RequestBody EmpresaDTO dto) {
        try {
            Empresa empresa = empresaMapper.toEntity(dto);
            return ResponseEntity.status(HttpStatus.OK).body(service.save(empresa));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error al guardar la empresa.\"}");
        }
    }

    @Operation(summary = "Ver una empresa por ID")
    @GetMapping("/ver/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        try {
            Empresa empresa = empresaService.findById(id);
            if (empresa == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Empresa no encontrada.\"}");
            }

            return ResponseEntity.status(HttpStatus.OK).body(empresaMapper.toDto(empresa));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al obtener la empresa.\"}");
        }
    }

    @Operation(summary = "ver empresa completa")
    @GetMapping("/full")
    public ResponseEntity<?> showAll() {
        try {
            List<Empresa> empresas = empresaService.findAll();
            List<EmpresaResponseDTO> empresaResponseDTOs = empresas.stream()
                    .map(empresaMapper::toDtoFull)
                    .toList();
            return ResponseEntity.status(HttpStatus.OK).body(empresaResponseDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al listar las empresas.\"}");
        }
    }
//    @Operation(summary = "actualizar empresa completa")
//    @PutMapping("/update/{id}")
//   public ResponseEntity<?> updateEmpresa(@PathVariable Long id, @RequestBody EmpresaResponseDTO dto) {
//    try {
//        Empresa empresaExistente = empresaService.findById(id);
//        if (empresaExistente == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("{\"error\":\"Empresa no encontrada.\"}");
//        }
//
//        // Mapear el DTO a entidad, manteniendo el ID
//        Empresa empresaActualizada = empresaMapper.toEntityFromResponse(dto);
//        empresaActualizada.setId(id); // Aseguramos que el ID se mantenga
//
//        // Guardar cambios
//        empresaService.save(empresaActualizada);
//
//        return ResponseEntity.ok().body("{\"message\":\"Empresa actualizada con éxito.\"}");
//    } catch (Exception e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body("{\"error\":\"Error al actualizar la empresa.\"}");
//    }
//}

}
