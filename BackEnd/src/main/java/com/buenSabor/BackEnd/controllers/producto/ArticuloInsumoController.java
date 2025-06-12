/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.producto;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.producto.insumo.InsumoDTO;
import com.buenSabor.BackEnd.mapper.ArticuloMapper;
import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.services.producto.ArticuloInsumoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author oscarloha
 */
@RestController
@RequestMapping("api/insumos")
@Tag(name = "ArticuloInsumo", description = "Operaciones relacionadas con entidad ArticuloInsumo")
public class ArticuloInsumoController extends BeanControllerImpl<ArticuloInsumo,ArticuloInsumoService>{
    @Autowired
    private ArticuloMapper articuloMapper;
    @Autowired
    private ArticuloInsumoService articuloInsumoService;

    @Operation(summary = "Guardar un nuevo insumo a partir de un DTO")
    @PostMapping("/crear")
    public ResponseEntity<?> saveFromDTO(@RequestBody InsumoDTO dto) {
        try {
            Long id = dto.getStockArticuloInsumo().getSucursalId();
            //Envio DTO - service hace mappeo y devuelv
            ArticuloInsumo savedInsumo =articuloInsumoService.crearInsumo(dto,id);

            dto.setId(savedInsumo.getId());
            dto.getStockArticuloInsumo().setId(savedInsumo.getStockArticuloInsumo().getId());
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al guardar el insumo: " + e.getMessage() + "\", " +
                          "\"causa\":\"" + (e.getCause() != null ? e.getCause().getMessage() : "No hay causa específica") + "\"}");
        }
    }

    @Operation(summary = "Actualizar un insumo existente")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateInsumo(@PathVariable Long id, @RequestBody InsumoDTO dto) {
        try {
            ArticuloInsumo insumo = articuloMapper.toEntity(dto);
            return ResponseEntity.status(HttpStatus.OK).body(articuloInsumoService.actualizar(id, insumo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al actualizar el insumo: " + e.getMessage() + "\", " +
                          "\"causa\":\"" + (e.getCause() != null ? e.getCause().getMessage() : "No hay causa específica") + "\"}");
        }
    }

    @Operation(summary = "Obtener insumos que existen")
    @GetMapping("/existentes")
    public ResponseEntity<?> getInsumosExistentes() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(articuloInsumoService.findByExisteTrue());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al obtener insumos existentes.\"}");
        }
    }

}