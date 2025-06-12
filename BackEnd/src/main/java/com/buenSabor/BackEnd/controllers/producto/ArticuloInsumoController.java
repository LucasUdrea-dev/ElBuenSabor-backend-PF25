/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.producto;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.services.producto.ArticuloInsumoService;
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
@RequestMapping("api/insumos")
@Tag(name = "ArticuloInsumo", description = "Operaciones relacionadas con entidad ArticuloInsumo")
public class ArticuloInsumoController extends BeanControllerImpl<ArticuloInsumo,ArticuloInsumoService>{
    
    @Autowired
    private ArticuloInsumoService articuloInsumoService;

    @GetMapping("/paraElaborar")
    public ResponseEntity<?> buscarInsumosParaElaborar(){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(articuloInsumoService.obtenerInsumosParaElaborar());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        
    }

}
