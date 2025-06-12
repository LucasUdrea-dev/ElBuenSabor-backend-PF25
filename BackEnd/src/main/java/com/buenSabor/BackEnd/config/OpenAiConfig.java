/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author oscarloha
 */
public class OpenAiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI(); // Config base si querés personalizar
    }
//http://localhost:8080/swagger-ui.html

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("default")
            .packagesToScan("com.buenSabor.BackEnd.controllers") 
            .build();
    }
}
