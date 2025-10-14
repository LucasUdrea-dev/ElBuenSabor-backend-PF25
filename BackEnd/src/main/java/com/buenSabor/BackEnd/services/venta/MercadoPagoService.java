package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.models.PreferenceMP;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MercadoPagoService {

    private static final Logger logger = LoggerFactory.getLogger(MercadoPagoService.class);

    public PreferenceMP generarPreferencia(BigDecimal monto, String frontendUrl) {
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

            PreferenceBackUrlsRequest backURL = PreferenceBackUrlsRequest.builder()
                    .success(frontendUrl + "/ordenRecibida")
                    .pending(frontendUrl + "/catalogo")
                    .failure(frontendUrl + "/catalogo")
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(List.of(itemRequest))
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
            logger.error("MercadoPago API Error - Status: {}, Content: {}", 
                e.getApiResponse().getStatusCode(), 
                e.getApiResponse().getContent());
            return null;
        } catch (MPException e) {
            logger.error("MercadoPago Exception: {}", e.getMessage(), e);
            return null;
        }
    }
}