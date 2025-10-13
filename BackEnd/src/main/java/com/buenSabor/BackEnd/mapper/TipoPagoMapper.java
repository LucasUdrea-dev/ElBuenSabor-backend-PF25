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
    // <--[TipoPago tipoPago]--
    // ==>{TipoPagoDTO dto, y lo que ignora *-*}
    @Mapping(source = "tipoPago", target = "tipoPago") 
    TipoPagoDTO toDto(TipoPago tipoPago);

    // Map DTO to entity
    // <--[TipoPagoDTO dto]--
    // ==>{TipoPago entity, y lo que ignora *pedidoList,mercadoPagoList*}
    // IMPORTANTE: NO ignorar el ID - TipoPago está precargado en BD por TipoPagoInitializer
    @Mapping(source = "tipoPago", target = "tipoPago")
    @Mapping(target = "pedidoList", ignore = true) 
    @Mapping(target = "mercadoPagoList", ignore = true) 
    TipoPago toEntity(TipoPagoDTO dto);

    // Optional: For updating an existing entity from a DTO
    // <--[TipoPagoDTO dto, TipoPago entity]--
    // ==>{void, y lo que ignora *id,pedidoList,mercadoPagoList*}
    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "pedidoList", ignore = true) 
    @Mapping(target = "mercadoPagoList", ignore = true) 
    void updateTipoPagoFromDto(TipoPagoDTO dto, @MappingTarget TipoPago entity);

    // <--[List<TipoPago> tipoPagos]--
    // ==>{List<TipoPagoDTO> list, y lo que ignora *-*}
    List<TipoPagoDTO> toDtoList(List<TipoPago> tipoPagos);
}