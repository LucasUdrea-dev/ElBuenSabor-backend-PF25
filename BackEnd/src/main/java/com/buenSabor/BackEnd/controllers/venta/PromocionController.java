package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.dto.venta.promocion.PromocionDTO;
import com.buenSabor.BackEnd.dto.venta.promocion.PromocionLiteDTO;
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

    private final PromocionService promocionService;

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
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<PromocionDTO> promociones = promocionService.findAllPromocionesDTO();
            return ResponseEntity.ok(promociones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al listar las promociones: " + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Guardar una nueva promoción")
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody PromocionDTO dto) {
        try {
            PromocionDTO savedDto = promocionService.crearPromocion(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedDto);
        } catch (RuntimeException e) {

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
            promocionService.eliminarPromocion(id);
            return ResponseEntity.ok("{\"message\":\"Promoción eliminada con éxito.\"}");
        } catch (RuntimeException e) {
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

            PromocionDTO updatedDto = promocionService.actualizarPromocion(id, dto);
            return ResponseEntity.ok(updatedDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
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
            Page<PromocionDTO> pageResult = promocionService.findAllPromocionesDTO(pageable);
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

            List<PromocionDTO> promocionesDto = promocionService.findPromocionesByDenominacion(denominacion);
            return ResponseEntity.ok(promocionesDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al buscar promociones por denominación: " + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Ver una promoción por ID (versión lite)")
    @GetMapping("/lite/{id}") // New endpoint path for lite version
    public ResponseEntity<?> getLiteById(@PathVariable Long id) {
        try {
            PromocionLiteDTO promocionLiteDTO = promocionService.findPromocionLiteById(id);
            if (promocionLiteDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Promoción lite no encontrada.\"}");
            }
            return ResponseEntity.ok(promocionLiteDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener la promoción lite: " + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Listar todas las promociones (versión lite)")
    @GetMapping("/lite") // New endpoint path for lite version
    public ResponseEntity<?> getAllLite() {
        try {
            List<PromocionLiteDTO> promocionesLite = promocionService.findAllPromocionesLite();
            return ResponseEntity.ok(promocionesLite);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al listar las promociones lite: " + e.getMessage() + "\"}");
        }
    }
    
    
    @Operation(summary = "Listar todas las promociones (versión lite) y condicionadas por existe")
    @GetMapping("/lite/existente") // New endpoint path for lite version
    public ResponseEntity<?> getAllLiteExist() {
        try {
            List<PromocionLiteDTO> promocionesLite = promocionService.findAllPromocionesExistentesLite();
            return ResponseEntity.ok(promocionesLite);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al listar las promociones lite: " + e.getMessage() + "\"}");
        }
    }
    
    
    @Operation(summary = "Listar todas las promociones y condicionadas por existe")
    @GetMapping("/existente") // New endpoint path for lite version
    public ResponseEntity<?> getAllExist() {
         try {
            List<PromocionDTO> promociones = promocionService.findAllPromocionesExistentesDTO();
            return ResponseEntity.ok(promociones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al listar las promociones: " + e.getMessage() + "\"}");
        }
    }
}

