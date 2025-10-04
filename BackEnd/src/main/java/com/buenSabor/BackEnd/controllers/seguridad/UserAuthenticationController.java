package com.buenSabor.BackEnd.controllers.seguridad;

import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationRequestDTO;
import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationResponseDTO;
import com.buenSabor.BackEnd.dto.seguridad.registro.UsuarioRegistroDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import com.buenSabor.BackEnd.mapper.UserAuthenticationMapper;
import com.buenSabor.BackEnd.mapper.UsuarioMapper;
import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.services.seguridad.UserAuthenticationService;
import com.buenSabor.BackEnd.services.user.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserAuthenticationController {

    @Autowired
    private final UserAuthenticationService userAuthService;
    @Autowired
    private final UserAuthenticationMapper userAuthMapper;

    @Operation(summary = "Crear un nuevo usuario")
    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioRegistroDTO dto) {
        try {
            UsuarioDTO guardado = userAuthService.crearUsuario(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error inesperado al crear el usuario.\"}");
        }
    }

    @PostMapping("/login")
    public UserAuthenticationResponseDTO login(
            @RequestBody @Valid UserAuthenticationRequestDTO authRequest){

        UserAuthenticationResponseDTO jwtDto = userAuthService.login(authRequest);

        return jwtDto;
    }

    @PostMapping
    public ResponseEntity<UserAuthenticationRequestDTO> create(@RequestBody UserAuthenticationRequestDTO dto) {
        UserAuthentication saved = userAuthService.create(userAuthMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userAuthMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAuthenticationRequestDTO> update(@PathVariable Long id, @RequestBody UserAuthenticationRequestDTO dto) {
        UserAuthentication updated = userAuthService.update(id, userAuthMapper.toEntity(dto));
        return ResponseEntity.ok(userAuthMapper.toDto(updated));
    }
}