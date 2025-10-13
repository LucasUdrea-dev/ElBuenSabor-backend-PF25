package com.buenSabor.BackEnd.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockResponse {
    private Long id;
    private Long articuloInsumoId;
    private String nombreArticulo;
    private Integer cantidadActual;
    private Integer stockMinimo;
    private Long sucursalId;
    private String nombreSucursal;
}
