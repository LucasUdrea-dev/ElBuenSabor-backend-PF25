/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.initializer;

import com.buenSabor.BackEnd.enums.TypePay;
import com.buenSabor.BackEnd.models.venta.TipoPago;
import com.buenSabor.BackEnd.repositories.venta.TipoPagoRepository;
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
public class TipoPagoInitializer implements CommandLineRunner {

    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    @Override
    public void run(String... args) {
        if (tipoPagoRepository.count() == 0) {
            Arrays.stream(TypePay.values())
                    .map(tipo -> new TipoPago(tipo, new ArrayList<>(), new ArrayList<>()))
                    .forEach(tipoPagoRepository::save);
        }
    }

}
