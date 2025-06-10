/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.initializer;

import com.buenSabor.BackEnd.enums.TypeRol;
import com.buenSabor.BackEnd.models.seguridad.TipoRol;
import com.buenSabor.BackEnd.repositories.seguridad.TipoRolRepository;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author oscarloha
 */
@Component
public class TipoRolInitializer implements CommandLineRunner {

   @Autowired
    private TipoRolRepository tipoRolRepository;

    @Override
    public void run(String... args) {
        if (tipoRolRepository.count() == 0) {
            Arrays.stream(TypeRol.values())
                  .map(tipo -> new TipoRol(tipo, new ArrayList<>()))
                  .forEach(tipoRolRepository::save);
        }
    }
}