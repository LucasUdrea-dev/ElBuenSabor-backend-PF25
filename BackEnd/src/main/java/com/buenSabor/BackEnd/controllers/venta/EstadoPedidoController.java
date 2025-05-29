package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.venta.EstadoPedido;
import com.buenSabor.BackEnd.services.venta.EstadoPedidoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/estadoPedidos")
@CrossOrigin(origins = "*")
@Tag(name = "EstadoPedido", description = "Operaciones relacionadas con entidad EstadoPedido")
public class EstadoPedidoController extends BeanControllerImpl<EstadoPedido, EstadoPedidoService> {
}
