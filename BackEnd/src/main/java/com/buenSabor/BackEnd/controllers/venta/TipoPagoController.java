package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.venta.TipoPago;
import com.buenSabor.BackEnd.services.venta.TipoPagoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tipoPagos")
@Tag(name = "TipoPago", description = "Operaciones relacionadas con entidad TipoPago")
public class TipoPagoController extends BeanControllerImpl<TipoPago, TipoPagoService> {
}
