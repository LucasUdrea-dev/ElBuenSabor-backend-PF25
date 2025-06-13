/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.producto;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.producto.manufacturado.ArticuloManufacturadoDTO;
import com.buenSabor.BackEnd.mapper.ArticuloMapper;

import com.buenSabor.BackEnd.models.producto.ArticuloManufacturado;
import com.buenSabor.BackEnd.services.producto.ArticuloManufacturadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author oscarloha
 */
@RestController
@RequestMapping("api/ArticuloManufacturado")
@Tag(name = "ArticuloManufacturado", description = "Operaciones relacionadas con entidad ArticuloManufacturado")
public class ArticuloManufacturadoController extends
        BeanControllerImpl<ArticuloManufacturado,ArticuloManufacturadoService>{

    @Autowired
    private ArticuloManufacturadoService articuloManufacturadoService;

    @Operation(summary = "Guardar una nuevo Manufacturado a partir de un DTO")
    @PostMapping("/crear")
    public ResponseEntity<?> saveFromDTO(@RequestBody ArticuloManufacturadoDTO dto) {
        try {
            ArticuloManufacturadoDTO savedManufacturado = articuloManufacturadoService.crearManufacturado(dto);
            return ResponseEntity.status(HttpStatus.OK).body(savedManufacturado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al guardar manufacturado: " + e.getMessage() + "\", " +
                            "\"causa\":\"" + (e.getCause() != null ? e.getCause().getMessage() : "No hay causa específica") + "\"}");
        }
    }
}