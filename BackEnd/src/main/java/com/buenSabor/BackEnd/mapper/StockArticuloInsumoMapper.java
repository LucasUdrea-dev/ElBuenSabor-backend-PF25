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

    @Mapping(target = "sucursalId", ignore = true)
    StockDTO toDTO(StockArticuloInsumo entity);

   
    
    @Mapping(target = "historicoStockArticuloInsumoList", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "articuloInsumo", ignore = true)
    @Mapping(target = "sucursal", ignore = true)
    StockArticuloInsumo toEntity(StockDTO dto);
}
