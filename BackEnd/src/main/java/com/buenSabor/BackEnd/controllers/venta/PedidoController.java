package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.venta.Pedido;
import com.buenSabor.BackEnd.services.venta.PedidoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pedidos")
@Tag(name = "Pedido", description = "Operaciones relacionadas con entidad Pedido")
public class PedidoController extends BeanControllerImpl<Pedido, PedidoService> {

    @Autowired
    PedidoService pedidoService;

    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Pedido pedido){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(pedidoService.save(pedido).getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear pedido: " + e.getMessage());
        }
    }

}
