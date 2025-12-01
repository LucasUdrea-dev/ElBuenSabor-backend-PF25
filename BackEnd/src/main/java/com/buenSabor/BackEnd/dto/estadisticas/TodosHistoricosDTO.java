package com.buenSabor.BackEnd.dto.estadisticas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodosHistoricosDTO {
    private List<HistoricoPrecioCostoDTO> historicosPrecioCosto;
    private List<HistoricoPrecioVentaDTO> historicosPrecioVenta;
    private List<HistoricoStockDTO> historicosStock;
    private List<HistoricoEstadoPedidoDTO> historicosEstadoPedido;
}
