package com.buenSabor.BackEnd.dto.producto.stock;

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
public class StockDTO {

    private Long id;
    @NotNull(message = "El stock mínimo es requerido")
    private Integer minStock;
    @NotNull(message = "La cantidad es requerida")
    private Integer cantidad;

    @NotNull(message = "La sucursal es requerida")
    private Long sucursalId;

}
