package com.buenSabor.BackEnd.controllers.user;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.user.Empleado;
import com.buenSabor.BackEnd.services.user.EmpleadoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/empleados")
@Tag(name = "Empleado", description = "Operaciones relacionadas con entidad Empleado")
public class EmpleadoController extends BeanControllerImpl<Empleado, EmpleadoService> {
}
