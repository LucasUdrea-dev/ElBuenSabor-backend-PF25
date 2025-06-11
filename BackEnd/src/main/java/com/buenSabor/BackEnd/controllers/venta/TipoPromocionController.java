package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.venta.TipoPromocion;
import com.buenSabor.BackEnd.services.venta.TipoPromocionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tipoPromociones")
@CrossOrigin(origins = "*")
@Tag(name = "TipoPromocion", description = "Operaciones relacionadas con entidad TipoPromocion")
public class TipoPromocionController extends BeanControllerImpl<TipoPromocion, TipoPromocionService> {
}
