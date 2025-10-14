package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.venta.detallepedido.DetallePedidoDTO;
import com.buenSabor.BackEnd.models.venta.DetallePedido;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {
        ArticuloMapper.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DetallePedidoMapper {

    // Map entity to DTO
    // <--[DetallePedido detallePedido]--
    // ==>{DetallePedidoDTO dto, y lo que ignora *-*}
    @Mapping(source = "articulo", target = "articulo")
    // @Mapping(target = "pedido", ignore = true)
    DetallePedidoDTO toDto(DetallePedido detallePedido);

    // Map DTO to entity
    // <--[DetallePedidoDTO dto]--
    // ==>{DetallePedido entity, y lo que ignora *pedido,id*}
    @Mapping(source = "articulo", target = "articulo")
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "id", ignore = true)
    DetallePedido toEntity(DetallePedidoDTO dto);

    // Optional: For updating an existing entity from a DTO
    // <--[DetallePedidoDTO dto, DetallePedido entity]--
    // ==>{void, y lo que ignora *id,pedido*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    void updateDetallePedidoFromDto(DetallePedidoDTO dto, @MappingTarget DetallePedido entity);

    // <--[List<DetallePedido> detallePedidos]--
    // ==>{List<DetallePedidoDTO> list, y lo que ignora *-*}
    List<DetallePedidoDTO> toDtoList(List<DetallePedido> detallePedidos);
}
