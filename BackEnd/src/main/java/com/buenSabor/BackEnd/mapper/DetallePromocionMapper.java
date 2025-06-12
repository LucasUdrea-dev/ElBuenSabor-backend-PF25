/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.venta.detallepromocion.DetallePromocionDTO;
import com.buenSabor.BackEnd.models.venta.DetallePromocion;
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
        uses = { PromocionMapper.class }, 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DetallePromocionMapper {

    
    @Mapping(source = "promocion", target = "promocion") 
//    @Mapping(target = "pedido", ignore = true) 
    DetallePromocionDTO toDto(DetallePromocion detallePromocion);

    
    @Mapping(source = "promocion", target = "promocion") 
    @Mapping(target = "pedido", ignore = true) 
    @Mapping(target = "id", ignore = true) 
    DetallePromocion toEntity(DetallePromocionDTO dto);

    
    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "pedido", ignore = true) 
    void updateDetallePromocionFromDto(DetallePromocionDTO dto, @MappingTarget DetallePromocion entity);

    List<DetallePromocionDTO> toDtoList(List<DetallePromocion> detallePromociones);
}