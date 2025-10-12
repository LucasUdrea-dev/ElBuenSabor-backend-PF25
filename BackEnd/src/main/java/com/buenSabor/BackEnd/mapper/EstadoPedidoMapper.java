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

    // <--[EstadoPedido estadoPedido]--
    // ==>{EstadoPedidoDTO dto, y lo que ignora *-*}
    @Mapping(source = "nombreEstado", target = "nombreEstado") 
    EstadoPedidoDTO toDto(EstadoPedido estadoPedido);

    // <--[EstadoPedidoDTO dto]--
    // ==>{EstadoPedido entity, y lo que ignora *pedidoList,id*}
    @Mapping(source = "nombreEstado", target = "nombreEstado") 
    @Mapping(target = "pedidoList", ignore = true) 
    @Mapping(target = "id", ignore = true) 
    EstadoPedido toEntity(EstadoPedidoDTO dto);
  
    // <--[EstadoPedidoDTO dto, EstadoPedido entity]--
    // ==>{void, y lo que ignora *id,pedidoList*}
    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "pedidoList", ignore = true) 
    void updateEstadoPedidoFromDto(EstadoPedidoDTO dto, @MappingTarget EstadoPedido entity);

    // <--[List<EstadoPedido> estadoPedidos]--
    // ==>{List<EstadoPedidoDTO> list, y lo que ignora *-*}
    List<EstadoPedidoDTO> toDtoList(List<EstadoPedido> estadoPedidos);
}