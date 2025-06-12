package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.models.PreferenceMP;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.preference.Preference;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping({"/api/mp"})
@Tag(name = "Ventas", description = "Operaciones relacionadas con entidad MercadoPago")
public class MercadoPagoIntegrationController {
    @Value("${app.frontend.base-url}")
    private String frontendUrl;


    @Operation(summary = "enviar preferencia")
    @PostMapping("/preference")
    public PreferenceMP getPreferenciaIdMercadoPago(@RequestParam("monto") BigDecimal monto){

        try {
            MercadoPagoConfig.setAccessToken("APP_USR-4638386553408540-051500-96650b52e2f6307fec6570d86426e378-2438512283");
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .id("1234")
                    .title("Compra Realizada En El Buen Sabor")
                    .description("Pedido realizado desde el carrito de compras")
                    .pictureUrl("https://img-global.cpcdn.com/recipes/0709fbb52d87d2d7/1200x630cq70/photo.jpg")
                    .quantity(1)
                    .currencyId("ARG")
                    .unitPrice(monto.setScale(2))
                    .build();
            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            PreferenceBackUrlsRequest backURL = PreferenceBackUrlsRequest.builder().success( frontendUrl+"/ordenRecibida")
                    .pending(frontendUrl + "/catalogo")
                    .failure(frontendUrl+"/catalogo").build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backURL)
                    .autoReturn("all")
                    .build();
            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            PreferenceMP mpPreference = new PreferenceMP();
            mpPreference.setStatusCode(preference.getResponse().getStatusCode());
            mpPreference.setId(preference.getId());
            return mpPreference;

        } catch (MPApiException e) {
            System.err.println("MP ERROR " + e.getApiResponse().getStatusCode());
            System.err.println(e.getApiResponse().getContent());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}