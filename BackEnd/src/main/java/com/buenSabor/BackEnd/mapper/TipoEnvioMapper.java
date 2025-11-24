/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.config.mapperConfig;
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

@Mapper(componentModel = "spring", config = mapperConfig.class)
public interface TipoEnvioMapper {

    // Map entity to DTO
    // <--[TipoEnvio tipoEnvio]--
    // ==>{TipoEnvioDTO dto, y lo que ignora *-*}
    TipoEnvioDTO toDto(TipoEnvio tipoEnvio);

    // Map DTO to entity
    // <--[TipoEnvioDTO dto]--
    // ==>{TipoEnvio entity, y lo que ignora *pedidoList*}
    // IMPORTANTE: NO ignorar el ID - TipoEnvio está precargado en BD por
    // TypeDeliveryInitializer
    @Mapping(target = "pedidoList", ignore = true)
    TipoEnvio toEntity(TipoEnvioDTO dto);

    // <--[TipoEnvioDTO dto, TipoEnvio entity]--
    // ==>{void, y lo que ignora *id,pedidoList*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedidoList", ignore = true)
    void updateFromDto(TipoEnvioDTO dto, @MappingTarget TipoEnvio entity);

    // <--[List<TipoEnvio> tipoEnvios]--
    // ==>{List<TipoEnvioDTO> list, y lo que ignora *-*}
    List<TipoEnvioDTO> toDtoList(List<TipoEnvio> tipoEnvios);
}