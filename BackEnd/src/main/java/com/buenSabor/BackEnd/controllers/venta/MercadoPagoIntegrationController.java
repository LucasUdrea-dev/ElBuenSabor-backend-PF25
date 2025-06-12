package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.models.PreferenceMP;
import com.buenSabor.BackEnd.services.venta.MercadoPagoService;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/mp")
@Tag(name = "Ventas", description = "Operaciones relacionadas con entidad MercadoPago")
public class MercadoPagoIntegrationController {

    @Value("${app.frontend.base-url}")
    private String frontendUrl;

    private final MercadoPagoService mercadoPagoService;

    public MercadoPagoIntegrationController(MercadoPagoService mercadoPagoService) {
        this.mercadoPagoService = mercadoPagoService;
    }

    @Operation(summary = "enviar preferencia")
    @PostMapping("/preference")
    public ResponseEntity<PreferenceMP> getPreferenciaIdMercadoPago(@RequestParam("monto") BigDecimal monto) {
        PreferenceMP preference = mercadoPagoService.generarPreferencia(monto, frontendUrl);
        return preference != null ? ResponseEntity.ok(preference) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
