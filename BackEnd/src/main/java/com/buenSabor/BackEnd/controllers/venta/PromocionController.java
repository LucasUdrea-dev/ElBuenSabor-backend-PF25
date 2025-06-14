package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.venta.Promocion;
import com.buenSabor.BackEnd.services.venta.PromocionService;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/promociones")
@Tag(name = "Promocion", description = "Operaciones relacionadas con entidad Promocion")
public class PromocionController extends BeanControllerImpl<Promocion, PromocionService> {

    @Autowired
    private PromocionService promocionService;

    @GetMapping("/venta")
    public ResponseEntity<?> obtenerPromocionesParaVender(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(promocionService.obtenerParaVenta());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al obtener las promociones para venta: " + e.getMessage());
        }
    }

}
