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

                    //Login y autenticación
                    authConfig.requestMatchers("/api/auth/**").permitAll();

                    authConfig.requestMatchers(
                            "/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/swagger-resources/**",
                            "/webjars/**",
                            "/dev/doc",        // Página principal del Swagger UI
                            "/dev/doc/**",     // Cualquier subruta
                            "/error"
                            ).permitAll();

                    // =======================================================================================
                    //                              ENDPOINTS DE EMPRESA Y SUCURSAL
                    // =======================================================================================

                    //Empresas
                    authConfig.requestMatchers("/api/empresas/**").hasRole("ADMIN");
                    
                    //Sucursales
                    authConfig.requestMatchers(HttpMethod.GET, "/api/sucursales/**").authenticated();
                    authConfig.requestMatchers(HttpMethod.POST, "/api/sucursales/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/sucursales/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/sucursales/**").hasRole("ADMIN");

                    // =======================================================================================
                    //                              ENDPOINTS DE PRODUCTOS
                    // =======================================================================================
                    
                    //Categorías
                    authConfig.requestMatchers(HttpMethod.GET, "/api/Categoria/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/api/Categoria/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/Categoria/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/Categoria/**").hasRole("ADMIN");
                    
                    //Subcategorías
                    authConfig.requestMatchers(HttpMethod.GET, "/api/Subcategoria/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/api/Subcategoria/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/Subcategoria/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/Subcategoria/**").hasRole("ADMIN");
                    
                    //Artículos
                    authConfig.requestMatchers(HttpMethod.GET, "/api/articulo/**").permitAll();
                    
                    //Insumos
                    authConfig.requestMatchers(HttpMethod.GET, "/api/insumos/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/api/insumos/**").hasAnyRole("ADMIN", "COCINERO");
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/insumos/**").hasAnyRole("ADMIN", "COCINERO");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/insumos/**").hasAnyRole("ADMIN", "COCINERO");
                    
                    //Artículos Manufacturados
                    authConfig.requestMatchers(HttpMethod.GET, "/api/ArticuloManufacturado/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/api/ArticuloManufacturado/**").hasAnyRole("ADMIN", "COCINERO");
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/ArticuloManufacturado/**").hasAnyRole("ADMIN", "COCINERO");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/ArticuloManufacturado/**").hasAnyRole("ADMIN", "COCINERO");

                    // =======================================================================================
                    //                              ENDPOINTS DE STOCK
                    // =======================================================================================
                    
                    //Stock
                    authConfig.requestMatchers(HttpMethod.GET, "/api/stock/**").authenticated();
                    authConfig.requestMatchers(HttpMethod.POST, "/api/stock/**").hasAnyRole("ADMIN", "COCINERO");
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/stock/**").hasAnyRole("ADMIN", "COCINERO");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/stock/**").hasAnyRole("ADMIN", "COCINERO");

                    // =======================================================================================
                    //                              ENDPOINTS DE USUARIOS
                    // =======================================================================================
                    
                    //Usuarios
                    authConfig.requestMatchers(HttpMethod.GET, "/api/usuarios/**").authenticated();
                    authConfig.requestMatchers(HttpMethod.POST, "/api/usuarios/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMIN");
                    
                    //Empleados
                    authConfig.requestMatchers("/api/empleados/**").hasRole("ADMIN");
                    
                    //Teléfonos
                    authConfig.requestMatchers("/api/telefonos/**").hasRole("ADMIN");
                    
                    //Roles y Tipos de Rol
                    authConfig.requestMatchers("/api/roles/**").hasRole("ADMIN");
                    authConfig.requestMatchers("/api/tipos-rol/**").hasRole("ADMIN");

                    // =======================================================================================
                    //                              ENDPOINTS DE VENTAS
                    // =======================================================================================
                    
                    //Pedidos
                    authConfig.requestMatchers(HttpMethod.POST, "/api/pedidos/**").authenticated();
                    authConfig.requestMatchers(HttpMethod.GET, "/api/pedidos/activos/**").authenticated();
                    authConfig.requestMatchers(HttpMethod.GET, "/api/pedidos/all").hasAnyRole("ADMIN", "CAJERO", "COCINERO", "DELIVERY");
                    authConfig.requestMatchers(HttpMethod.GET, "/api/pedidos/paged").hasAnyRole("ADMIN", "CAJERO", "COCINERO", "DELIVERY");
                    authConfig.requestMatchers(HttpMethod.GET, "/api/pedidos/{id}").authenticated();
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/pedidos/**").hasAnyRole("ADMIN", "CAJERO");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/pedidos/**").hasRole("ADMIN");
                    
                    //Promociones
                    authConfig.requestMatchers(HttpMethod.GET, "/api/promociones/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/api/promociones/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/promociones/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/promociones/**").hasRole("ADMIN");
                    
                    //Promoción Artículos
                    authConfig.requestMatchers("/api/promocion-articulos/**").hasRole("ADMIN");
                    
                    //Estados de Pedido
                    authConfig.requestMatchers("/api/estados-pedido/**").hasAnyRole("ADMIN", "CAJERO", "COCINERO", "DELIVERY");
                    
                    //Tipos de Pago
                    authConfig.requestMatchers(HttpMethod.GET, "/api/tipos-pago/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/api/tipos-pago/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/tipos-pago/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/tipos-pago/**").hasRole("ADMIN");
                    
                    //Tipos de Envío
                    authConfig.requestMatchers(HttpMethod.GET, "/api/tipos-envio/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/api/tipos-envio/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/tipos-envio/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/tipos-envio/**").hasRole("ADMIN");
                    
                    //Tipos de Promoción
                    authConfig.requestMatchers("/api/tipos-promocion/**").hasRole("ADMIN");
                    
                    //MercadoPago
                    authConfig.requestMatchers("/api/mercadopago/**").permitAll();

                    // =======================================================================================
                    //                              ENDPOINTS DE UBICACIÓN
                    // =======================================================================================
                    
                    //Países
                    authConfig.requestMatchers(HttpMethod.GET, "/api/paises/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/api/paises/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/paises/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/paises/**").hasRole("ADMIN");
                    
                    //Provincias
                    authConfig.requestMatchers(HttpMethod.GET, "/api/provincias/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/api/provincias/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/provincias/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/provincias/**").hasRole("ADMIN");
                    
                    //Ciudad
                    authConfig.requestMatchers(HttpMethod.GET, "/api/ciudades/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/api/ciudades/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/ciudades/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/ciudades/**").hasRole("ADMIN");
                    
                    //Direcciones
                    authConfig.requestMatchers(HttpMethod.GET, "/api/direcciones/**").authenticated();
                    authConfig.requestMatchers(HttpMethod.POST, "/api/direcciones/**").authenticated();
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/direcciones/**").authenticated();
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/direcciones/**").authenticated();

                    // =======================================================================================
                    //                              ENDPOINTS DE IMÁGENES
                    // =======================================================================================
                    
                    //Imágenes
                    authConfig.requestMatchers(HttpMethod.GET, "/api/imagenes/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/api/imagenes/**").authenticated();
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/imagenes/**").authenticated();

                    // =======================================================================================
                    //                              CONFIGURACIÓN POR DEFECTO
                    // =======================================================================================
                    
                    //Por defecto, cualquier endpoint no declarado anteriormente necesita ser autenticado
                    //authConfig.anyRequest().authenticated();
                    authConfig.anyRequest().permitAll();
                });

        return httpSecurity.build();
    }

}
