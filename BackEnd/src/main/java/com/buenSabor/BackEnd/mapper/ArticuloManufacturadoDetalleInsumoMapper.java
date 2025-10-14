/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.producto.manufacturadodetalle.ArticuloManufacturadoDetalleInsumoDTO;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturadoDetalleInsumo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 *
 * @author oscarloha
 */
@Mapper(componentModel = "spring", uses = {SubcategoriaMapper.class, UnidadMedidaMapper.class, StockArticuloInsumoMapper.class})
public interface ArticuloManufacturadoDetalleInsumoMapper {
    
    // <--[ArticuloManufacturadoDetalleInsumoDTO dto]--
    // ==>{ArticuloManufacturadoDetalleInsumo entity, y lo que ignora *articuloManufacturado,id,articuloInsumo*}
    @Mapping(target = "articuloManufacturado", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "articuloInsumo", ignore = true)
    ArticuloManufacturadoDetalleInsumo toEntity(ArticuloManufacturadoDetalleInsumoDTO dto);
    
    // <--[ArticuloManufacturadoDetalleInsumo entity]--
    // ==>{ArticuloManufacturadoDetalleInsumoDTO dto, y lo que ignora *-*}
    ArticuloManufacturadoDetalleInsumoDTO toDTO(ArticuloManufacturadoDetalleInsumo entity);

    // <--[ArticuloManufacturadoDetalleInsumoDTO dto, ArticuloManufacturadoDetalleInsumo entity]--
    // ==>{void, y lo que ignora *id,articuloManufacturado,articuloInsumo*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "articuloManufacturado", ignore = true)
    @Mapping(target = "articuloInsumo", ignore = true)
    void updateFromDto(ArticuloManufacturadoDetalleInsumoDTO dto, @MappingTarget ArticuloManufacturadoDetalleInsumo entity);
}
