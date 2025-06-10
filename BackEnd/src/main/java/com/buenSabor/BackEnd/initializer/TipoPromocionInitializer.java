/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.initializer;

import com.buenSabor.BackEnd.enums.TypePromotion;
import com.buenSabor.BackEnd.models.venta.TipoPromocion;
import com.buenSabor.BackEnd.repositories.venta.TipoPromocionRepository;
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
public class TipoPromocionInitializer implements CommandLineRunner {

    @Autowired
    private TipoPromocionRepository tipoPromocionRepository;

    @Override
    public void run(String... args) {
        Arrays.stream(TypePromotion.values())
              .map(tipo -> new TipoPromocion(tipo, new ArrayList<>()))
              .forEach(tipoPromocionRepository::save);
    }
}
