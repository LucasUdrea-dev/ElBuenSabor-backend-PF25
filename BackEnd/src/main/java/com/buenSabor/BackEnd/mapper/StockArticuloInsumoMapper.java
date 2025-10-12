/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.producto.stock.StockDTO;
import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author oscarloha
 */
@Mapper(componentModel = "spring")
public interface StockArticuloInsumoMapper {

    // <--[StockArticuloInsumo entity]--
    // ==>{StockDTO dto, y lo que ignora *sucursalId*}
    @Mapping(target = "sucursalId", ignore = true)
    StockDTO toDTO(StockArticuloInsumo entity);

   
    
    // <--[StockDTO dto]--
    // ==>{StockArticuloInsumo entity, y lo que ignora *historicoStockArticuloInsumoList,id,articuloInsumo,sucursal*}
    @Mapping(target = "historicoStockArticuloInsumoList", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "articuloInsumo", ignore = true)
    @Mapping(target = "sucursal", ignore = true)
    StockArticuloInsumo toEntity(StockDTO dto);

    // <--[StockDTO dto, StockArticuloInsumo entity]--
    // ==>{void, y lo que ignora *id,historicoStockArticuloInsumoList,articuloInsumo,sucursal*}
    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "historicoStockArticuloInsumoList", ignore = true)
    @org.mapstruct.Mapping(target = "articuloInsumo", ignore = true)
    @org.mapstruct.Mapping(target = "sucursal", ignore = true)
    void updateFromDto(StockDTO dto, @org.mapstruct.MappingTarget StockArticuloInsumo entity);
}
