package com.buenSabor.BackEnd.dto.venta.promocion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromocionDetalladoDTO {

    private Long id;
    private String denominacion;
    private String descripcion;
    private Double precioRebajado;
    private Boolean existe;
    private String imagen;

    // private SucursalDTO sucursal;
    // private TipoPromocionDTO tipoPromocion;
    //
    // // Lista de DTOs para las relaciones 'ToMany'
    // private List<PromocionArticuloDTO> articulos;

}