/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.initializer;

import com.buenSabor.BackEnd.enums.Measument;
import com.buenSabor.BackEnd.models.producto.UnidadMedida;
import com.buenSabor.BackEnd.repositories.producto.UnidadMedidaRepository;
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
public class MeasumentInitializer implements CommandLineRunner {

    @Autowired
    private UnidadMedidaRepository repository;

    @Override
    public void run(String... args) {
        Arrays.stream(Measument.values())
              .map(medida -> new UnidadMedida(medida, new ArrayList<>())) 
              .forEach(repository::save);
    }
}