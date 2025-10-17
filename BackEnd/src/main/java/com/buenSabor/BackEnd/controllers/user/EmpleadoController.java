package com.buenSabor.BackEnd.controllers.user;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.enums.TypeRol;
import com.buenSabor.BackEnd.models.user.Empleado;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.repositories.user.EmpleadoRepository;
import com.buenSabor.BackEnd.services.user.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/empleados")
@Tag(name = "Empleado", description = "Operaciones relacionadas con entidad Empleado")
public class EmpleadoController extends BeanControllerImpl<Empleado, EmpleadoService> {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Operation(summary = "Obtener todos los empleados (Cocinero, Cajero y Delivery)")
    @GetMapping("/getEmpleados")
    public List<Usuario> getEmpleadosPorRoles() {
        List<TypeRol> roles = List.of(TypeRol.COCINERO, TypeRol.CAJERO, TypeRol.DELIVERY);
        return empleadoRepository.findByTiposRol(roles);
    }
}
