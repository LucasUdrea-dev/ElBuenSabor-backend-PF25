package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.venta.PromocionArticulo;
import com.buenSabor.BackEnd.services.venta.PromocionArticuloService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/promocionArticulos")
@CrossOrigin(origins = "*")
@Tag(name = "PromocionArticulo", description = "Operaciones relacionadas con entidad PromocionArticulo")
public class PromocionArticuloController extends BeanControllerImpl<PromocionArticulo, PromocionArticuloService> {
}
