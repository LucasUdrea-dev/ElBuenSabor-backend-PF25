package com.buenSabor.BackEnd.controllers.seguridad;

import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationRequestDTO;
import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationResponseDTO;
import com.buenSabor.BackEnd.dto.seguridad.registro.UsuarioRegistroDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import com.buenSabor.BackEnd.mapper.UserAuthenticationMapper;
import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.services.seguridad.UserAuthenticationService;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/{id}")
    public ResponseEntity<UserAuthenticationRequestDTO> update(@PathVariable Long id, @RequestBody UserAuthenticationRequestDTO dto) {
        UserAuthentication updated = userAuthService.update(id, userAuthMapper.toEntity(dto));
        return ResponseEntity.ok(userAuthMapper.toDto(updated));
    }

    // --- Nuevo Endpoint para Firebase ---

    @Operation(summary = "Login con Token de Firebase",
            description = "Recibe el ID Token de Firebase y devuelve un JWT propio.")
    @PostMapping("/firebase-login")
    public ResponseEntity<?> firebaseLogin(@RequestHeader("Firebase-Token") String firebaseToken) {
        if (firebaseToken == null || firebaseToken.isBlank()) {
            return ResponseEntity.badRequest().body("{\"error\":\"El encabezado 'Firebase-Token' es requerido.\"}");
        }

        try {
            // Llama al nuevo método del servicio que verifica el token y genera tu JWT
            UserAuthenticationResponseDTO jwtDto = userAuthService.loginWithFirebaseToken(firebaseToken);

            return ResponseEntity.ok(jwtDto);

        } catch (FirebaseAuthException e) {
            // Error específico de Firebase (ej: token expirado, inválido)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"error\":\"Autenticación de Firebase fallida: " + e.getMessage() + "\"}");
        } catch (RuntimeException e) {
            // Errores de la DB (ej: Rol CUSTOMER no encontrado)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error de la aplicación al procesar el usuario: " + e.getMessage() + "\"}");
        }
    }

}