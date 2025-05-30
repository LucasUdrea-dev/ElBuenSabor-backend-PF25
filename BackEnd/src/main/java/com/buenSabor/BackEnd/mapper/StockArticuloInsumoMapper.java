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
@Mapping(source = "idArticuloInsumo.id", target = "idArticuloInsumo")
    @Mapping(source = "idArticuloInsumo.nombre", target = "nombreArticuloInsumo")
    @Mapping(source = "idSucursal.id", target = "idSucursal")
    @Mapping(source = "idSucursal.nombre", target = "nombreSucursal")
    StockDTO toDTO(StockArticuloInsumo entity);

    @Mapping(target = "idArticuloInsumo.id", source = "idArticuloInsumo")
    @Mapping(target = "idSucursal.id", source = "idSucursal")
    @Mapping(target = "idArticuloInsumo", ignore = true) // lo puedes setear manualmente si lo traes desde base de datos
    @Mapping(target = "idSucursal", ignore = true)       // igual aquí
    @Mapping(target = "historicoStockArticuloInsumoList", ignore = true)
              @Mapping(target = "id", ignore = true)
    StockArticuloInsumo toEntity(StockDTO dto);
}
