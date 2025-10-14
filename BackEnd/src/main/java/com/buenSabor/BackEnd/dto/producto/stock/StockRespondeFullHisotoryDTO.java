package com.buenSabor.BackEnd.dto.producto.stock;

import com.buenSabor.BackEnd.dto.producto.insumo.InsumoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockRespondeFullHisotoryDTO {

    private Long id;
    private Integer minStock;
    private InsumoDTO articuloInsumo;
    // historico:
    private Integer cantidad;
    private String fechaActualizacion;

}
