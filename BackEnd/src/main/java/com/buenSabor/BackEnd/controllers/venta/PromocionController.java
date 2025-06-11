package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.dto.venta.promocion.PromocionDTO;
import com.buenSabor.BackEnd.mapper.PromocionMapper;
import com.buenSabor.BackEnd.models.venta.Promocion;
import com.buenSabor.BackEnd.services.venta.PromocionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("api/promociones")
@Tag(name = "Promoción", description = "Operaciones relacionadas con la entidad Promoción")
public class PromocionController {

    // No need to inject PromocionMapper directly here for CRUD methods
    // because the service will handle DTO-to-Entity and Entity-to-DTO conversion.
    // If you have specific, non-CRUD DTO mapping needs, you could keep it.
    // @Autowired
    // private PromocionMapper promocionMapper;

    private final PromocionService promocionService;

    // Use constructor injection for dependencies
    @Autowired
    public PromocionController(PromocionService promocionService) {
        this.promocionService = promocionService;
    }

    @Operation(summary = "Ver una promoción por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            PromocionDTO promocionDTO = promocionService.findPromocionDTOById(id);
            if (promocionDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Promoción no encontrada.\"}");
            }
            return ResponseEntity.ok(promocionDTO); // Service returns DTO directly
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener la promoción: " + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Listar todas las promociones")
    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        try {
            List<PromocionDTO> promociones = promocionService.findAllPromocionesDTO(); // Service returns list of DTOs
            return ResponseEntity.ok(promociones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al listar las promociones: " + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Guardar una nueva promoción")
    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody PromocionDTO dto) {
        try {
            PromocionDTO savedDto = promocionService.crearPromocion(dto); // Service handles DTO-to-Entity and saving
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedDto); // Service returns DTO directly
        } catch (RuntimeException e) { // Catch specific runtime exceptions for better clarity
            // For example, if a related entity is not found
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error de datos al guardar la promoción: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error interno al guardar la promoción: " + e.getMessage() + "\"}");
        }
    }
  
    @Operation(summary = "Eliminar una promoción por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            promocionService.eliminarPromocion(id); // Service handles finding and deleting
            return ResponseEntity.ok("{\"message\":\"Promoción eliminada con éxito.\"}");
        } catch (RuntimeException e) { // Catch specific runtime exceptions (e.g., "not found" from service)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al eliminar la promoción: " + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Actualizar una promoción")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PromocionDTO dto) {
        try {
            // No need to check ID consistency or call findById in the controller.
            // The service method will handle finding the existing entity and updating it.
            PromocionDTO updatedDto = promocionService.actualizarPromocion(id, dto); // Service handles everything
            return ResponseEntity.ok(updatedDto);
        } catch (RuntimeException e) { // Catch specific runtime exceptions (e.g., "not found" from service)
            return ResponseEntity.status(HttpStatus.NOT_FOUND) // Or BAD_REQUEST if it's a validation error
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al actualizar la promoción: " + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Listar promociones paginadas")
    @GetMapping("/paged")
    public ResponseEntity<?> getPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<PromocionDTO> pageResult = promocionService.findAllPromocionesDTO(pageable); // Service returns Page of DTOs
            return ResponseEntity.ok(pageResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener las promociones paginadas: " + e.getMessage() + "\"}");
        }
    }
   
    @Operation(summary = "Buscar promociones por denominación")
    @GetMapping("/search")
    public ResponseEntity<?> searchByDenominacion(@RequestParam String denominacion) {
        try {
            // Assuming your service method `findPromocionesByDenominacion` now returns a List<PromocionDTO>
            // or you explicitly map it here if the service returns entities.
            List<PromocionDTO> promocionesDto = promocionService.findPromocionesByDenominacion(denominacion);
            return ResponseEntity.ok(promocionesDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al buscar promociones por denominación: " + e.getMessage() + "\"}");
        }
    }
}