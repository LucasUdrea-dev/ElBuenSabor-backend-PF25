package com.buenSabor.BackEnd.config.security;

import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.repositories.seguridad.UserAuthenticationRepository;
import com.buenSabor.BackEnd.services.seguridad.JwtService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
//Filtros con JWT o con Firebase
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //OncePerRequestFilter, asegura que una peticion sea procesada 1 sola vez

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;
    @Autowired
    private FirebaseAuth firebaseAuth;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1- Obtener el header que contiene el token o firebase
        String authHeader = request.getHeader("Authorization"); //Bearer token

        //validamos que exista el header y comience con "Bearer"
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        //2- Obtener token desde header
        //String token = authHeader.split(" ")[1];
        String token = authHeader.substring(7); // "Bearer ".length() == 7

        // 3- Intentar autenticación con JWT
        if (autenticarConJWT(token)) {
            // Si la autenticación JWT fue exitosa, el SecurityContext ya está seteado.
            filterChain.doFilter(request, response);
            return;
        }

        // 4- Si falla JWT, intentar autenticación con Firebase
        if (autenticarConFirebase(token)) {
            // Si la autenticación Firebase fue exitosa, el SecurityContext ya está seteado.
            filterChain.doFilter(request, response);
            return;
        }

        // Si ninguna autenticación fue exitosa (el token es inválido o expiró),
        // el SecurityContext sigue vacío y la petición continuará, lo que
        // eventualmente será denegado por el resto de la configuración de seguridad.
        filterChain.doFilter(request, response);

        /*
        //3- Obtener subject/username desd token
        String username = jwtService.extractUsername(token);

        //4- Setear objeto Authentication dentro del SecurityContext
        UserAuthentication userAuth = userAuthenticationRepository.findByUsername(username).get();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, userAuth.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);


        //5- Ejecutar resto de los filtros
        filterChain.doFilter(request,response);

         */
    }

    private boolean autenticarConJWT(String jwt) {
        try {
            // Obtener subject/username desde jwt
            String username = jwtService.extractUsername(jwt);

            // Verificar si el contexto ya tiene autenticación (por si acaso)
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Buscar el usuario en la base de datos
                Optional<UserAuthentication> userAuthOptional = userAuthenticationRepository.findByUsername(username);

                if (userAuthOptional.isPresent()) {
                    UserAuthentication userAuth = userAuthOptional.get();

                    // 4- Setear objeto Authentication dentro del SecurityContext
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            username, null, userAuth.getAuthorities()
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    return true; // Autenticación JWT exitosa
                }
            }
        } catch (Exception e) {
            // El token JWT es inválido, no hacemos nada y dejamos que pase a la verificación de Firebase.
        }
        return false; // Autenticación JWT fallida
    }

    private boolean autenticarConFirebase(String token) {
        try {
            // 1. Verificar el token con Firebase
            FirebaseToken decodedToken = firebaseAuth.verifyIdToken(token);
            String firebaseUid = decodedToken.getUid();

            // 2. OBTENER EL ROL DEL CUSTOM CLAIM
            String roleClaim = (String) decodedToken.getClaims().get("role");

            List<GrantedAuthority> authorities = List.of();

            if (roleClaim != null) {
                // El rol en Spring Security debe empezar con "ROLE_"
                authorities = List.of(new SimpleGrantedAuthority("ROLE_" + roleClaim.toUpperCase()));
            } else {
                // ⚠️ Manejo de usuario sin rol: Puedes asignar un rol por defecto si es necesario.
                authorities = List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
            }

            // 3. Crear y establecer el objeto de autenticación
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            firebaseUid, // UID como el principal
                            null,
                            authorities // Roles obtenidos del token
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true; // Autenticación Firebase exitosa


        } catch (Exception e) {
            // El token de Firebase es inválido o expiró. Limpiamos el contexto por si acaso.
            SecurityContextHolder.clearContext();
            return false; // Autenticación Firebase fallida
        }
    }
}
