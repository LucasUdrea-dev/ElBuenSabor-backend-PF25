package com.buenSabor.BackEnd.services.seguridad;

import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationRequestDTO;
import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationResponseDTO;
import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.repositories.seguridad.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserAuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;
    @Autowired
    private JwtService jwtService;

    public UserAuthentication create(UserAuthentication entity) {
        entity.setId(null); 
        return userAuthenticationRepository.save(entity);
    }

    public UserAuthentication update(Long id, UserAuthentication newData) {
        UserAuthentication existing = userAuthenticationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserAuthentication no encontrado con id: " + id));

        existing.setUsername(newData.getUsername());
        existing.setPassword(newData.getPassword());
        return userAuthenticationRepository.save(existing);
    }

    public UserAuthenticationResponseDTO login (UserAuthenticationRequestDTO authenticationRequestDTO){

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          authenticationRequestDTO.getUsername(), authenticationRequestDTO.getPassword()
        );

        authenticationManager.authenticate(authToken);

        //Si logro pasar authenticate, quiere decir que si existe un usuario
        UserAuthentication usuario = userAuthenticationRepository
                .findByUsername(authenticationRequestDTO.getUsername())
                .get();

        String jwt = jwtService.generateToken(usuario, generateExtraClaims(usuario));

        return new UserAuthenticationResponseDTO(jwt);
    }

    private Map<String,Object> generateExtraClaims(UserAuthentication usuario) {
        Map<String,Object> extraClaims = new HashMap<>();

        extraClaims.put("username" , usuario.getUsername());
        extraClaims.put("name", usuario.getUsuario().getNombre());
        extraClaims.put("surname", usuario.getUsuario().getApellido());
        extraClaims.put("role", usuario.getUsuario().getRol().getTipoRol().getRol().name());

        return  extraClaims;
    }
}