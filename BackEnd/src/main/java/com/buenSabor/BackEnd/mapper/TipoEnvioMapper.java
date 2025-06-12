/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.venta.envio.TipoEnvioDTO;
import com.buenSabor.BackEnd.models.venta.TipoEnvio;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 *
 * @author oscarloha
 */

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TipoEnvioMapper {

    // Map entity to DTO
    @Mapping(source = "tipoDelivery", target = "tipoDelivery") 
    TipoEnvioDTO toDto(TipoEnvio tipoEnvio);

    // Map DTO to entity
    @Mapping(source = "tipoDelivery", target = "tipoDelivery") 
    @Mapping(target = "pedidoList", ignore = true) 
    @Mapping(target = "id", ignore = true) 
    TipoEnvio toEntity(TipoEnvioDTO dto);

   
    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "pedidoList", ignore = true) 
    void updateTipoEnvioFromDto(TipoEnvioDTO dto, @MappingTarget TipoEnvio entity);

    List<TipoEnvioDTO> toDtoList(List<TipoEnvio> tipoEnvios);
}