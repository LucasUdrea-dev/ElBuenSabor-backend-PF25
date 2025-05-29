package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.venta.Promocion;
import com.buenSabor.BackEnd.services.venta.PromocionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/promociones")
@CrossOrigin(origins = "*")
@Tag(name = "Promocion", description = "Operaciones relacionadas con entidad Promocion")
public class PromocionController extends BeanControllerImpl<Promocion, PromocionService> {
}
