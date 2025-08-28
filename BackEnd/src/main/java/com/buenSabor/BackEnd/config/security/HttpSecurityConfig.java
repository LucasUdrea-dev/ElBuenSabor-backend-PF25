package com.buenSabor.BackEnd.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf( crsfConfig -> crsfConfig.disable()) //Previene vulnerabilidades
                .sessionManagement(sessionMangConfig
                        -> sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                            //Cada peticion sera autonoma e independiente del resto, con STATELESS Se crea sin seciones
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests( authConfig ->{

                    //Enpoint publicos
                    authConfig.requestMatchers(HttpMethod.GET,"/api/auth/login").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET, "/api/auth/public-access").permitAll();

                    //authConfig.requestMatchers("/error").permitAll(); //Es para todos lo metodos Http, endoint /error existe por defecto en SpringSecurity

                    //Endpoints privados
                    //authConfig.requestMatchers(HttpMethod.GET, "/products").hasRole("ADMIN");
                    //authConfig.requestMatchers(HttpMethod.POST, "/products").hasAnyRole("ADMIN","CUSTOMER");

                    //Cualquier endpoint no declarado anteriormente, configuro como permitido
                    authConfig.anyRequest().permitAll();
                });

        return httpSecurity.build();
    }

}
