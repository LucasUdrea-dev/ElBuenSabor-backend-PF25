/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.producto;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.producto.categoria.CategoriaDTO;
import com.buenSabor.BackEnd.models.producto.Categoria;
import com.buenSabor.BackEnd.services.producto.CategoriaService;
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
@RequestMapping("api/Categoria")
@Tag(name = "Categoria", description = "Operaciones relacionadas con entidad Categoria")
public class CategoriaController extends BeanControllerImpl<Categoria,CategoriaService>{
    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Guardar una Categoria a partir de un DTO")
    @PostMapping("/crear")
    public ResponseEntity<?> saveFromDTO(@RequestBody CategoriaDTO dto) {
        try {
            CategoriaDTO savedCategoria = categoriaService.crearCategoria(dto);
            return ResponseEntity.status(HttpStatus.OK).body(savedCategoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al guardar categoria: " + e.getMessage() + "\", " +
                            "\"causa\":\"" + (e.getCause() != null ? e.getCause().getMessage() : "No hay causa específica") + "\"}");
        }
    }

    @Operation(summary = "Actualizar un Categoria existente a partir de un DTO")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateFromDTO( @PathVariable Long id,   @RequestBody CategoriaDTO dto) {
        try {
            CategoriaDTO updatedDTO = categoriaService.actualizarCategoria(id, dto);
            return ResponseEntity.ok(updatedDTO);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al actualizar categoria: " + e.getMessage() + "\", " +
                            "\"causa\":\"" + (e.getCause() != null ? e.getCause().getMessage() : "No hay causa específica") + "\"}");
        }
    }

    @Operation(summary = "Obtener categorias filtradas por nombre")
    @GetMapping("/nombre")
    public ResponseEntity<?> getCategoriasByNombre(@RequestParam("nombre") String nombre) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(categoriaService.buscarPorNombre(nombre));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al obtener categorias filtradas por nombre.\"}");
        }
    }

    @Operation(summary = "Obtener categorias si es para elaborar")
    @GetMapping("/esParaElaborar")
    public ResponseEntity<?> getCategoriasByEsParaElaborar() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(categoriaService.getByEsParaElaborar());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al obtener categorias para elaborar.\"}");
        }
    }

    @GetMapping("/ventas")
    public ResponseEntity<?> obtenerTodasCategorias(){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoriaService.findParaVenta());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Intente más tarde.\"}");
        }

    }

    /*@Operation(summary = "Borrado lógico de una Categoria (marca existe=false)")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            Categoria categoria = categoriaService.eliminarLogico(id);
            return ResponseEntity.ok(categoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }

    }
    */

}
