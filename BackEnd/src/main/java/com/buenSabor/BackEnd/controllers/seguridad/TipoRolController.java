package com.buenSabor.BackEnd.controllers.seguridad;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.seguridad.TipoRol;
import com.buenSabor.BackEnd.services.seguridad.TipoRolService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tipoRoles")
@CrossOrigin(origins = "*")
@Tag(name = "TipoRoles", description = "Operaciones relacionadas con entidad TipoRol")
public class TipoRolController extends BeanControllerImpl<TipoRol, TipoRolService> {
}
