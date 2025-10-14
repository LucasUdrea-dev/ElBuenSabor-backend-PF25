package com.buenSabor.BackEnd.initializer;

import com.buenSabor.BackEnd.enums.TypeDelivery;
import com.buenSabor.BackEnd.models.venta.TipoEnvio;
import com.buenSabor.BackEnd.repositories.venta.TipoEnvioRepository;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TypeDeliveryInitializer implements CommandLineRunner {

    @Autowired
    private TipoEnvioRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            Arrays.stream(TypeDelivery.values())
                    .map(tipo -> new TipoEnvio(tipo, new ArrayList<>()))
                    .forEach(repository::save);
        }
    }
}
