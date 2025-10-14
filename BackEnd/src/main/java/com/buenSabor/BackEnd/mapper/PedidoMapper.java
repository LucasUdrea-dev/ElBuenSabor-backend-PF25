package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.venta.pedido.PedidoConDireccionDTO;
import com.buenSabor.BackEnd.dto.venta.pedido.PedidoDTO;
import com.buenSabor.BackEnd.models.venta.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        EstadoPedidoMapper.class,
        SucursalMapper.class,
        TipoEnvioMapper.class,
        TipoPagoMapper.class,
        UsuarioMapper.class,
        DetallePedidoMapper.class,
        DetallePromocionMapper.class,

}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PedidoMapper {

    // Maps Pedido entity to PedidoConDireccionDTO (includes address)
    // <--[Pedido pedido]--
    // ==>{PedidoConDireccionDTO dto, y lo que ignora *-*}
    @Mapping(source = "estadoPedido", target = "estadoPedido")
    @Mapping(source = "sucursal", target = "sucursal")
    @Mapping(source = "tipoEnvio", target = "tipoEnvio")
    @Mapping(source = "tipoPago", target = "tipoPago")
    @Mapping(source = "usuario", target = "usuario")
    @Mapping(source = "detallePedidoList", target = "detallePedidoList")
    @Mapping(source = "detallePromocionList", target = "detallePromocionList")
    @Mapping(source = "direccionPedido", target = "direccion")
    PedidoConDireccionDTO toPedidoConDireccionDto(Pedido pedido);

    // --- toDto (Entity to DTO) ---
    // <--[Pedido pedido]--
    // ==>{PedidoDTO dto, y lo que ignora *-*}
    @Mapping(source = "estadoPedido", target = "estadoPedido")
    @Mapping(source = "sucursal", target = "sucursal")
    @Mapping(source = "tipoEnvio", target = "tipoEnvio")
    @Mapping(source = "tipoPago", target = "tipoPago")
    @Mapping(source = "usuario", target = "usuario")
    @Mapping(source = "detallePedidoList", target = "detallePedidoList")
    @Mapping(source = "detallePromocionList", target = "detallePromocionList")

    PedidoDTO toDto(Pedido pedido);

    // --- toEntity (DTO to Entity) ---
    // <--[PedidoDTO dto]--
    // ==>{Pedido entity, y lo que ignora
    // *usuario,detallePedidoList,detallePromocionList,direccionPedido*}
    @Mapping(source = "estadoPedido", target = "estadoPedido")
    @Mapping(source = "sucursal", target = "sucursal")
    @Mapping(source = "tipoEnvio", target = "tipoEnvio")
    @Mapping(source = "tipoPago", target = "tipoPago")

    @Mapping(target = "usuario", ignore = true)

    @Mapping(target = "detallePedidoList", ignore = true)
    @Mapping(target = "detallePromocionList", ignore = true)
    @Mapping(target = "direccionPedido", ignore = true)
    Pedido toEntity(PedidoDTO dto);

    // <--[PedidoConDireccionDTO dto]--
    // ==>{Pedido entity, y lo que ignora
    // *id,usuario,detallePedidoList,detallePromocionList,direccionPedido*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "detallePedidoList", ignore = true)
    @Mapping(target = "detallePromocionList", ignore = true)
    @Mapping(target = "direccionPedido", ignore = true)
    Pedido toEntity(PedidoConDireccionDTO dto);

    // --- updatePedidoFromDto (DTO to existing Entity) ---
    // <--[PedidoDTO dto, Pedido entity]--
    // ==>{void, y lo que ignora
    // *id,detallePedidoList,detallePromocionList,direccionPedido,usuario*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "detallePedidoList", ignore = true)
    @Mapping(target = "detallePromocionList", ignore = true)
    @Mapping(target = "direccionPedido", ignore = true)

    @Mapping(target = "usuario", ignore = true)

    @Mapping(source = "tiempoEstimado", target = "tiempoEstimado")
    @Mapping(source = "existe", target = "existe")
    @Mapping(source = "fecha", target = "fecha")
    @Mapping(source = "estadoPedido", target = "estadoPedido")
    @Mapping(source = "sucursal", target = "sucursal")
    @Mapping(source = "tipoEnvio", target = "tipoEnvio")
    @Mapping(source = "tipoPago", target = "tipoPago")
    void updatePedidoFromDto(PedidoDTO dto, @MappingTarget Pedido entity);

    // Updates an existing Pedido entity from a PedidoConDireccionDTO.
    // <--[PedidoConDireccionDTO dto, Pedido entity]--
    // ==>{void, y lo que ignora
    // *id,detallePedidoList,detallePromocionList,direccionPedido,usuario*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "detallePedidoList", ignore = true)
    @Mapping(target = "detallePromocionList", ignore = true)
    @Mapping(target = "direccionPedido", ignore = true)
    @Mapping(target = "usuario", ignore = true)

    @Mapping(source = "tiempoEstimado", target = "tiempoEstimado")
    @Mapping(source = "existe", target = "existe")
    @Mapping(source = "fecha", target = "fecha")
    @Mapping(source = "estadoPedido", target = "estadoPedido")
    @Mapping(source = "sucursal", target = "sucursal")
    @Mapping(source = "tipoEnvio", target = "tipoEnvio")
    @Mapping(source = "tipoPago", target = "tipoPago")
    void updatePedidoFromDto(PedidoConDireccionDTO dto, @MappingTarget Pedido entity);

    // <--[PedidoConDireccionDTO dto]--
    // ==>{PedidoDTO dto, y lo que ignora *-*}
    @Mapping(source = "estadoPedido", target = "estadoPedido")
    @Mapping(source = "sucursal", target = "sucursal")
    @Mapping(source = "tipoEnvio", target = "tipoEnvio")
    @Mapping(source = "tipoPago", target = "tipoPago")
    @Mapping(source = "usuario", target = "usuario")
    @Mapping(source = "detallePedidoList", target = "detallePedidoList")
    @Mapping(source = "detallePromocionList", target = "detallePromocionList")
    PedidoDTO toPedidoDtoFromConDireccionDto(PedidoConDireccionDTO dto);

    // <--[List<Pedido> pedidos]--
    // ==>{List<PedidoDTO> list, y lo que ignora *-*}
    List<PedidoDTO> toDtoList(List<Pedido> pedidos);

    // <--[List<Pedido> pedidos]--
    // ==>{List<PedidoConDireccionDTO> list, y lo que ignora *-*}
    public List<PedidoConDireccionDTO> toPedidoConDireccionDtoList(List<Pedido> pedidos);
}