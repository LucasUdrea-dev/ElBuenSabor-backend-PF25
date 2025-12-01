package com.buenSabor.BackEnd.dto.estadisticas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoPrecioCostoDTO {
    private Long id;
    private Date fecha;
    private Double precio;
    private Long articuloInsumoId;
}
