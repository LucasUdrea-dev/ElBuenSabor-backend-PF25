package com.buenSabor.BackEnd.dto.estadisticas;

import com.buenSabor.BackEnd.models.producto.HistoricoPrecioCostoArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.HistoricoPrecioVentaArticulo;
import com.buenSabor.BackEnd.models.producto.HistoricoStockArticuloInsumo;
import com.buenSabor.BackEnd.models.venta.HistoricoEstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodosHistoricosDTO {
    private List<HistoricoPrecioCostoArticuloInsumo> historicosPrecioCosto;
    private List<HistoricoPrecioVentaArticulo> historicosPrecioVenta;
    private List<HistoricoStockArticuloInsumo> historicosStock;
    private List<HistoricoEstadoPedido> historicosEstadoPedido;
}
