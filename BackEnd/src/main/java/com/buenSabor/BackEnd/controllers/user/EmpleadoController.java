package com.buenSabor.BackEnd.controllers.user;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.seguridad.registro.EmpleadoRegistroDTO;
import com.buenSabor.BackEnd.dto.seguridad.registro.UsuarioRegistroDTO;
import com.buenSabor.BackEnd.dto.user.empleado.EmpleadoDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import com.buenSabor.BackEnd.enums.TypeRol;
import com.buenSabor.BackEnd.mapper.UserAuthenticationMapper;
import com.buenSabor.BackEnd.models.user.Empleado;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.repositories.user.EmpleadoRepository;
import com.buenSabor.BackEnd.services.seguridad.UserAuthenticationService;
import com.buenSabor.BackEnd.services.user.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/empleados")
@Tag(name = "Empleado", description = "Operaciones relacionadas con entidad Empleado")
public class EmpleadoController extends BeanControllerImpl<Empleado, EmpleadoService> {
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private UserAuthenticationService userAuthService;

    @Operation(summary = "Obtener todos los empleados (Cocinero, Cajero y Delivery)")
    @GetMapping("/getEmpleados")
    public List<Usuario> getEmpleadosPorRoles() {
        List<TypeRol> roles = List.of(TypeRol.COCINERO, TypeRol.CAJERO, TypeRol.DELIVERY);
        return empleadoRepository.findByTiposRol(roles);
    }

    @Operation(summary = "Crear un nuevo empleado")
    @PostMapping("/crear")
    public ResponseEntity<?> crearEmpleado(@RequestBody EmpleadoRegistroDTO dto) {
        try {
            EmpleadoDTO guardado = userAuthService.crearEmpleado(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error inesperado al crear el empleado.\"}");
        }
    }
}
