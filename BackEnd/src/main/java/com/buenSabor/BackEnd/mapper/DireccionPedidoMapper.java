package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.venta.pedido.DireccionPedidoDTO;
import com.buenSabor.BackEnd.models.venta.DireccionPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {
        DireccionMapper.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DireccionPedidoMapper {

    // <--[DireccionPedido direccionPedido]--
    // ==>{DireccionPedidoDTO dto, y lo que ignora *-*}
    @Mapping(source = "direccion", target = "direccion")
    DireccionPedidoDTO toDto(DireccionPedido direccionPedido);

    // <--[DireccionPedidoDTO dto]--
    // ==>{DireccionPedido entity, y lo que ignora *id,pedido*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(source = "direccion", target = "direccion")
    DireccionPedido toEntity(DireccionPedidoDTO dto);

    // <--[DireccionPedidoDTO dto, DireccionPedido entity]--
    // ==>{void, y lo que ignora *id,pedido*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(source = "direccion", target = "direccion")
    void updateDireccionPedidoFromDto(DireccionPedidoDTO dto, @MappingTarget DireccionPedido entity);
}
