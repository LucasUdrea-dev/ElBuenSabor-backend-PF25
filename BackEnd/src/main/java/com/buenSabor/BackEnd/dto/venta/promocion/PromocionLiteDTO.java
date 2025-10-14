package com.buenSabor.BackEnd.dto.venta.promocion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromocionLiteDTO {
    private Long id;
    private String denominacion;
    private String descripcion;
    private Double precioRebajado;
    private String imagen;
}