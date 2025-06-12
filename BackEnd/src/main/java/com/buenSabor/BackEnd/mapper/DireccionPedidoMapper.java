/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.venta.pedido.DireccionPedidoDTO;
import com.buenSabor.BackEnd.models.venta.DireccionPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 *
 * @author oscarloha
 */

@Mapper(componentModel = "spring",
        uses = {DireccionMapper.class}, // Still need DireccionMapper for nested DireccionDTO
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DireccionPedidoMapper {

    // To DTO: Map the entity to its DTO, including the nested DireccionDTO
    @Mapping(source = "direccion", target = "direccion")
    // If your DireccionPedido entity has a 'pedido' field and you want to ensure it's not mapped back into this DTO,
    // you might still use @Mapping(target = "pedido", ignore = true) here if PedidoDTO was still part of it.
    // But since DireccionPedidoDTO no longer has 'pedido', this is naturally handled.
    DireccionPedidoDTO toDto(DireccionPedido direccionPedido);

    // To Entity: Map the DTO to the entity.
    // 'id' will be generated, 'pedido' on the entity will be set by the service.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedido", ignore = true) // Pedido will be set by PedidoService on the entity
    @Mapping(source = "direccion", target = "direccion") // Map the nested DireccionDTO to Direccion entity
    DireccionPedido toEntity(DireccionPedidoDTO dto);

    // Update Entity: Map the DTO to an existing entity.
    @Mapping(target = "id", ignore = true) // Don't update ID
    @Mapping(target = "pedido", ignore = true) // Pedido relationship should not be updated via this mapper method
    @Mapping(source = "direccion", target = "direccion") // Update the nested Direccion entity
    void updateDireccionPedidoFromDto(DireccionPedidoDTO dto, @MappingTarget DireccionPedido entity);
}
