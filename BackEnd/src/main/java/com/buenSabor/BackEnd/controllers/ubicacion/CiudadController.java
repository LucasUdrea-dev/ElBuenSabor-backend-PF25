package com.buenSabor.BackEnd.controllers.ubicacion;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.ubicacion.Ciudad;
import com.buenSabor.BackEnd.services.ubicacion.CiudadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/Ciudad")
@Tag(name = "Ciudad", description = "Operaciones relacionadas con entidad Ciudad")
public class CiudadController extends BeanControllerImpl<Ciudad, CiudadService> {

}
