/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.producto;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.producto.insumo.InsumoDTO;
import com.buenSabor.BackEnd.mapper.ArticuloMapper;
import com.buenSabor.BackEnd.models.producto.Articulo;

import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.services.producto.ArticuloInsumoService;
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
@RequestMapping("api/insumos")
@Tag(name = "ArticuloInsumo", description = "Operaciones relacionadas con entidad ArticuloInsumo")
public class ArticuloInsumoController extends BeanControllerImpl<ArticuloInsumo,ArticuloInsumoService>{
    
    @Autowired
    private ArticuloInsumoService articuloInsumoService;

    @Autowired
    private ArticuloMapper articuloMapper;

    

  @Operation(summary = "Guardar una nuevo insumo a partir de un DTO")
    @PostMapping("/crear")
    public ResponseEntity<?> saveFromDTO(@RequestBody InsumoDTO dto) {
        try {
            Long id = dto.getStockArticuloInsumo().getSucursalId();
            ArticuloInsumo savedInsumo = 
                    articuloInsumoService.crearInsumo(articuloMapper.toEntity(dto),id);
            return ResponseEntity.status(HttpStatus.OK).body(savedInsumo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("{\"error\":\"Error al guardar la manufacturado.\"}");
        }
    }
    

   
}