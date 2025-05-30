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
@Mapper(componentModel = "spring", uses = {ArticuloMapper.class})
public interface ArticuloManufacturadoDetalleInsumoMapper {
    
    @Mapping(target = "articuloManufacturado", ignore = true)
              @Mapping(target = "id", ignore = true)
    ArticuloManufacturadoDetalleInsumo toEntity(ArticuloManufacturadoDetalleInsumoDTO dto);
    ArticuloManufacturadoDetalleInsumoDTO toDTO(ArticuloManufacturadoDetalleInsumo entity);
}
