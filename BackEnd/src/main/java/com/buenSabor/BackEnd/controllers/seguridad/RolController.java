package com.buenSabor.BackEnd.controllers.seguridad;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.seguridad.Rol;
import com.buenSabor.BackEnd.services.seguridad.RolService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/roles")
@Tag(name = "Roles", description = "Operaciones relacionadas con entidad Rol")
public class RolController extends BeanControllerImpl<Rol, RolService> {
}
