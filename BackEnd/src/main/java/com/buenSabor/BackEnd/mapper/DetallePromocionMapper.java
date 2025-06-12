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
        uses = { PromocionMapper.class }, // Specify PromocionMapper for nested PromocionDTO
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DetallePromocionMapper {

    // Map entity to DTO
    @Mapping(source = "promocion", target = "promocion") // Map the Promocion entity to PromocionDTO
//    @Mapping(target = "pedido", ignore = true) // Ignore the 'pedido' field as it's part of the parent DTO
    DetallePromocionDTO toDto(DetallePromocion detallePromocion);

    // Map DTO to entity
    @Mapping(source = "promocion", target = "promocion") // Map the PromocionDTO to Promocion entity
    @Mapping(target = "pedido", ignore = true) // Ignore 'pedido'; it will be set in the service when associating with the parent Pedido
    @Mapping(target = "id", ignore = true) // Typically ignore ID when converting a DTO to a new entity
    DetallePromocion toEntity(DetallePromocionDTO dto);

    // Optional: For updating an existing entity from a DTO
    @Mapping(target = "id", ignore = true) // ID should not be updated from DTO
    @Mapping(target = "pedido", ignore = true) // 'pedido' is handled by the parent service logic
    void updateDetallePromocionFromDto(DetallePromocionDTO dto, @MappingTarget DetallePromocion entity);

    List<DetallePromocionDTO> toDtoList(List<DetallePromocion> detallePromociones);
}