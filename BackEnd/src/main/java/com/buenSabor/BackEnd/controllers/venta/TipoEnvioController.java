package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.venta.TipoEnvio;
import com.buenSabor.BackEnd.services.venta.TipoEnvioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tipoEnvios")
@Tag(name = "TipoEnvio", description = "Operaciones relacionadas con entidad TipoEnvio")
public class TipoEnvioController extends BeanControllerImpl<TipoEnvio, TipoEnvioService> {
}
