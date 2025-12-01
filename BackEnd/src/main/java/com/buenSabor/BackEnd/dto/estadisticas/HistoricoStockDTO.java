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
public class HistoricoStockDTO {
    private Long id;
    private Integer cantidad;
    private Date fechaActualizacion;
    private Long stockArticuloInsumoId;
}
