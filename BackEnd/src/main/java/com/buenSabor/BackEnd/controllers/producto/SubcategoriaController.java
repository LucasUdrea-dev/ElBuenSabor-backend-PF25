/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.producto;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.producto.categoria.CategoriaDTO;
import com.buenSabor.BackEnd.dto.producto.subcategoria.SubcategoriaDTO;
import com.buenSabor.BackEnd.models.producto.Subcategoria;
import com.buenSabor.BackEnd.services.producto.CategoriaService;
import com.buenSabor.BackEnd.services.producto.SubcategoriaService;
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
@RequestMapping("api/subcategoria")
@Tag(name = "Subcategoria", description = "Operaciones relacionadas con entidad Subcategoria")
public class SubcategoriaController extends BeanControllerImpl<Subcategoria,SubcategoriaService>{

    @Autowired
    private SubcategoriaService subcategoriaService;

    @Operation(summary = "Guardar una Subcategoria a partir de un DTO")
    @PostMapping("/crear")
    public ResponseEntity<?> saveFromDTO(@RequestBody SubcategoriaDTO dto) {
        try {
            SubcategoriaDTO savedSubcategoria = subcategoriaService.crearSubcategoria(dto);
            return ResponseEntity.status(HttpStatus.OK).body(savedSubcategoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al guardar subcategoria: " + e.getMessage() + "\", " +
                            "\"causa\":\"" + (e.getCause() != null ? e.getCause().getMessage() : "No hay causa específica") + "\"}");
        }
    }

    @Operation(summary = "Actualizar una Subcategoria existente a partir de un DTO")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateFromDTO( @PathVariable Long id,   @RequestBody SubcategoriaDTO dto) {
        try {
            SubcategoriaDTO updatedDTO = subcategoriaService.actualizarSubcategoria(id, dto);

            return ResponseEntity.ok(updatedDTO);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al actualizar subcategoria: " + e.getMessage() + "\", " +
                            "\"causa\":\"" + (e.getCause() != null ? e.getCause().getMessage() : "No hay causa específica") + "\"}");
        }
    }

    @Operation(summary = "Obtener subcategorias filtradas por nombre")
    @GetMapping("/nombre")
    public ResponseEntity<?> getSubCategoriasByNombre(@RequestParam("nombre") String nombre) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(subcategoriaService.buscarPorNombre(nombre));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al obtener categorias filtradas por nombre.\"}");
        }
    }

    @Operation(summary = "Obtener subcategorias filtradas por categoria")
    @GetMapping("/nombreCategoria")
    public ResponseEntity<?> getSubCategoriasByNombreCategoria(@RequestParam("nombre") String nombre) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(subcategoriaService.buscarPorNombreCategoria(nombre));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al obtener categorias filtradas por nombre.\"}");
        }
    }

    /*@Operation(summary = "Borrado lógico de una Subcategoria (marca existe=false)")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            Subcategoria subcategoria = subcategoriaService.eliminarLogico(id);
            return ResponseEntity.ok(subcategoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }

    }*/

}
