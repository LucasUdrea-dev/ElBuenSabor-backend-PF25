/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.ubicacion;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import com.buenSabor.BackEnd.services.ubicacion.DireccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
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
@RequestMapping("api/Direccion")
@Tag(name = "Direccion", description = "Operaciones relacionadas con entidad Direccion")
public class DireccionController extends BeanControllerImpl<Direccion,DireccionService> {
    
  private final DireccionService direccionService;

    @Autowired 
    public DireccionController(DireccionService direccionService) {
        this.direccionService = direccionService;
    }

    @Operation(summary = "Obtener todas las direcciones de un usuario por su ID")
    @GetMapping("/usuario/{idUsuario}") 
    public ResponseEntity<?> getDireccionesByUserId(@PathVariable Long idUsuario) {
        try {
            
            List<Direccion> direcciones = direccionService.findDireccionesByUserId(idUsuario);

            if (direcciones.isEmpty()) {
              
                return ResponseEntity.ok().body("{\"mensaje\":\"El usuario no tiene direcciones registradas.\"}");
            }
            
            
            return ResponseEntity.ok(direcciones); 
            
        } catch (Exception e) {
            
            if (e.getMessage().contains("no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"" + e.getMessage() + "\"}");
            }
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error interno al obtener las direcciones.\"}");
        }
    }
}