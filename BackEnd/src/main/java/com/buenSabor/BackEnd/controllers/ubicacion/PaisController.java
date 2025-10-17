package com.buenSabor.BackEnd.controllers.ubicacion;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.ubicacion.Pais;
import com.buenSabor.BackEnd.services.ubicacion.PaisService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/Pais")
@Tag(name = "Pais", description = "Operaciones relacionadas con entidad Pais")
public class PaisController extends BeanControllerImpl<Pais, PaisService> {

}
