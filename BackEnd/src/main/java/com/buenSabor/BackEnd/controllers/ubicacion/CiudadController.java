/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.ubicacion;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.ubicacion.Ciudad;
import com.buenSabor.BackEnd.services.ubicacion.CiudadService;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author oscarloha
 */
@RestController
@RequestMapping("api/Ciudad")
@Tag(name = "Ciudad", description = "Operaciones relacionadas con entidad Ciudad")
public class CiudadController extends BeanControllerImpl<Ciudad,CiudadService>{
    
    @Autowired
    private CiudadService ciudadService;

    @GetMapping("/provincia/{id}")
    public ResponseEntity<?> obtenerCiudadesDeProvincia(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ciudadService.obtenerCiudadPorProvincia(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al buscar las ciudades de la provincia: " + e.getMessage());
        }
    }

}
