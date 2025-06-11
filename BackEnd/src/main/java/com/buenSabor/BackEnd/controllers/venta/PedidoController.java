package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.dto.venta.pedido.PedidoDTO;
import com.buenSabor.BackEnd.services.venta.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pedidos")
@CrossOrigin(origins = "*")
@Tag(name = "Pedido", description = "Operaciones relacionadas con entidad Pedido")
public class PedidoController {

//    private final PedidoService pedidoService;
//
//    @Autowired
//    public PedidoController(PedidoService pedidoService) {
//        this.pedidoService = pedidoService;
//    }
//
//   
//    @Operation(summary = "Ver un pedido por ID")
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getById(@PathVariable Long id) {
//        try {
//            PedidoDTO pedidoDTO = pedidoService.findPedidoDTOById(id);
//            if (pedidoDTO == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body("{\"error\":\"Pedido no encontrado.\"}");
//            }
//            return ResponseEntity.ok(pedidoDTO);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("{\"error\":\"Error al obtener el pedido: " + e.getMessage() + "\"}");
//        }
//    }
//   
//    @Operation(summary = "Listar todos los pedidos")
//    @GetMapping // Maps to /api/pedidos
//    public ResponseEntity<?> getAll() {
//        try {
//            List<PedidoDTO> pedidos = pedidoService.findAllPedidosDTO();
//            return ResponseEntity.ok(pedidos);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("{\"error\":\"Error al listar los pedidos: " + e.getMessage() + "\"}");
//        }
//    }
//   
//    @Operation(summary = "Listar pedidos paginados")
//    @GetMapping("/paged")
//    public ResponseEntity<?> getPaged(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        try {
//            Pageable pageable = PageRequest.of(page, size);
//            Page<PedidoDTO> pageResult = pedidoService.findAllPedidosDTO(pageable);
//            return ResponseEntity.ok(pageResult);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("{\"error\":\"Error al obtener los pedidos paginados: " + e.getMessage() + "\"}");
//        }
//    }
//   
//    @Operation(summary = "Crear un nuevo pedido")
//    @PostMapping // Maps to /api/pedidos
//    public ResponseEntity<?> create(@RequestBody PedidoDTO dto) {
//        try {
//            PedidoDTO savedDto = pedidoService.crearPedido(dto);
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body(savedDto);
//        } catch (RuntimeException e) { // Catch more specific exceptions from service, e.g., validation errors
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("{\"error\":\"Error de datos al crear el pedido: " + e.getMessage() + "\"}");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("{\"error\":\"Error interno al crear el pedido: " + e.getMessage() + "\"}");
//        }
//    }
//  
//    @Operation(summary = "Actualizar un pedido existente")
//    @PutMapping("/{id}") // Maps to /api/pedidos/{id}
//    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PedidoDTO dto) {
//        try {
//            // It's good practice to ensure the ID in the DTO matches the path variable, or to ignore DTO's ID.
//            // The service method will use the path variable ID to find the entity.
//            PedidoDTO updatedDto = pedidoService.actualizarPedido(id, dto);
//            return ResponseEntity.ok(updatedDto);
//        } catch (RuntimeException e) { // Catch specific exceptions like "not found" from service
//            return ResponseEntity.status(HttpStatus.NOT_FOUND) // Or BAD_REQUEST if it's a validation error
//                    .body("{\"error\":\"" + e.getMessage() + "\"}");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("{\"error\":\"Error al actualizar el pedido: " + e.getMessage() + "\"}");
//        }
//    }
//   
//    @Operation(summary = "Eliminar un pedido por ID")
//    @DeleteMapping("/{id}") // Maps to /api/pedidos/{id}
//    public ResponseEntity<?> delete(@PathVariable Long id) {
//        try {
//            pedidoService.eliminarPedido(id);
//            return ResponseEntity.ok("{\"message\":\"Pedido eliminado con éxito.\"}");
//        } catch (RuntimeException e) { // Catch specific exceptions like "not found" from service
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("{\"error\":\"" + e.getMessage() + "\"}");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("{\"error\":\"Error al eliminar el pedido: " + e.getMessage() + "\"}");
//        }
//    }
   
    // Assuming you have a method like this in PedidoService
    // public List<PedidoDTO> findPedidosByUsuario(Long userId)
    /*
    @Operation(summary = "Buscar pedidos por ID de usuario")
    @GetMapping("/byUser/{userId}")
    public ResponseEntity<?> getPedidosByUsuario(@PathVariable Long userId) {
        try {
            List<PedidoDTO> pedidos = pedidoService.findPedidosByUsuario(userId);
            if (pedidos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\":\"No se encontraron pedidos para el usuario con ID: " + userId + "\"}");
            }
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al buscar pedidos por usuario: " + e.getMessage() + "\"}");
        }
    }
    */
   
}
