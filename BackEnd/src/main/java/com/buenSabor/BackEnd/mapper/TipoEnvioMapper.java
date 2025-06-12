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
    @Mapping(source = "tipoDelivery", target = "tipoDelivery") // Maps the enum to its string representation
//    @Mapping(target = "pedidoList", ignore = true) // Ignore the pedidoList for DTO conversion
    TipoEnvioDTO toDto(TipoEnvio tipoEnvio);

    // Map DTO to entity
    @Mapping(source = "tipoDelivery", target = "tipoDelivery") // Maps the string back to the enum
    @Mapping(target = "pedidoList", ignore = true) // Ignore the pedidoList when converting to entity
    @Mapping(target = "id", ignore = true) // Typically ignore ID when converting a DTO to a new entity
    TipoEnvio toEntity(TipoEnvioDTO dto);

    // Optional: For updating an existing entity from a DTO
    @Mapping(target = "id", ignore = true) // ID should not be updated
    @Mapping(target = "pedidoList", ignore = true) // Collections usually handled separately or ignored on update
    void updateTipoEnvioFromDto(TipoEnvioDTO dto, @MappingTarget TipoEnvio entity);

    List<TipoEnvioDTO> toDtoList(List<TipoEnvio> tipoEnvios);
}