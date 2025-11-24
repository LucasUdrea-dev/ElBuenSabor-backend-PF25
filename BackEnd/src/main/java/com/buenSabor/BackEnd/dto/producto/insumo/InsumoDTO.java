package com.buenSabor.BackEnd.dto.producto.insumo;

import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloDTO;
import com.buenSabor.BackEnd.dto.producto.stock.StockDTO;
import jakarta.validation.constraints.NotNull;
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
public class InsumoDTO extends ArticuloDTO {

    private Long id;
    private Double precioCompra;
    @NotNull(message = "El stock es requerido")
    private StockDTO stockArticuloInsumo;

}
