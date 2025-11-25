package com.buenSabor.BackEnd.controllers.user;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationResponseDTO;
import com.buenSabor.BackEnd.dto.seguridad.registro.EmpleadoRegistroDTO;
import com.buenSabor.BackEnd.dto.seguridad.registro.UsuarioRegistroDTO;
import com.buenSabor.BackEnd.dto.user.empleado.EmpleadoDTO;
import com.buenSabor.BackEnd.dto.user.empleado.EmpleadoUpdateDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import com.buenSabor.BackEnd.enums.TypeRol;
import com.buenSabor.BackEnd.mapper.UserAuthenticationMapper;
import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.models.user.Empleado;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.repositories.seguridad.UserAuthenticationRepository;
import com.buenSabor.BackEnd.repositories.user.EmpleadoRepository;
import com.buenSabor.BackEnd.services.seguridad.JwtService;
import com.buenSabor.BackEnd.services.seguridad.UserAuthenticationService;
import com.buenSabor.BackEnd.services.user.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/empleados")
@Tag(name = "Empleado", description = "Operaciones relacionadas con entidad Empleado")
public class EmpleadoController extends BeanControllerImpl<Empleado, EmpleadoService> {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoController.class);

    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private UserAuthenticationService userAuthService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserAuthenticationRepository authenticationRepository;

    @Operation(summary = "Obtener todos los empleados (Cocinero, Cajero y Delivery)")
    @GetMapping("/getEmpleados")
    public ResponseEntity<?> getEmpleadosPorRoles() {
        try {
            List<Usuario> empleados = empleadoService.getEmpleados();
            return ResponseEntity.status(HttpStatus.OK).body(empleados);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Crear un nuevo empleado")
    @PostMapping("/crear")
    public ResponseEntity<?> crearEmpleado(@Valid @RequestBody EmpleadoRegistroDTO dto) {
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

    @Operation(summary = "Actualizar un empleado", description = "Actualiza los datos de un empleadi existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @Parameter(description = "ID del empleado a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Datos actualizados del empleado", required = true) @Valid @RequestBody EmpleadoUpdateDTO dto) {
        try {
            logger.info("Actualizando empleado con ID: {}", id);
            EmpleadoDTO actualizado = empleadoService.updateEmpleado(id,dto);

            logger.info("Usuario con ID {} actualizado exitosamente", id);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            logger.warn("Error al actualizar empleado ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            logger.error("Error interno al actualizar empleado ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al actualizar el empleado.\"}");
        }
    }

    @Operation(summary = "Eliminar un empleado (soft delete)", description = "Realiza una eliminación lógica del empleado marcándolo como no existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del empleado a eliminar", required = true) @PathVariable Long id) {
        try {
            logger.info("Eliminando empleado con ID: {}", id);
            empleadoService.eliminarEmpleado(id);
            logger.info("Empleado con ID {} eliminado exitosamente", id);
            return ResponseEntity.ok("{\"message\":\"Empleado eliminado con éxito.\"}");
        } catch (RuntimeException e) {
            logger.warn("Empleado con ID {} no encontrado para eliminar: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            logger.error("Error al eliminar empleado ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al eliminar el empleado.\"}");
        }
    }


}
