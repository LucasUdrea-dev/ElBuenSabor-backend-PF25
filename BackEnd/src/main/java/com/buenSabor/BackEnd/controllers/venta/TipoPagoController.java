package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.venta.pago.TipoPagoDTO;
import com.buenSabor.BackEnd.models.venta.TipoPago;
import com.buenSabor.BackEnd.services.venta.TipoPagoService;
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
@RequestMapping("api/tipoPagos")
@Tag(name = "TipoPago", description = "Operaciones relacionadas con entidad TipoPago")
public class TipoPagoController extends BeanControllerImpl<TipoPago, TipoPagoService> {

    @Autowired
    private TipoPagoService tipoPagoService;

    @Operation(summary = "Listar todas los tipos de pagos con DTO")
    @GetMapping("") 
    public ResponseEntity<?> findAllTipoRolesDTO() { 
        try {
            
            List<TipoPagoDTO> dtos = tipoPagoService.findAllTipoPagosDTO();
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener los tipos de rol: " + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Ver un tipo de rol por ID con DTO")
    @GetMapping("/dto/{id}") 
    public ResponseEntity<?> getTipoRolDTOById(@PathVariable Long id) {
        try {
            TipoPagoDTO tiposDTO = tipoPagoService.findTipoPagoDTOById(id);
            if (tiposDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Tipo de rol con ID " + id + " no encontrado.\"}");
            }
            return ResponseEntity.ok(tiposDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener el tipo de rol por ID: " + e.getMessage() + "\"}");
        }
    }
}
