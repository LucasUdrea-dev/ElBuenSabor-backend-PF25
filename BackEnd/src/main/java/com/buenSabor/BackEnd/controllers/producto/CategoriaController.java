/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.producto;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.producto.Categoria;
import com.buenSabor.BackEnd.services.producto.CategoriaService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/ventas")
    public ResponseEntity<?> obtenerTodasCategorias(){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoriaService.findParaVenta());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Intente más tarde.\"}");
        }

    }

    @GetMapping("/insumos")
    public ResponseEntity<?> obtenerCategoriasConSubcategoriasParaInsumos(){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoriaService.findParaInsumos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Intente más tarde.\"}" + e.getMessage());
        }

    }

}
