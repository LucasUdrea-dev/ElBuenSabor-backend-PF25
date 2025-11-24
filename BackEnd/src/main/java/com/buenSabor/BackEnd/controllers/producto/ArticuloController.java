/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.producto;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloResponseDTO;
import com.buenSabor.BackEnd.mapper.ArticuloMapper;

import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.services.producto.ArticuloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/articulo")
@Tag(name = "Articulo", description = "Operaciones relacionadas con entidad Articulo")
public class ArticuloController extends BeanControllerImpl<Articulo, ArticuloService> {

    @Autowired
    private ArticuloService articuloService;

    @Autowired
    private ArticuloMapper articuloMapper;

    @Operation(summary = "Obtener artículos que existen")
    @GetMapping("/existentes")
    public ResponseEntity<?> getArticulosExistentes() {
        try {
            List<ArticuloResponseDTO> dtos = articuloService.findByExisteTrue().stream()
                    .map(articuloMapper::toResponseDto)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al obtener artículos existentes.\"}");
        }
    }

    @Operation(summary = "Obtener artículos para elaborar")
    @GetMapping("/para_elaborar")
    public ResponseEntity<?> getArticulosParaElaborar() {
        try {
            List<ArticuloResponseDTO> dtos = articuloService.findByEsParaElaborarTrue().stream()
                    .map(articuloMapper::toResponseDto)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al obtener artículos para elaborar.\"}");
        }
    }

    @Operation(summary = "Obtener artículos para elaborar que existen")
    @GetMapping("/para_elaborar_y_existentes")
    public ResponseEntity<?> getArticulosParaElaborarExistentes() {
        try {
            List<ArticuloResponseDTO> dtos = articuloService.buscarArticuloSiEsParaElaborarYExiste().stream()
                    .map(articuloMapper::toResponseDto)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al obtener artículos para elaborar existentes.\"}");
        }
    }

    @Operation(summary = "Obtener articulos para vender y disponibles")
    @GetMapping("/venta")
    public ResponseEntity<?> obtenerArticulosParaVender() {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(articuloService.listarDisponiblesYParaVenta());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Intente más tarde.\"}");
        }

    }

    @Operation(summary = "Obtener articulos para incluir en promos")
    @GetMapping("/promos")
    public ResponseEntity<?> obtenerArticulosParaPromos() {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(articuloService.listarParaPromos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Intente más tarde.\"}");
        }

    }
}
