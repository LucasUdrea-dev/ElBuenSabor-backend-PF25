/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.initializer;

import com.buenSabor.BackEnd.enums.TypeState;
import com.buenSabor.BackEnd.models.venta.EstadoPedido;
import com.buenSabor.BackEnd.repositories.venta.EstadoPedidoRepository;
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
public class EstadoPedidoInitializer implements CommandLineRunner {

    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    @Override
    public void run(String... args) {
        Arrays.stream(TypeState.values())
              .map(estado -> new EstadoPedido(estado, new ArrayList<>())) 
              .forEach(estadoPedidoRepository::save);
    }
}
