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

                    // =======================================================================================
                    //                              ENDPOINTS PÚBLICOS
                    // =======================================================================================

                    //Login
                    authConfig.requestMatchers("/api/auth/**").permitAll();

                    //Es para todos lo metodos Http, endpoint /error existe por defecto en SpringSecurity
                    authConfig.requestMatchers("/error").permitAll();
                    authConfig.requestMatchers(
                                    "/swagger-ui/**",
                                    "/swagger-ui.html",
                                    "/swagger-ui/index.html",
                                    "/v3/api-docs/**",
                                    "/v3/api-docs",
                                    "/swagger-resources/**",
                                    "/webjars/**"
                            ).permitAll();

                    // =======================================================================================
                    //                              ENDPOINTS PRIVADOS
                    // =======================================================================================

                    //Company
                    authConfig.requestMatchers("/api/empresas").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.GET,"/api/sucursales/").authenticated();

                    //authConfig.requestMatchers(HttpMethod.GET, "/products").hasRole("ADMIN");
                    //authConfig.requestMatchers(HttpMethod.POST, "/products").hasAnyRole("ADMIN","CUSTOMER");

                    //Por defecto, cualquier enpoint no declarado anteriormente necesita ser autenticado
                    authConfig.anyRequest().authenticated();
                });

        return httpSecurity.build();
    }

}
