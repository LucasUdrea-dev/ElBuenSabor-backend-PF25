package com.buenSabor.BackEnd.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info()
                .title("El Buen Sabor API")
                .description("API del backend para gestión de empresas, productos, pedidos y seguridad.")
                .version("v1.0.0")
                .contact(new Contact()
                        .name("Equipo El Buen Sabor")
                        .email("support@elbuen sabor.example")
                        .url("https://elbuen-sabor.example"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0.html"));

        Server local = new Server()
                .url("http://localhost:8080")
                .description("Local");

        Server prod = new Server()
                .url("https://api.elbuensabor.com")
                .description("Producción");

        Components components = new Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(SECURITY_SCHEME_NAME);

        return new OpenAPI()
                .info(info)
                .servers(List.of(local, prod))
                .components(components)
                .addSecurityItem(securityRequirement)
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación adicional")
                        .url("https://docs.elbuensabor.com"));
    }
}
