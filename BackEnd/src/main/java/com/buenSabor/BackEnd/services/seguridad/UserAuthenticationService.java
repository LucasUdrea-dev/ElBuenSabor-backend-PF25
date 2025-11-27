package com.buenSabor.BackEnd.services.seguridad;

import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationRequestDTO;
import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationResponseDTO;
import com.buenSabor.BackEnd.dto.seguridad.registro.EmpleadoRegistroDTO;
import com.buenSabor.BackEnd.dto.seguridad.registro.UpdatePasswordDTO;
import com.buenSabor.BackEnd.dto.seguridad.registro.UpdateUsernameDTO;
import com.buenSabor.BackEnd.dto.seguridad.registro.UsuarioRegistroDTO;
import com.buenSabor.BackEnd.dto.user.empleado.EmpleadoDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import com.buenSabor.BackEnd.enums.TypeRol;
import com.buenSabor.BackEnd.mapper.EmpleadoMapper;
import com.buenSabor.BackEnd.mapper.UsuarioMapper;
import com.buenSabor.BackEnd.models.seguridad.Rol;
import com.buenSabor.BackEnd.models.seguridad.TipoRol;
import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import com.buenSabor.BackEnd.models.user.Empleado;
import com.buenSabor.BackEnd.models.user.Telefono;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.repositories.seguridad.RolRepository;
import com.buenSabor.BackEnd.repositories.seguridad.TipoRolRepository;
import com.buenSabor.BackEnd.repositories.seguridad.UserAuthenticationRepository;
import com.buenSabor.BackEnd.repositories.user.UsuarioRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.persistence.EntityNotFoundException;
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
    private RolRepository rolRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private EmpleadoMapper empleadoMapper;
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
                .orElseThrow(() -> new RuntimeException("Tipo Rol CUSTOMER no encontrado"));

        Rol nuevoRol = new Rol();
        nuevoRol.setTipoRol(tipoRolCliente);
        nuevoRol.setFechaAlta(new Date());
        rolRepository.save(nuevoRol);

        usuario.setRol(nuevoRol);


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
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " +id));

        UserAuthentication userAuth = usuario.getUserAuthentication();
        if (userAuth == null) {
            throw new EntityNotFoundException("El usuario no tiene credenciales asociadas");
        }

        userAuth.setUsername(newData.getUsername());
        userAuth.setPassword(passwordEncoder.encode(newData.getPassword()));

        return userAuthenticationRepository.save(userAuth);
    }

    public UserAuthenticationResponseDTO login(UserAuthenticationRequestDTO authenticationRequestDTO) {

        UserAuthentication usuario = userAuthenticationRepository
                .findByUsername(authenticationRequestDTO.getUsername())
                .orElseThrow(()  -> new RuntimeException("Usuario " + authenticationRequestDTO.getUsername() + " no encontrado!"));

        if(!usuario.getUsuario().getExiste()){
            throw new RuntimeException("El usuario no está activo");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authenticationRequestDTO.getUsername(), authenticationRequestDTO.getPassword());

        authenticationManager.authenticate(authToken);

        // Si logro pasar authenticate, quiere decir que si existe un usuario
        /*UserAuthentication usuario = userAuthenticationRepository
                .findByUsername(authenticationRequestDTO.getUsername())
                .get();
        */
        return generarRespuestaDeLogin(usuario);
    }

    public UserAuthenticationResponseDTO generarRespuestaDeLogin(UserAuthentication usuario) {

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

            //Validamos que sea un usuario activo
            if (userAuth.getUsuario() == null) {
                throw new RuntimeException("El usuario no tiene un perfil asociado (Usuario es null)");
            } else if(!userAuth.getUsuario().getExiste()){
                throw new RuntimeException("El usuario no está activo");
            }

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

        // Asignar rol de CLIENTE por defecto
        TipoRol tipoRolCliente = tipoRolRepository.findByRol(TypeRol.CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Tipo Rol CUSTOMER no encontrado"));

        Rol nuevoRol = new Rol();
        nuevoRol.setTipoRol(tipoRolCliente);
        nuevoRol.setFechaAlta(new Date());
        rolRepository.save(nuevoRol);

        usuario.setRol(nuevoRol);

        // Crear UserAuthentication
        UserAuthentication userAuth = new UserAuthentication();
        userAuth.setUsername(email);
        //  NO NECESITA CONTRASEÑA ENCRIPTADA para Firebase, usamos null o un marcador
        userAuth.setPassword(null);
        userAuth.setFirebaseUid(firebaseUid); // ⬅️ GUARDAR EL UID
        userAuth.setUsuario(usuario);

        // Asignar UserAuthentication al Usuario
        usuario.setUserAuthentication(userAuth);

        // Guardar el usuario
        usuarioRepository.save(usuario);

        return userAuth;

    }

    public UserAuthentication updateUsername(Long id, UpdateUsernameDTO entity) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " +id));

        UserAuthentication userAuth = usuario.getUserAuthentication();
        if (userAuth == null) {
            throw new EntityNotFoundException("El usuario no tiene credenciales asociadas");
        }

        Optional<UserAuthentication> existe = userAuthenticationRepository.findByUsername(entity.getUsername());

        if(existe.isPresent()
                && !existe.get().getId().equals(userAuth.getId())){
            throw new RuntimeException("El nombre de usuario ya está en uso por otro usuario");
        }

        usuario.setEmail(entity.getUsername());
        userAuth.setUsername(entity.getUsername());

        usuarioRepository.save(usuario);

        return userAuthenticationRepository.save(userAuth);
    }

    public UserAuthentication updatePassword(Long id, UpdatePasswordDTO entity) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " +id));

        UserAuthentication userAuth = usuario.getUserAuthentication();
        if (userAuth == null) {
            throw new EntityNotFoundException("El usuario no tiene credenciales asociadas");
        }

        if(!passwordEncoder.matches(entity.getPasswordActual() ,userAuth.getPassword())){
            throw new IllegalArgumentException("Ingrese contraseña actual correctamente.");
        }

        if(entity.getPasswordActual().equals(entity.getPasswordNuevo())){
            throw new IllegalArgumentException("La nueva contraseña no puede ser igual a la actual");
        }

        String passwordNuevo = entity.getPasswordNuevo();

        userAuth.setPassword(passwordEncoder.encode(passwordNuevo));

        return userAuthenticationRepository.save(userAuth);
    }

    public EmpleadoDTO crearEmpleado(EmpleadoRegistroDTO registroDTO) {

        // Validar que el email no exista
        if (userAuthenticationRepository.findByUsername(
                registroDTO.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        //Crear el objeto Empleado
        Empleado empleado = new Empleado();
        empleado.setNombre(registroDTO.getNombre());
        empleado.setApellido(registroDTO.getApellido());
        empleado.setEmail(registroDTO.getEmail());
        empleado.setExiste(true);
        empleado.setFechaAlta(new Date());
        empleado.setSueldo(registroDTO.getSueldo());

        // Asignar teléfonos
        if (registroDTO.getTelefonoList() != null &
                !registroDTO.getTelefonoList().isEmpty()) {
            List<Telefono> telefonoList = new ArrayList<>();

            registroDTO.getTelefonoList().forEach(telefonoDTO -> {
                Telefono telefono = new Telefono();
                telefono.setNumero(telefonoDTO.getNumero());
                telefono.setUsuario(empleado);
                telefonoList.add(telefono);
            });

            empleado.setTelefonoList(telefonoList);
        }

        // Asignar rol
        TipoRol tipoRol = tipoRolRepository.findById(registroDTO.getIdRol())
                .orElseThrow(() -> new RuntimeException("No existe rol con id: " + registroDTO.getIdRol()));

        Rol nuevoRol = new Rol();
        nuevoRol.setTipoRol(tipoRol);
        nuevoRol.setFechaAlta(new Date());
        rolRepository.save(nuevoRol);

        empleado.setRol(nuevoRol);

        // Crear UserAuthentication
        UserAuthentication userAuth = new UserAuthentication();
        userAuth.setUsername(registroDTO.getEmail());
        userAuth.setPassword(passwordEncoder.encode(registroDTO.getUserAuth().getPassword()));
        userAuth.setUsuario(empleado);

        // Asignar UserAuthentication al Empleado
        empleado.setUserAuthentication(userAuth);

        // Guardar el empleado (cascade guardará también UserAuthentication y Teléfonos)
        Empleado empleadoGuardado = usuarioRepository.save(empleado);

        return empleadoMapper.toDto(empleadoGuardado);
    }
}