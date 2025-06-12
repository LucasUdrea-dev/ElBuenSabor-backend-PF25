/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.venta.pago.TipoPagoDTO;
import com.buenSabor.BackEnd.models.venta.TipoPago;
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
public interface TipoPagoMapper {

    // Map entity to DTO
    @Mapping(source = "tipoPago", target = "tipoPago") // Maps the enum to its string representation
//    @Mapping(target = "pedidoList", ignore = true) // Ignore pedidoList for DTO conversion
//    @Mapping(target = "mercadoPagoList", ignore = true) // Ignore mercadoPagoList for DTO conversion
    TipoPagoDTO toDto(TipoPago tipoPago);

    // Map DTO to entity
    @Mapping(source = "tipoPago", target = "tipoPago") // Maps the string back to the enum
    @Mapping(target = "pedidoList", ignore = true) // Ignore pedidoList when converting to entity
    @Mapping(target = "mercadoPagoList", ignore = true) // Ignore mercadoPagoList when converting to entity
    @Mapping(target = "id", ignore = true) // Typically ignore ID when converting a DTO to a new entity
    TipoPago toEntity(TipoPagoDTO dto);

    // Optional: For updating an existing entity from a DTO
    @Mapping(target = "id", ignore = true) // ID should not be updated
    @Mapping(target = "pedidoList", ignore = true) // Collections usually handled separately or ignored on update
    @Mapping(target = "mercadoPagoList", ignore = true) // Collections usually handled separately or ignored on update
    void updateTipoPagoFromDto(TipoPagoDTO dto, @MappingTarget TipoPago entity);

    List<TipoPagoDTO> toDtoList(List<TipoPago> tipoPagos);
}