package com.buenSabor.BackEnd.config.security;

import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.repositories.seguridad.UserAuthenticationRepository;
import com.buenSabor.BackEnd.services.seguridad.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //OncePerRequestFilter, asegura que una peticion sea procesada 1 sola vez

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1- Obtener el header que contiene el jwt
        String authHeader = request.getHeader("Authorization"); //Bearer jwt

        //validamos que exista el header
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }


        //2- Obtener jwt desde header
        String jwt = authHeader.split(" ")[1];

        //3- Obtener subject/username desd jwt
        String username = jwtService.extractUsername(jwt);

        //4- Setear objeto Authentication dentro del SecurityContext
        UserAuthentication userAuth = userAuthenticationRepository.findByUsername(username).get();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, userAuth.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);


        //5- Ejecutar resto de los filtros
        filterChain.doFilter(request,response);
    }
}
