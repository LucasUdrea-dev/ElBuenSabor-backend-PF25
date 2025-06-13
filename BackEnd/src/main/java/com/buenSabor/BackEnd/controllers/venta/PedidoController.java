package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.dto.venta.pedido.PedidoConDireccionDTO;
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
@Tag(name = "Pedido", description = "Operaciones relacionadas con entidad Pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // --- Read Operations ---

    @Operation(summary = "Ver un pedido por ID con detalles completos (incluye dirección)")
    @GetMapping("/{id}")
    public ResponseEntity<?> getByIdFull(@PathVariable Long id) {
        try {
            PedidoConDireccionDTO pedidoDTO = pedidoService.findPedidoConDireccionDTOById(id);
            if (pedidoDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Pedido con ID " + id + " no encontrado.\"}");
            }
            return ResponseEntity.ok(pedidoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener el pedido: " + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Listar todos los pedidos con detalles completos (incluye dirección)")
    @GetMapping("/all") 
    public ResponseEntity<?> getAllFull() {
        try {
            List<PedidoConDireccionDTO> pedidos = pedidoService.findAllPedidosConDireccionDTO();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al listar los pedidos completos: " + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Listar pedidos paginados con detalles completos (incluye dirección)")
    @GetMapping("/paged")
    public ResponseEntity<?> getPagedFull(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<PedidoConDireccionDTO> pageResult = pedidoService.findAllPedidosConDireccionDTO(pageable);
            return ResponseEntity.ok(pageResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener los pedidos completos paginados: " + e.getMessage() + "\"}");
        }
    }

    // --- Write Operations ---

    @Operation(summary = "Crear un nuevo pedido con dirección (si aplica)")
    @PostMapping("") // Maps to /api/pedidos
    public ResponseEntity<?> create(@RequestBody PedidoConDireccionDTO dto) {
        try {
            PedidoConDireccionDTO savedDto = pedidoService.crearPedido(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedDto);
        } catch (RuntimeException e) {
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error de datos al crear el pedido: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error interno al crear el pedido: " + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Actualizar un pedido existente (incluye dirección si aplica)")
    @PutMapping("/{id}") 
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PedidoConDireccionDTO dto) {
        try {
            PedidoConDireccionDTO updatedDto = pedidoService.actualizarPedido(id, dto);
            if (updatedDto == null) {
                
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Pedido con ID " + id + " no encontrado para actualizar.\"}");
            }
            return ResponseEntity.ok(updatedDto);
        } catch (RuntimeException e) {
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST) 
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al actualizar el pedido: " + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Eliminar un pedido por ID")
    @DeleteMapping("/{id}") // Maps to /api/pedidos/{id}
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            pedidoService.eliminarPedido(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
        } catch (RuntimeException e) {
            // Catch specific exceptions like "not found" from service
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al eliminar el pedido: " + e.getMessage() + "\"}");
        }
    }

     @GetMapping("/activos")
    public ResponseEntity<List<PedidoConDireccionDTO>> getAllPedidosExistentes() {
        List<PedidoConDireccionDTO> pedidos = pedidoService.findAllExistentes();
        return ResponseEntity.ok(pedidos);
    }
     @GetMapping("/activos/{id}")
    public ResponseEntity<PedidoConDireccionDTO> getPedidoByIdExistente(@PathVariable Long id) {
        try {
            PedidoConDireccionDTO pedido = pedidoService.findByIdExistente(id);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            // Si el servicio lanza la excepción, devolvemos un 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    /*
    @Operation(summary = "Buscar pedidos por ID de usuario")
    @GetMapping("/byUser/{userId}")
    public ResponseEntity<?> getPedidosByUsuario(@PathVariable Long userId) {
        try {
            // Assuming pedidoService has findPedidosByUsuario that returns List<PedidoConDireccionDTO>
            List<PedidoConDireccionDTO> pedidos = pedidoService.findPedidosByUsuario(userId);
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