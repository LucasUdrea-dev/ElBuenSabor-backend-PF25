package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.producto.stock.StockCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.producto.stock.StockCreateDTO;
import com.buenSabor.BackEnd.dto.producto.stock.StockResponseDTO;
import com.buenSabor.BackEnd.dto.producto.stock.StockUpdateDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StockArticuloInsumoMapper extends
        BeanMapper<StockArticuloInsumo, StockResponseDTO, StockCreateDTO, StockUpdateDTO, StockCadenaSimpleDTO> {

    // <--[StockArticuloInsumo entity]--
    // ==>{StockDTO dto, y lo que ignora *-*}
    @Override
    @Mapping(source = "sucursal.id", target = "sucursalId")
    StockResponseDTO toResponseDto(StockArticuloInsumo entity);

    // <--[StockDTO dto]--
    // ==>{StockArticuloInsumo entity, y lo que ignora
    // *historicoStockArticuloInsumoList,id,articuloInsumo,sucursal*}
    @Override
    @Mapping(target = "historicoStockArticuloInsumoList", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "articuloInsumo", ignore = true)
    @Mapping(target = "sucursal", ignore = true)
    @Mapping(target = "version", ignore = true)
    StockArticuloInsumo toEntity(StockCreateDTO dto);

    // ==>{void, y lo que ignora
    // *id,historicoStockArticuloInsumoList,articuloInsumo,sucursal*}
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "historicoStockArticuloInsumoList", ignore = true)
    @Mapping(target = "articuloInsumo", ignore = true)
    @Mapping(target = "sucursal", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateFromUpdateDto(StockUpdateDTO dto, @MappingTarget StockArticuloInsumo entity);
}
