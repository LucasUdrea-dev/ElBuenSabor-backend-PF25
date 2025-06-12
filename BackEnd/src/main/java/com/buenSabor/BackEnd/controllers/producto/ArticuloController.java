/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.producto;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloDTO;
import com.buenSabor.BackEnd.dto.producto.insumo.InsumoDTO;
import com.buenSabor.BackEnd.dto.producto.manufacturado.ArticuloManufacturadoDTO;
import com.buenSabor.BackEnd.mapper.ArticuloMapper;

import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.services.producto.ArticuloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("api/articulo")
@Tag(name = "Articulo", description = "Operaciones relacionadas con entidad Articulo")
public class ArticuloController extends BeanControllerImpl<Articulo,ArticuloService>{
    
    @Autowired
    private ArticuloService articuloService;

    @Autowired
    private ArticuloMapper articuloMapper;

    /*@Operation(summary = "Guardar un nuevo artículo (insumo o manufacturado) a partir de un DTO")
    @Schema( description = "Puede ser un ArticuloInsumoDTO o ArticuloManufacturadoDTO"
            ,oneOf = {InsumoDTO.class, ArticuloManufacturadoDTO.class})
    @PostMapping("/crear")
    public ResponseEntity<?> saveFromDTO(@RequestBody ArticuloDTO dto) {
        try {
            Articulo articulo = articuloMapper.toArticulo(dto);
            return ResponseEntity.status(HttpStatus.OK).body(articuloService.guardar(articulo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al guardar el artículo: " + e.getMessage() + "\", " +
                          "\"causa\":\"" + (e.getCause() != null ? e.getCause().getMessage() : "No hay causa específica") + "\"}");
        }
    }
    
    @Operation(summary = "Actualizar un artículo existente DTO")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateArticuloDTO (@PathVariable Long id, @RequestBody ArticuloDTO dto) {
        try {
            Articulo articulo = articuloMapper.toArticulo(dto);
            return ResponseEntity.status(HttpStatus.OK).body(articuloService.actualizar(id, articulo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al actualizar el artículo: " + e.getMessage() + "\", " +
                          "\"causa\":\"" + (e.getCause() != null ? e.getCause().getMessage() : "No hay causa específica") + "\"}");
        }
    }*/

    @Operation(summary = "Obtener artículos que existen")
    @GetMapping("/existentes")
    public ResponseEntity<?> getArticulosExistentes() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(articuloService.findByExisteTrue());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al obtener artículos existentes.\"}");
        }
    }

    @Operation(summary = "Obtener artículos para elaborar")
    @GetMapping("/para_elaborar")
    public ResponseEntity<?> getArticulosParaElaborar() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(articuloService.findByEsParaElaborarTrue());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al obtener artículos para elaborar.\"}");
        }
    }

    @Operation(summary = "Obtener artículos para elaborar que existen")
    @GetMapping("/para_elaborar_y_existentes")
    public ResponseEntity<?> getArticulosParaElaborarExistentes() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(articuloService.buscarArticuloSiEsParaElaborarYExiste());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al obtener artículos para elaborar existentes.\"}");
        }
    }
}
    

