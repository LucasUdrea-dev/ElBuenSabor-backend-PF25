package com.buenSabor.BackEnd.controllers.seguridad;

import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationRequestDTO;
import com.buenSabor.BackEnd.mapper.UserAuthenticationMapper;
import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.services.seguridad.UserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserAuthenticationController {

    private final UserAuthenticationService userAuthService;
    private final UserAuthenticationMapper userAuthMapper;

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