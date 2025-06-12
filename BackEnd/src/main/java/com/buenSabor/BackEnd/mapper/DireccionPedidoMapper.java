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
        uses = {DireccionMapper.class}, 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DireccionPedidoMapper {

    
    @Mapping(source = "direccion", target = "direccion")
    DireccionPedidoDTO toDto(DireccionPedido direccionPedido);

   
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedido", ignore = true) 
    @Mapping(source = "direccion", target = "direccion") 
    DireccionPedido toEntity(DireccionPedidoDTO dto);


    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "pedido", ignore = true)
    @Mapping(source = "direccion", target = "direccion")
    void updateDireccionPedidoFromDto(DireccionPedidoDTO dto, @MappingTarget DireccionPedido entity);
}
