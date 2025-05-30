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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author oscarloha
 */
@RestController
@RequestMapping("api/sucursales")
@Tag(name = "Sucursales", description = "Operaciones relacionadas con entidad Sucursal")
public class SucursalController extends BeanControllerImpl<Sucursal,SucursalService>{
    
    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalMapper sucursalMapper;

    @Operation(summary = "Guardar una nueva sucursal a partir de un DTO")
    @PostMapping("/crear")
    public ResponseEntity<?> saveFromDTO(@RequestBody SucursalDTO dto) {
        try {
            Long id = dto.getEmpresaId();
            Sucursal savedSucursal = sucursalService.crearSucursal(sucursalMapper.toEntity(dto),id);
            return ResponseEntity.status(HttpStatus.OK).body(savedSucursal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("{\"error\":\"Error al guardar la sucursal.\"}");
        }
    }

   @Operation(summary = "Listar todas las sucursales")
    @GetMapping("/listar")
    public ResponseEntity<?> findAll() throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("{\"error\":\"Error. Intente más tarde.\"}");
        }
    }
//
//    @Operation(summary = "Obtener una sucursal por su ID")
//    @GetMapping("/one/{id}")
//    public ResponseEntity<?> getById(@PathVariable Long id) {
//        try {
//            Sucursal sucursal = sucursalService.findById(id);
//            return ResponseEntity.ok(sucursal );
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                 .body("{\"error\":\"Sucursal no encontrada.\"}");
//        }
//    }
}
    

