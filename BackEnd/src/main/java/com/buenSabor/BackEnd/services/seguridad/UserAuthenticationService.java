package com.buenSabor.BackEnd.services.seguridad;

import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationRequestDTO;
import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationResponseDTO;
import com.buenSabor.BackEnd.dto.seguridad.registro.UsuarioRegistroDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import com.buenSabor.BackEnd.enums.TypeRol;
import com.buenSabor.BackEnd.mapper.UsuarioMapper;
import com.buenSabor.BackEnd.models.seguridad.Rol;
import com.buenSabor.BackEnd.models.seguridad.TipoRol;
import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.models.user.Telefono;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.repositories.seguridad.TipoRolRepository;
import com.buenSabor.BackEnd.repositories.seguridad.UserAuthenticationRepository;
import com.buenSabor.BackEnd.repositories.user.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserAuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;
    @Autowired
    private TipoRolRepository tipoRolRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioDTO crearUsuario(UsuarioRegistroDTO registroDTO) {



        // Validar que el email no exista
        if(userAuthenticationRepository.findByUsername(
                registroDTO.getEmail()).isPresent()){
            throw new RuntimeException("El email ya está registrado");
        }

        // Crear el objeto Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setApellido(registroDTO.getApellido());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setExiste(true);

        // Asignar teléfonos
        if(registroDTO.getTelefonoList() != null &
            !registroDTO.getTelefonoList().isEmpty()){
            List<Telefono> telefonoList = new ArrayList<>();

            registroDTO.getTelefonoList().forEach(telefonoDTO -> {
                Telefono telefono = new Telefono();
                telefono.setNumero(telefonoDTO.getNumero());
                telefono.setUsuario(usuario);
                telefonoList.add(telefono);
            });

            usuario.setTelefonoList(telefonoList);
        }

        // Asignar rol de CLIENTE por defecto
        TipoRol tipoRolCliente = tipoRolRepository.findByRol(TypeRol.CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Rol CUSTOMER no encontrado"));

        Rol rolCliente = new Rol();
        rolCliente.setFechaAlta(new Date());
        rolCliente.setTipoRol(tipoRolCliente);
        usuario.setRol(rolCliente);


        // Crear UserAuthentication
        UserAuthentication userAuth = new UserAuthentication();
        userAuth.setUsername(registroDTO.getEmail());
        userAuth.setPassword(passwordEncoder.encode(registroDTO.getUserAuth().getPassword()));
        userAuth.setUsuario(usuario);

        // Asignar UserAuthentication al Usuario
        usuario.setUserAuthentication(userAuth);

        // Guardar el usuario (cascade guardará también UserAuthentication y Teléfonos)
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return usuarioMapper.toDto(usuarioGuardado);
    }

    public UserAuthentication create(UserAuthentication userAuth) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userAuth.setPassword(encoder.encode(userAuth.getPassword()));
        return userAuthenticationRepository.save(userAuth);
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

        UserAuthenticationResponseDTO responseDTO = new UserAuthenticationResponseDTO();
        responseDTO.setId(usuario.getUsuario().getId());
        responseDTO.setNombre(usuario.getUsuario().getNombre());
        responseDTO.setApellido(usuario.getUsuario().getApellido());
        responseDTO.setEmail(usuario.getUsername());
        responseDTO.setRol(usuario.getUsuario().getRol().getTipoRol().getRol().name());
        responseDTO.setJwt(jwt);

        return responseDTO;
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