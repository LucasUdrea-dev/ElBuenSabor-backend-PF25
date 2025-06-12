package com.buenSabor.BackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndApplication.class, args);
		System.out.println("Aplicación corriendo...");
	}

        //como hacer con los historicos
        //cuantos DTO y como manejar los update
        //puede hacerse un Post con DTO+id?
        // cual es la forma correcta de guardar una referencia a otra entidad
        
        
}
