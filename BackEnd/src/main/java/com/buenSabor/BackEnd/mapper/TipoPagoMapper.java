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
    @Mapping(source = "tipoPago", target = "tipoPago") 
    TipoPagoDTO toDto(TipoPago tipoPago);

    // Map DTO to entity
    @Mapping(source = "tipoPago", target = "tipoPago")
    @Mapping(target = "pedidoList", ignore = true) 
    @Mapping(target = "mercadoPagoList", ignore = true) 
    @Mapping(target = "id", ignore = true) 
    TipoPago toEntity(TipoPagoDTO dto);

    // Optional: For updating an existing entity from a DTO
    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "pedidoList", ignore = true) 
    @Mapping(target = "mercadoPagoList", ignore = true) 
    void updateTipoPagoFromDto(TipoPagoDTO dto, @MappingTarget TipoPago entity);

    List<TipoPagoDTO> toDtoList(List<TipoPago> tipoPagos);
}