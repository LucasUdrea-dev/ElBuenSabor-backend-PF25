package com.buenSabor.BackEnd.initializer;

import com.buenSabor.BackEnd.enums.TypeState;
import com.buenSabor.BackEnd.models.venta.EstadoPedido;
import com.buenSabor.BackEnd.repositories.venta.EstadoPedidoRepository;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EstadoPedidoInitializer implements CommandLineRunner {

    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    @Override
    public void run(String... args) {
        try {
            for (TypeState estado : TypeState.values()) {
                if (!estadoPedidoRepository.existsByNombreEstado(estado)) {
                    estadoPedidoRepository.save(new EstadoPedido(estado, new ArrayList<>()));
                    System.out.println("EstadoPedidoInitializer: Nuevo estado guardado: " + estado);
                }
            }
        } catch (Exception e) {
            throw e; 
        }
    }
}