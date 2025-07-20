/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.producto;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.producto.HistoricoStockArticuloInsumo;
import com.buenSabor.BackEnd.services.producto.HistoricoStockArticuloInsumoService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/historicoStock")
@Tag(name = "HistoricoStock", description = "Operaciones relacionadas con entidad HistoricoStockArticuloInsumo")
public class HistoricoStockArticuloInsumoController extends BeanControllerImpl<HistoricoStockArticuloInsumo,HistoricoStockArticuloInsumoService>{
    
    @Autowired
    private HistoricoStockArticuloInsumoService historicoStockArticuloInsumoService;

    @GetMapping("/insumo/{id}")
    public ResponseEntity<?> obtenerHistoricoStockPorInsumo(@PathVariable Long id){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(historicoStockArticuloInsumoService.buscarHistorialPorArticulo(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Intente más tarde.\"}");
        }

    }

}
