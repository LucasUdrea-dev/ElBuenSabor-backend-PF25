package com.buenSabor.BackEnd.dto.venta.detallepromocion;

import com.buenSabor.BackEnd.dto.venta.promocion.PromocionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetallePromocionDTO {
    private Long id;

    private PromocionDTO promocion;

    private int cantidad;

}
