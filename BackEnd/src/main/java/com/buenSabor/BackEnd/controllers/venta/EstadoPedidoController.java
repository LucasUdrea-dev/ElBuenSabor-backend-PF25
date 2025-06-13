package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.dto.venta.estado.EstadoPedidoDTO;
import com.buenSabor.BackEnd.repositories.venta.EstadoPedidoRepository;
import com.buenSabor.BackEnd.services.venta.EstadoPedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/estadoPedidos")
@Tag(name = "EstadoPedido", description = "Operaciones relacionadas con entidad EstadoPedido")
public class EstadoPedidoController { 

    private final EstadoPedidoService estadoPedidoService;
    private final EstadoPedidoRepository estadoPedidoRepository; 

    @Autowired
    public EstadoPedidoController(EstadoPedidoService estadoPedidoService,
            EstadoPedidoRepository estadoPedidoRepository) {
        this.estadoPedidoService = estadoPedidoService;
        this.estadoPedidoRepository = estadoPedidoRepository;
    }

   
    @Operation(summary = "Listar todos los estados de pedido disponibles")
    @GetMapping 
    public ResponseEntity<?> getAllEstadoPedidos() {
        try {
            List<EstadoPedidoDTO> dtos = estadoPedidoService.findAllEstadoPedidosDTO();
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
           
            System.err.println("Error al obtener los estados de pedido: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener los estados de pedido.\"}");
        }
    }

    @Operation(summary = "Obtener un estado de pedido por su ID")
    @GetMapping("/{id}") 
    public ResponseEntity<?> getEstadoPedidoById(@PathVariable Long id) {
        try {
            EstadoPedidoDTO estadoPedidoDTO = estadoPedidoService.findEstadoPedidoDTOById(id);
            if (estadoPedidoDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Estado de pedido con ID " + id + " no encontrado.\"}");
            }
            return ResponseEntity.ok(estadoPedidoDTO);
        } catch (Exception e) {
           
            System.err.println("Error al obtener el estado de pedido por ID: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener el estado de pedido por ID.\"}");
        }
    }
}
