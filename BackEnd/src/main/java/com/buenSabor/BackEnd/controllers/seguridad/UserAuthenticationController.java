package com.buenSabor.BackEnd.controllers.seguridad;

import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationRequestDTO;
import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationResponseDTO;
import com.buenSabor.BackEnd.dto.seguridad.registro.UpdatePasswordDTO;
import com.buenSabor.BackEnd.dto.seguridad.registro.UpdateResponseDTO;
import com.buenSabor.BackEnd.dto.seguridad.registro.UpdateUsernameDTO;
import com.buenSabor.BackEnd.dto.seguridad.registro.UsuarioRegistroDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import com.buenSabor.BackEnd.mapper.UserAuthenticationMapper;
import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.repositories.seguridad.UserAuthenticationRepository;
import com.buenSabor.BackEnd.services.seguridad.UserAuthenticationService;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserAuthenticationController {

    @Autowired
    private final UserAuthenticationService userAuthService;
    @Autowired
    private final UserAuthenticationMapper userAuthMapper;
    @Autowired
    private final UserAuthenticationRepository authenticationRepository;

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

    @Operation(summary = "Login con correo y contraseña")
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Valid UserAuthenticationRequestDTO authRequest){
        try {
            UserAuthenticationResponseDTO jwtDto = userAuthService.login(authRequest);
            return ResponseEntity.ok(jwtDto);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar usuario y contraseña")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserAuthenticationRequestDTO dto) {
        try {

            UserAuthentication usuario = authenticationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("UserAuth no encontrado con id: " + id));

            usuario = userAuthService.update(id, userAuthMapper.toEntity(dto));

            UserAuthenticationResponseDTO authResponse = userAuthService.generarRespuestaDeLogin(usuario);

            UpdateResponseDTO response = new UpdateResponseDTO();
            response.setMensaje("Usuario con id: "+ id+ " actualizado con exito username y password");
            response.setToken(authResponse.getJwt());

            return ResponseEntity.ok(response);

        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @Operation(summary = "Actualizar username")
    @PutMapping("/updateUsername/{id}")
    public ResponseEntity<?> updateUsername(@PathVariable Long id
            , @Valid @RequestBody UpdateUsernameDTO dto) {

        try {
            UserAuthentication usuario = authenticationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("UserAuth no encontrado con id: " + id));

            usuario = userAuthService.updateUsername(id, dto);

            UserAuthenticationResponseDTO authResponse = userAuthService.generarRespuestaDeLogin(usuario);

            UpdateResponseDTO response = new UpdateResponseDTO();
            response.setMensaje("Usuario con id: "+ id+ " actualizado con exito username.");
            response.setToken(authResponse.getJwt());

            return ResponseEntity.ok(response);

        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar contraseña")
    @PutMapping("/updatePassword/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordDTO dto) {
        try {
            UserAuthentication usuario = authenticationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("UserAuth no encontrado con id: " + id));

            usuario = userAuthService.updatePassword(id, dto);

            UserAuthenticationResponseDTO authResponse = userAuthService.generarRespuestaDeLogin(usuario);

            UpdateResponseDTO response = new UpdateResponseDTO();
            response.setMensaje("Usuario con id: "+ id+ " actualizado con exito password.");
            response.setToken(authResponse.getJwt());

            return ResponseEntity.ok(response);

        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

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
        }
    }

}