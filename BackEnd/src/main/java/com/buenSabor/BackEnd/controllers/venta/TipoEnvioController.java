package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.venta.envio.TipoEnvioDTO;
import com.buenSabor.BackEnd.enums.TypeDelivery;
import com.buenSabor.BackEnd.models.venta.TipoEnvio;
import com.buenSabor.BackEnd.repositories.venta.TipoEnvioRepository;
import com.buenSabor.BackEnd.services.venta.TipoEnvioService;
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
@RequestMapping("api/tipoEnvios")
@Tag(name = "TipoEnvio", description = "Operaciones relacionadas con entidad TipoEnvio")
public class TipoEnvioController extends BeanControllerImpl<TipoEnvio, TipoEnvioService> {

    private final TipoEnvioService tipoEnvioService; 
    private final TipoEnvioRepository tipoEnvioRepository; 

    @Autowired
    public TipoEnvioController(TipoEnvioService tipoEnvioService,
            TipoEnvioRepository tipoEnvioRepository) { 

        this.tipoEnvioService = tipoEnvioService;
        this.tipoEnvioRepository = tipoEnvioRepository;
    }

 
    @Operation(summary = "Listar todos los tipos de envío disponibles (DTO)")
    @GetMapping("") 
    public ResponseEntity<?> findAllTipoEnviosDTO() {
        try {
            List<TipoEnvioDTO> dtos = tipoEnvioService.findAllTipoEnviosDTO();
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            System.err.println("Error al obtener los tipos de envío: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener los tipos de envío.\"}");
        }
    }

    @Operation(summary = "Obtener un tipo de envío por su ID (DTO)")
    @GetMapping("/dto/{id}") 
    public ResponseEntity<?> getTipoEnvioDTOById(@PathVariable Long id) {
        try {
            TipoEnvioDTO tipoEnvioDTO = tipoEnvioService.findTipoEnvioDTOById(id);
            if (tipoEnvioDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Tipo de envío con ID " + id + " no encontrado.\"}");
            }
            return ResponseEntity.ok(tipoEnvioDTO);
        } catch (Exception e) {
            System.err.println("Error al obtener el tipo de envío por ID: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener el tipo de envío por ID.\"}");
        }
    }
}
