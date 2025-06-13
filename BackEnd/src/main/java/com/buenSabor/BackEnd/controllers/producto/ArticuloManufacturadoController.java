/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.producto;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.producto.manufacturado.ArticuloManufacturadoDTO;
import com.buenSabor.BackEnd.mapper.ArticuloMapper;

import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturado;
import com.buenSabor.BackEnd.services.producto.ArticuloManufacturadoService;
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

    @Operation(summary = "Actualizar un ArticuloManufacturado existente a partir de un DTO")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateFromDTO(
            @PathVariable Long id,
            @RequestBody ArticuloManufacturadoDTO dto
    ) {
        try {
            ArticuloManufacturadoDTO updatedDTO =
                    articuloManufacturadoService.actualizarManufacturado(id, dto);
            return ResponseEntity.ok(updatedDTO);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al actualizar manufacturado: " + e.getMessage() + "\", " +
                            "\"causa\":\"" + (e.getCause() != null ? e.getCause().getMessage() : "No hay causa específica") + "\"}");
        }
    }

    @Operation(summary = "Borrado lógico de un articulo Manufacturado (marca existe=false)")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            ArticuloManufacturado manufacturado = articuloManufacturadoService.eliminarLogico(id);
            return ResponseEntity.ok(manufacturado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }

    }
    @Operation(summary = "Obtener articulos manufacturados que existen")
    @GetMapping("/existentes")
    public ResponseEntity<?> getManufacturadosExistentes() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(articuloManufacturadoService.findByExisteTrue());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al obtener insumos existentes.\"}");
        }
    }

    @Operation(summary = "Obtener articulos manufacturados filtrados por subcategoria")
    @GetMapping("/subcategoria")
    public ResponseEntity<?> getManufacturadoBySubcategoria(@PathVariable Long idSubcategoria) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(articuloManufacturadoService.buscarPorSubcategoria(idSubcategoria));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al obtener articulos manufacturados filtrados por subcategorias.\"}");
        }
    }

    @Operation(summary = "Obtener articulos manufacturados filtrados por nombre")
    @GetMapping("/nombre")
    public ResponseEntity<?> getManufacturadosByNombre(@RequestParam("nombre") String nombre) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(articuloManufacturadoService.buscarPorNombre(nombre));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al obtener manufacturados filtrados por nombre.\"}");
        }
    }

}