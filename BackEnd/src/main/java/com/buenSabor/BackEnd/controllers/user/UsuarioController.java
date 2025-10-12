package com.buenSabor.BackEnd.controllers.user;

import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import com.buenSabor.BackEnd.mapper.UsuarioMapper;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.services.ubicacion.DireccionService;
import com.buenSabor.BackEnd.services.user.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con la entidad Usuario")
public class UsuarioController {

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

    /*@Operation(summary = "Crear un nuevo usuario")
    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioDTO dto) {
        try {
            UsuarioDTO guardado = usuarioService.crearUsuario(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error inesperado al crear el usuario.\"}");
        }
    }*/

    @Operation(summary = "Listar todos los usuarios existentes")
    @GetMapping("/listar")
    public ResponseEntity<?> listarExistentes() {
        try {
            List<UsuarioDTO> dtos = usuarioMapper.toDtoList(usuarioService.findAllExistente());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener los usuarios.\"}");
        }
    }
    @Operation(summary = "Listar todos los usuarios")
    @GetMapping("")
    public ResponseEntity<?> listar() {
        try {
            List<UsuarioDTO> dtos = usuarioMapper.toDtoList(usuarioService.findAll());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener los usuarios.\"}");
        }
    }
    
    @Operation(summary = "Obtener un usuario por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            if (usuario == null || !Boolean.TRUE.equals(usuario.getExiste())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Usuario no encontrado.\"}");
            }
            return ResponseEntity.ok(usuarioMapper.toDto(usuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener el usuario.\"}");
        }
    }

    @Operation(summary = "Actualizar un usuario")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        try {
            UsuarioDTO actualizado = usuarioService.actualizarUsuario(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al actualizar el usuario.\"}");
        }
    }

    @Operation(summary = "Eliminar lógicamente un usuario")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok("{\"message\":\"Usuario eliminado con éxito.\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al eliminar el usuario.\"}");
        }
    }
    
    
      
    
}
