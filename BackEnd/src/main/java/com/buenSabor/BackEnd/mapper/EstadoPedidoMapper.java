/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.venta.estado.EstadoPedidoDTO;
import com.buenSabor.BackEnd.models.venta.EstadoPedido;
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
public interface EstadoPedidoMapper {

    @Mapping(source = "nombreEstado", target = "nombreEstado") 

    EstadoPedidoDTO toDto(EstadoPedido estadoPedido);

    @Mapping(source = "nombreEstado", target = "nombreEstado") 
    @Mapping(target = "pedidoList", ignore = true) 
    @Mapping(target = "id", ignore = true) 
    EstadoPedido toEntity(EstadoPedidoDTO dto);
  
    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "pedidoList", ignore = true) 
    void updateEstadoPedidoFromDto(EstadoPedidoDTO dto, @MappingTarget EstadoPedido entity);


    List<EstadoPedidoDTO> toDtoList(List<EstadoPedido> estadoPedidos);
}