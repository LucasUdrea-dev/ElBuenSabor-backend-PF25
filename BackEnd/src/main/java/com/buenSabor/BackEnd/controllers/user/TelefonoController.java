package com.buenSabor.BackEnd.controllers.user;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.user.telefono.TelefonoDTO;
import com.buenSabor.BackEnd.models.user.Telefono;
import com.buenSabor.BackEnd.services.user.TelefonoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/telefonos")
@CrossOrigin(origins = "*")
@Tag(name = "Telefono", description = "Operaciones relacionadas con entidad Telefono")
public class TelefonoController extends BeanControllerImpl<Telefono, TelefonoService> {

    private final TelefonoService telefonoService;

    @Autowired
    public TelefonoController(TelefonoService telefonoService) {
        this.telefonoService = telefonoService;
    }

    @Operation(summary = "Obtener todos los teléfonos de un usuario por su ID")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> getTelefonosByUserId(@PathVariable Long idUsuario) {
        try {
            List<TelefonoDTO> telefonos = telefonoService.findTelefonosByUserId(idUsuario);
            return ResponseEntity.ok(telefonos);
        } catch (Exception e) {
            if (e.getMessage().contains("no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"" + e.getMessage() + "\"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Crear un teléfono para un usuario")
    @PostMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> crearTelefono(@PathVariable Long idUsuario, @RequestBody TelefonoDTO dto) {
        try {
            TelefonoDTO creado = telefonoService.crearTelefonoParaUsuario(idUsuario, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            if (e.getMessage().contains("no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"" + e.getMessage() + "\"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Obtener un teléfono específico de un usuario")
    @GetMapping("/usuario/{idUsuario}/{idTelefono}")
    public ResponseEntity<?> obtenerTelefono(@PathVariable Long idUsuario, @PathVariable Long idTelefono) {
        try {
            TelefonoDTO dto = telefonoService.obtenerTelefonoDeUsuario(idUsuario, idTelefono);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            if (e.getMessage().contains("no encontrado") || e.getMessage().contains("no pertenece")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"" + e.getMessage() + "\"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Actualizar un teléfono de un usuario")
    @PutMapping("/usuario/{idUsuario}/{idTelefono}")
    public ResponseEntity<?> actualizarTelefono(@PathVariable Long idUsuario,
                                                @PathVariable Long idTelefono,
                                                @RequestBody TelefonoDTO dto) {
        try {
            TelefonoDTO actualizado = telefonoService.actualizarTelefonoDeUsuario(idUsuario, idTelefono, dto);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            if (e.getMessage().contains("no encontrado") || e.getMessage().contains("no pertenece")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"" + e.getMessage() + "\"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Eliminar un teléfono de un usuario")
    @DeleteMapping("/usuario/{idUsuario}/{idTelefono}")
    public ResponseEntity<?> eliminarTelefono(@PathVariable Long idUsuario, @PathVariable Long idTelefono) {
        try {
            telefonoService.eliminarTelefonoDeUsuario(idUsuario, idTelefono);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            if (e.getMessage().contains("no encontrado") || e.getMessage().contains("no pertenece")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"" + e.getMessage() + "\"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
