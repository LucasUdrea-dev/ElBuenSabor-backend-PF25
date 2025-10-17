package com.buenSabor.BackEnd.dto.estadisticas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngresoDataDTO {
    private String periodo;
    private Integer ordenes;
    private Double ganancias;
}
