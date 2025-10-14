package com.buenSabor.BackEnd.dto.producto.manufacturadodetalle;

import com.buenSabor.BackEnd.dto.producto.insumo.InsumoDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticuloManufacturadoDetalleInsumoDTO {

    private Long id;
    private InsumoDTO articuloInsumo;
    private int cantidad;

    // Relación omitida: articuloManufacturado (JsonIgnore en entidad)
}