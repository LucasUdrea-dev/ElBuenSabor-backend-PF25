package com.buenSabor.BackEnd.dto.producto.manufacturado;

import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloDTO;
import com.buenSabor.BackEnd.dto.producto.manufacturadodetalle.ArticuloManufacturadoDetalleInsumoDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticuloManufacturadoDTO extends ArticuloDTO {

    private Long id;
    private String tiempoEstimado;
    private String preparacion;
    private Long sucursalId;
    private List<ArticuloManufacturadoDetalleInsumoDTO> insumos;
    // Relaciones omitidas: detalleInsumos
}
