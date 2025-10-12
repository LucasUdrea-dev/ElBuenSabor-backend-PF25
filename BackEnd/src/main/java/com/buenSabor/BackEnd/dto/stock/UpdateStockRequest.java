package com.buenSabor.BackEnd.dto.stock;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStockRequest {
    @NotNull(message = "El ID del stock es obligatorio")
    private Long id;
    
    @PositiveOrZero(message = "La cantidad debe ser un número positivo o cero")
    private Integer cantidad;
    
    @PositiveOrZero(message = "El stock mínimo debe ser un número positivo o cero")
    private Integer stockMinimo;
}
