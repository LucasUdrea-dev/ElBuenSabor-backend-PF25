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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    @Autowired
    private FirebaseAuth firebaseAuth;

    public UsuarioDTO crearUsuario(UsuarioRegistroDTO registroDTO) {

        // Validar que el email no exista
        if (userAuthenticationRepository.findByUsername(
                registroDTO.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Crear el objeto Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setApellido(registroDTO.getApellido());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setExiste(true);

        // Asignar teléfonos
        if (registroDTO.getTelefonoList() != null &
                !registroDTO.getTelefonoList().isEmpty()) {
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

    public UserAuthentication update(Long id, UserAuthentication newData) {
        UserAuthentication existing = userAuthenticationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserAuthentication no encontrado con id: " + id));

        existing.setUsername(newData.getUsername());
        existing.setPassword(newData.getPassword());
        return userAuthenticationRepository.save(existing);
    }

    public UserAuthenticationResponseDTO login(UserAuthenticationRequestDTO authenticationRequestDTO) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authenticationRequestDTO.getUsername(), authenticationRequestDTO.getPassword());

        authenticationManager.authenticate(authToken);

        // Si logro pasar authenticate, quiere decir que si existe un usuario
        UserAuthentication usuario = userAuthenticationRepository
                .findByUsername(authenticationRequestDTO.getUsername())
                .get();

        String jwt = jwtService.generateToken(usuario, generateExtraClaims(usuario));

        UserAuthenticationResponseDTO responseDTO = new UserAuthenticationResponseDTO();
        responseDTO.setId(usuario.getUsuario().getId());
        responseDTO.setNombre(usuario.getUsuario().getNombre());
        responseDTO.setApellido(usuario.getUsuario().getApellido());
        responseDTO.setEmail(usuario.getUsername());
        responseDTO.setId_rol(usuario.getUsuario().getRol().getTipoRol().getId());
        responseDTO.setRol(usuario.getUsuario().getRol().getTipoRol().getRol().name());
        responseDTO.setJwt(jwt);

        return responseDTO;
    }

    private Map<String, Object> generateExtraClaims(UserAuthentication usuario) {
        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("username", usuario.getUsername());
        extraClaims.put("name", usuario.getUsuario().getNombre());
        extraClaims.put("surname", usuario.getUsuario().getApellido());
        extraClaims.put("image_url", usuario.getUsuario().getImagenUsuario());
        extraClaims.put("id_role", usuario.getUsuario().getRol().getTipoRol().getId());
        extraClaims.put("role", usuario.getUsuario().getRol().getTipoRol().getRol().name());

        return extraClaims;
    }

    /**
     * Autentica o registra un usuario usando un token de ID de Firebase.
     * 
     * @param firebaseToken El JWT de Firebase enviado desde el frontend.
     * @return UserAuthenticationResponseDTO con un JWT propio generado.
     */
    public UserAuthenticationResponseDTO loginWithFirebaseToken(String firebaseToken) throws FirebaseAuthException {

        // 1. Verificar el token con Firebase
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(firebaseToken);

        String firebaseUid = decodedToken.getUid();
        String email = decodedToken.getEmail();

        // El claim de nombre puede venir en diferentes formatos, 'name' o 'email' son
        // comunes.
        String displayName = decodedToken.getName();

        String photoUrl = decodedToken.getPicture();

        // 2. Buscar si el usuario existe en la DB local usando el EMAIL
        Optional<UserAuthentication> userAuthOptional = userAuthenticationRepository.findByUsername(email);

        UserAuthentication userAuth;

        if (userAuthOptional.isEmpty()) {
            // 3. Si no existe, registrar el usuario de Firebase (registro implícito)
            userAuth = registerFirebaseUser(firebaseUid, email, displayName, photoUrl);
        } else {
            // 4. Si existe, actualizar el firebaseUid por si acaso y obtener el objeto
            userAuth = userAuthOptional.get();
            // Aseguramos que el campo firebaseUid esté sincronizado
            if (userAuth.getFirebaseUid() == null || !userAuth.getFirebaseUid().equals(firebaseUid)) {
                userAuth.setFirebaseUid(firebaseUid);
                userAuthenticationRepository.save(userAuth);
            }
        }

        // 5. Generar un JWT propio para la aplicación (con roles incluidos)
        String jwt = jwtService.generateToken(userAuth, generateExtraClaims(userAuth));

        // 6. Construir la respuesta
        UserAuthenticationResponseDTO responseDTO = new UserAuthenticationResponseDTO();
        responseDTO.setId(userAuth.getUsuario().getId());
        responseDTO.setNombre(userAuth.getUsuario().getNombre());
        responseDTO.setApellido(userAuth.getUsuario().getApellido());
        responseDTO.setEmail(userAuth.getUsername());
        responseDTO.setId_rol(userAuth.getUsuario().getRol().getTipoRol().getId());
        responseDTO.setRol(userAuth.getUsuario().getRol().getTipoRol().getRol().name());
        responseDTO.setJwt(jwt);

        return responseDTO;
    }

    /**
     * Registra un nuevo usuario en la DB local tras una autenticación exitosa con
     * Firebase.
     */
    private UserAuthentication registerFirebaseUser(
            String firebaseUid, String email, String displayName, String photoUrl) {

        String nombre = "";
        String apellido = "";

        // 1. Lógica para intentar separar Nombre y Apellido desde displayName
        if (displayName != null && !displayName.isBlank()) {
            String[] parts = displayName.trim().split("\\s+", 2);

            if (parts.length > 0) {
                nombre = parts[0]; // La primera palabra siempre será el nombre
            }

            if (parts.length > 1) {
                apellido = parts[1]; // El resto es el apellido
            }
        }

        // 2. Fallback: Si el nombre sigue vacío (ej. Firebase solo dio el email como
        // displayName, o es nulo),
        // usamos la parte del email antes del @.
        if (nombre.isBlank() && email != null && email.contains("@")) {
            nombre = email.substring(0, email.indexOf('@'));
        }

        // 3. Crear el objeto Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setExiste(true);
        usuario.setImagenUsuario(photoUrl);

        // Asignar rol de CLIENTE por defecto (usando tu lógica existente)
        TipoRol tipoRolCliente = tipoRolRepository.findByRol(TypeRol.CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Rol CUSTOMER no encontrado"));

        Rol rolCliente = new Rol();
        rolCliente.setFechaAlta(new Date());
        rolCliente.setTipoRol(tipoRolCliente);
        usuario.setRol(rolCliente);

        // Crear UserAuthentication
        UserAuthentication userAuth = new UserAuthentication();
        userAuth.setUsername(email);
        // ⚠️ NO NECESITA CONTRASEÑA ENCRIPTADA para Firebase, usamos null o un marcador
        userAuth.setPassword(null);
        userAuth.setFirebaseUid(firebaseUid); // ⬅️ GUARDAR EL UID
        userAuth.setUsuario(usuario);

        // Asignar UserAuthentication al Usuario
        usuario.setUserAuthentication(userAuth);

        // Guardar el usuario
        usuarioRepository.save(usuario);

        return userAuth;

    }

}