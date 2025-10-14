package com.buenSabor.BackEnd.dto.estadisticas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsumoStockDTO {
    private Long id;
    private String nombre;
    private Integer nivelActual;
    private Integer nivelMinimo;
    private Integer nivelMaximo;
    private String unidad;
    private List<Integer> stockHistorico;
}
