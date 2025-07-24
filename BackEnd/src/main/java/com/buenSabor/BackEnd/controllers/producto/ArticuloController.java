/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.producto;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.services.producto.ArticuloService;

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
@RequestMapping("api/articulo")
@Tag(name = "Articulo", description = "Operaciones relacionadas con entidad Articulo")
public class ArticuloController extends BeanControllerImpl<Articulo,ArticuloService>{
    
    @Autowired
    private ArticuloService articuloService;

    @GetMapping("/venta")
    public ResponseEntity<?> obtenerArticulosParaVender(){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(articuloService.listarDisponiblesYParaVenta());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Intente más tarde.\"}");
        }

    }

    @GetMapping("/promos")
    public ResponseEntity<?> obtenerArticulosParaPromos(){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(articuloService.listarParaPromos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Intente más tarde.\"}");
        }

    }

}
