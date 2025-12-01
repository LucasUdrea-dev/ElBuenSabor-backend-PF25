package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.estadisticas.HistoricoEstadoPedidoDTO;
import com.buenSabor.BackEnd.dto.estadisticas.HistoricoPrecioCostoDTO;
import com.buenSabor.BackEnd.dto.estadisticas.HistoricoPrecioVentaDTO;
import com.buenSabor.BackEnd.dto.estadisticas.HistoricoStockDTO;
import com.buenSabor.BackEnd.models.producto.HistoricoPrecioCostoArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.HistoricoPrecioVentaArticulo;
import com.buenSabor.BackEnd.models.producto.HistoricoStockArticuloInsumo;
import com.buenSabor.BackEnd.models.venta.HistoricoEstadoPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface HistoricoMapper {

    HistoricoMapper INSTANCE = Mappers.getMapper(HistoricoMapper.class);

    @Mapping(source = "idstockarticuloInsumo.id", target = "stockArticuloInsumoId")
    HistoricoStockDTO toStockDTO(HistoricoStockArticuloInsumo entity);
    List<HistoricoStockDTO> toStockDTOList(List<HistoricoStockArticuloInsumo> entityList);

    @Mapping(source = "idArticuloInsumo.id", target = "articuloInsumoId")
    HistoricoPrecioCostoDTO toPrecioCostoDTO(HistoricoPrecioCostoArticuloInsumo entity);
    List<HistoricoPrecioCostoDTO> toPrecioCostoDTOList(List<HistoricoPrecioCostoArticuloInsumo> entityList);

    @Mapping(source = "idArticulo.id", target = "articuloId")
    HistoricoPrecioVentaDTO toPrecioVentaDTO(HistoricoPrecioVentaArticulo entity);
    List<HistoricoPrecioVentaDTO> toPrecioVentaDTOList(List<HistoricoPrecioVentaArticulo> entityList);

    @Mapping(source = "pedido.id", target = "pedidoId")
    @Mapping(source = "estadoPedido.nombreEstado", target = "nombreEstadoPedido")
    HistoricoEstadoPedidoDTO toEstadoPedidoDTO(HistoricoEstadoPedido entity);
    List<HistoricoEstadoPedidoDTO> toEstadoPedidoDTOList(List<HistoricoEstadoPedido> entityList);
}
