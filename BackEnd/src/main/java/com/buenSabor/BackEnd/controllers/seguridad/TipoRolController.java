package com.buenSabor.BackEnd.controllers.seguridad;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.seguridad.tiporol.TipoRolDTO;
import com.buenSabor.BackEnd.models.seguridad.TipoRol;
import com.buenSabor.BackEnd.services.seguridad.TipoRolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tipoRoles")
@Tag(name = "TipoRoles", description = "Operaciones relacionadas con entidad TipoRol")
public class TipoRolController extends BeanControllerImpl<TipoRol, TipoRolService> {
    
     private final TipoRolService tipoRolService;

    @Autowired
    public TipoRolController(TipoRolService tipoRolService) {
        this.tipoRolService = tipoRolService;
    }

    @Operation(summary = "Listar todas los tipos de rol con DTO")
    @GetMapping("/listar") // Or just @GetMapping if you prefer /api/tipoRoles directly for listing all
    public ResponseEntity<?> findAllTipoRolesDTO() { // Renamed method for clarity
        try {
            // Call the service method that returns the DTO list directly
            List<TipoRolDTO> dtos = tipoRolService.findAllTipoRolesDTO();
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener los tipos de rol: " + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Ver un tipo de rol por ID con DTO")
    @GetMapping("/dto/{id}") // Added /dto to differentiate if BeanControllerImpl also has a getById
    public ResponseEntity<?> getTipoRolDTOById(@PathVariable Long id) {
        try {
            TipoRolDTO tipoRolDTO = tipoRolService.findTipoRolDTOById(id);
            if (tipoRolDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Tipo de rol con ID " + id + " no encontrado.\"}");
            }
            return ResponseEntity.ok(tipoRolDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener el tipo de rol por ID: " + e.getMessage() + "\"}");
        }
    }
    
}
