package com.buenSabor.BackEnd.controllers.seguridad;

import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationRequestDTO;
import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationResponseDTO;
import com.buenSabor.BackEnd.mapper.UserAuthenticationMapper;
import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.services.seguridad.UserAuthenticationService;
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

    @PostMapping("/login")
    public UserAuthenticationResponseDTO login(
            @RequestBody @Valid UserAuthenticationRequestDTO authRequest){

        UserAuthenticationResponseDTO jwtDto = userAuthService.login(authRequest);

        return jwtDto;
    }

    @GetMapping("/public-access")
    public String publicAccessEndpoint(){
        return "Endpoint de acceso público sin necesidad de login";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserAuthenticationRequestDTO> create(@RequestBody UserAuthenticationRequestDTO dto) {
        UserAuthentication saved = userAuthService.create(userAuthMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userAuthMapper.toDto(saved));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserAuthenticationRequestDTO> update(@PathVariable Long id, @RequestBody UserAuthenticationRequestDTO dto) {
        UserAuthentication updated = userAuthService.update(id, userAuthMapper.toEntity(dto));
        return ResponseEntity.ok(userAuthMapper.toDto(updated));
    }
}