/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.venta.detallepedido.DetallePedidoDTO;
import com.buenSabor.BackEnd.models.venta.DetallePedido;
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
        uses = { ArticuloMapper.class }, // Specify ArticuloMapper for nested ArticuloDTO
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DetallePedidoMapper {

    // Map entity to DTO
    @Mapping(source = "articulo", target = "articulo") // Map the Articulo entity to ArticuloDTO
//    @Mapping(target = "pedido", ignore = true) // Ignore the 'pedido' field as it's part of the parent DTO
    DetallePedidoDTO toDto(DetallePedido detallePedido);

    // Map DTO to entity
    @Mapping(source = "articulo", target = "articulo") // Map the ArticuloDTO to Articulo entity
    @Mapping(target = "pedido", ignore = true) // Ignore 'pedido'; it will be set in the service when associating with the parent Pedido
    @Mapping(target = "id", ignore = true) // Typically ignore ID when converting a DTO to a new entity
    DetallePedido toEntity(DetallePedidoDTO dto);

    // Optional: For updating an existing entity from a DTO
    @Mapping(target = "id", ignore = true) // ID should not be updated from DTO
    @Mapping(target = "pedido", ignore = true) // 'pedido' is handled by the parent service logic
    void updateDetallePedidoFromDto(DetallePedidoDTO dto, @MappingTarget DetallePedido entity);

    List<DetallePedidoDTO> toDtoList(List<DetallePedido> detallePedidos);
}
