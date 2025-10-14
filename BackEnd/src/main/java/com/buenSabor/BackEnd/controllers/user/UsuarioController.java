package com.buenSabor.BackEnd.controllers.user;

import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import com.buenSabor.BackEnd.mapper.UsuarioMapper;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.services.ubicacion.DireccionService;
import com.buenSabor.BackEnd.services.user.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con la entidad Usuario")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    @SuppressWarnings("unused")
    private final DireccionService direccionService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService,
            UsuarioMapper usuarioMapper,
            DireccionService direccionService) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
        this.direccionService = direccionService;
    }

    @Operation(summary = "Crear un nuevo usuario", description = "Crea un usuario con su rol, autenticación y teléfonos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> crearUsuario(
            @Parameter(description = "Datos del usuario a crear", required = true) @Valid @RequestBody UsuarioDTO dto) {
        try {
            logger.info("Creando nuevo usuario con email: {}", dto.getEmail());
            UsuarioDTO guardado = usuarioService.crearUsuario(dto);
            logger.info("Usuario creado exitosamente con ID: {}", guardado.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (RuntimeException e) {
            logger.warn("Error de validación al crear usuario: {}", e.getMessage());
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            logger.error("Error interno al crear usuario: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error inesperado al crear el usuario.\"}");
        }
    }

    @Operation(summary = "Listar usuarios activos", description = "Retorna todos los usuarios que están marcados como existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios activos obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/activos")
    public ResponseEntity<?> listarExistentes() {
        try {
            List<UsuarioDTO> dtos = usuarioMapper.toDtoList(usuarioService.findAllExistente());
            logger.info("Se obtuvieron {} usuarios activos", dtos.size());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            logger.error("Error al obtener usuarios activos: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener los usuarios.\"}");
        }
    }

    @Operation(summary = "Listar todos los usuarios", description = "Retorna todos los usuarios incluyendo inactivos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<UsuarioDTO> dtos = usuarioMapper.toDtoList(usuarioService.findAll());
            logger.info("Se obtuvieron {} usuarios en total", dtos.size());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            logger.error("Error al obtener usuarios: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener los usuarios.\"}");
        }
    }

    @Operation(summary = "Obtener un usuario por ID", description = "Retorna los detalles completos de un usuario activo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado o inactivo"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @Parameter(description = "ID del usuario a buscar", required = true) @PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            if (usuario == null || !Boolean.TRUE.equals(usuario.getExiste())) {
                logger.warn("Usuario con ID {} no encontrado o inactivo", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Usuario no encontrado.\"}");
            }
            return ResponseEntity.ok(usuarioMapper.toDto(usuario));
        } catch (Exception e) {
            logger.error("Error al obtener usuario ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener el usuario.\"}");
        }
    }

    @Operation(summary = "Actualizar un usuario", description = "Actualiza los datos de un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @Parameter(description = "ID del usuario a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Datos actualizados del usuario", required = true) @Valid @RequestBody UsuarioDTO dto) {
        try {
            logger.info("Actualizando usuario con ID: {}", id);
            UsuarioDTO actualizado = usuarioService.actualizarUsuario(id, dto);
            logger.info("Usuario con ID {} actualizado exitosamente", id);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            logger.warn("Error al actualizar usuario ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            logger.error("Error interno al actualizar usuario ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al actualizar el usuario.\"}");
        }
    }

    @Operation(summary = "Eliminar un usuario (soft delete)", description = "Realiza una eliminación lógica del usuario marcándolo como no existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del usuario a eliminar", required = true) @PathVariable Long id) {
        try {
            logger.info("Eliminando usuario con ID: {}", id);
            usuarioService.eliminarUsuario(id);
            logger.info("Usuario con ID {} eliminado exitosamente", id);
            return ResponseEntity.ok("{\"message\":\"Usuario eliminado con éxito.\"}");
        } catch (RuntimeException e) {
            logger.warn("Usuario con ID {} no encontrado para eliminar: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            logger.error("Error al eliminar usuario ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al eliminar el usuario.\"}");
        }
    }

    @GetMapping("/customer")
    public ResponseEntity<List<Usuario>> getUsuariosCustomer() {
        List<Usuario> customers = usuarioService.getUsuariosCustomer();
        return ResponseEntity.ok(customers);
    }
}
