package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.venta.MercadoPago;
import com.buenSabor.BackEnd.services.venta.MercadoPagoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/mercadoPago")
@CrossOrigin(origins = "*")
@Tag(name = "MercadoPago", description = "Operaciones relacionadas con entidad MercadoPago")
public class MercadoPagoController extends BeanControllerImpl<MercadoPago, MercadoPagoService> {
}
