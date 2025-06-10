package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.enums.TypeState;
import com.buenSabor.BackEnd.models.venta.EstadoPedido;
import com.buenSabor.BackEnd.repositories.venta.EstadoPedidoRepository;
import com.buenSabor.BackEnd.services.venta.EstadoPedidoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/estadoPedidos")
@CrossOrigin(origins = "*")
@Tag(name = "EstadoPedido", description = "Operaciones relacionadas con entidad EstadoPedido")
public class EstadoPedidoController extends BeanControllerImpl<EstadoPedido, EstadoPedidoService> implements CommandLineRunner{
    
    
    
      @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    @Override
    public void run(String... args) {
        for (TypeState state : TypeState.values()) {
            boolean exists = estadoPedidoRepository.existsByNombreEstado(state);
            if (!exists) {
                EstadoPedido ep = new EstadoPedido();
                ep.setNombreEstado(state);
                estadoPedidoRepository.save(ep);
            }
        }
    
    }
}