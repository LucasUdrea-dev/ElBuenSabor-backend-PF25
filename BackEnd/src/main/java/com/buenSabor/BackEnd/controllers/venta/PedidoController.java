package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.venta.Pedido;
import com.buenSabor.BackEnd.services.venta.PedidoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pedidos")
@CrossOrigin(origins = "*")
@Tag(name = "Pedido", description = "Operaciones relacionadas con entidad Pedido")
public class PedidoController extends BeanControllerImpl<Pedido, PedidoService> {
}
