package com.buenSabor.BackEnd.dto.venta.promocionArticulo;

import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromocionArticuloDTO {

    private Long id;
    private ArticuloDTO articulo;

    // La cantidad de ese artículo para esta promoción específica
    private int cantidad;
}
