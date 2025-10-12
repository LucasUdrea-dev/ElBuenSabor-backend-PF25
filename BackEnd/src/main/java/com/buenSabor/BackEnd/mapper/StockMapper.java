package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.stock.StockResponse;
import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface StockMapper {

    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cantidadActual", source = "cantidad")
    @Mapping(target = "stockMinimo", source = "minStock")
    @Mapping(target = "articuloInsumoId", source = "articuloInsumo.id")
    @Mapping(target = "nombreArticulo", source = "articuloInsumo.nombre")
    @Mapping(target = "sucursalId", source = "sucursal.id")
    @Mapping(target = "nombreSucursal", source = "sucursal.nombre")
    StockResponse toDto(StockArticuloInsumo stock);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cantidadActual", source = "cantidad")
    @Mapping(target = "stockMinimo", source = "minStock")
    @Mapping(target = "articuloInsumoId", source = "articuloInsumo.id")
    @Mapping(target = "nombreArticulo", source = "articuloInsumo.nombre")
    @Mapping(target = "sucursalId", source = "sucursal.id")
    @Mapping(target = "nombreSucursal", source = "sucursal.nombre")
    StockResponse toDtoSafe(StockArticuloInsumo stock);
}
