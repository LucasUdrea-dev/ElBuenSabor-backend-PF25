package com.buenSabor.BackEnd.dto.stock;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddStockRequest {
    @NotNull(message = "El ID del insumo es obligatorio")
    private Long idInsumo;
    
    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser un número positivo")
    private Double cantidad;
    
    @NotNull(message = "El ID de la sucursal es obligatorio")
    private Long sucursalId;
}
