/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.producto.manufacturadodetalle.ArticuloManufacturadoDetalleInsumoDTO;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturadoDetalleInsumo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author oscarloha
 */
@Mapper(componentModel = "spring", uses = {ArticuloMapper.class,StockArticuloInsumoMapper.class})
public interface ArticuloManufacturadoDetalleInsumoMapper {
    
    // <--[ArticuloManufacturadoDetalleInsumoDTO dto]--
    // ==>{ArticuloManufacturadoDetalleInsumo entity, y lo que ignora *articuloManufacturado,id*}
    @Mapping(target = "articuloManufacturado", ignore = true)
    @Mapping(target = "id", ignore = true)
    ArticuloManufacturadoDetalleInsumo toEntity(ArticuloManufacturadoDetalleInsumoDTO dto);
    // <--[ArticuloManufacturadoDetalleInsumo entity]--
    // ==>{ArticuloManufacturadoDetalleInsumoDTO dto, y lo que ignora *-*}
    ArticuloManufacturadoDetalleInsumoDTO toDTO(ArticuloManufacturadoDetalleInsumo entity);

    // <--[ArticuloManufacturadoDetalleInsumoDTO dto, ArticuloManufacturadoDetalleInsumo entity]--
    // ==>{void, y lo que ignora *id,articuloManufacturado*}
    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "articuloManufacturado", ignore = true)
    void updateFromDto(ArticuloManufacturadoDetalleInsumoDTO dto, @org.mapstruct.MappingTarget ArticuloManufacturadoDetalleInsumo entity);
}
