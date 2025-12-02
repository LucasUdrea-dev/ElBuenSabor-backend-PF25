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
    @Mapping(source = "estadoPedido", target = "estadoPedido")
    @Mapping(source = "sucursal", target = "sucursal")
    @Mapping(source = "tipoEnvio", target = "tipoEnvio")
    @Mapping(source = "tipoPago", target = "tipoPago")
    @Mapping(source = "usuario", target = "usuario")
    @Mapping(source = "detallePedidoList", target = "detallePedidoList")
    @Mapping(source = "detallePromocionList", target = "detallePromocionList")
    @Mapping(source = "direccionPedido", target = "direccionPedido")
    PedidoConDireccionDTO toPedidoConDireccionDto(Pedido pedido);

    // --- toDto (Entity to DTO) ---
    @Mapping(source = "estadoPedido", target = "estadoPedido")
    @Mapping(source = "sucursal", target = "sucursal")
    @Mapping(source = "tipoEnvio", target = "tipoEnvio")
    @Mapping(source = "tipoPago", target = "tipoPago")
    @Mapping(source = "usuario", target = "usuario")
    @Mapping(source = "detallePedidoList", target = "detallePedidoList")
    @Mapping(source = "detallePromocionList", target = "detallePromocionList")
    PedidoDTO toDto(Pedido pedido);

    // --- toEntity (DTO to Entity) ---
    @Mapping(source = "estadoPedido", target = "estadoPedido")
    @Mapping(source = "sucursal", target = "sucursal")
    @Mapping(source = "tipoEnvio", target = "tipoEnvio")
    @Mapping(source = "tipoPago", target = "tipoPago")
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "detallePedidoList", ignore = true)
    @Mapping(target = "detallePromocionList", ignore = true)
    @Mapping(target = "direccionPedido", ignore = true)
    @Mapping(target = "historicoEstados", ignore = true) // <--- CORRECCIÓN AGREGADA
    Pedido toEntity(PedidoDTO dto);

    // --- toEntity (ConDireccionDTO to Entity) ---
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "detallePedidoList", ignore = true)
    @Mapping(target = "detallePromocionList", ignore = true)
    @Mapping(target = "direccionPedido", ignore = true)
    @Mapping(target = "historicoEstados", ignore = true) // <--- CORRECCIÓN AGREGADA
    Pedido toEntity(PedidoConDireccionDTO dto);

    // --- updatePedidoFromDto (DTO to existing Entity) ---
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "detallePedidoList", ignore = true)
    @Mapping(target = "detallePromocionList", ignore = true)
    @Mapping(target = "direccionPedido", ignore = true)
    @Mapping(target = "usuario", ignore = true) // Ya se ignora, pero lo dejo explícito
    @Mapping(source = "tiempoEstimado", target = "tiempoEstimado")
    @Mapping(source = "existe", target = "existe")
    @Mapping(source = "fecha", target = "fecha")
    // IGNORAMOS las relaciones maestras para evitar side-effects
    @Mapping(target = "estadoPedido", ignore = true)
    @Mapping(target = "sucursal", ignore = true)
    @Mapping(target = "tipoEnvio", ignore = true)
    @Mapping(target = "tipoPago", ignore = true)
    @Mapping(target = "historicoEstados", ignore = true) 
    void updatePedidoFromDto(PedidoDTO dto, @MappingTarget Pedido entity);

    // Updates an existing Pedido entity from a PedidoConDireccionDTO.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "detallePedidoList", ignore = true)
    @Mapping(target = "detallePromocionList", ignore = true)
    @Mapping(target = "direccionPedido", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(source = "tiempoEstimado", target = "tiempoEstimado")
    @Mapping(source = "existe", target = "existe")
    @Mapping(source = "fecha", target = "fecha")
    // IGNORAMOS las relaciones maestras para evitar side-effects
    @Mapping(target = "estadoPedido", ignore = true)
    @Mapping(target = "sucursal", ignore = true)
    @Mapping(target = "tipoEnvio", ignore = true)
    @Mapping(target = "tipoPago", ignore = true)
    @Mapping(target = "historicoEstados", ignore = true) 
    void updatePedidoFromDto(PedidoConDireccionDTO dto, @MappingTarget Pedido entity);

    // --- Helpers / Conversiones Extra ---
    @Mapping(source = "estadoPedido", target = "estadoPedido")
    @Mapping(source = "sucursal", target = "sucursal")
    @Mapping(source = "tipoEnvio", target = "tipoEnvio")
    @Mapping(source = "tipoPago", target = "tipoPago")
    @Mapping(source = "usuario", target = "usuario")
    @Mapping(source = "detallePedidoList", target = "detallePedidoList")
    @Mapping(source = "detallePromocionList", target = "detallePromocionList")
    PedidoDTO toPedidoDtoFromConDireccionDto(PedidoConDireccionDTO dto);

    List<PedidoDTO> toDtoList(List<Pedido> pedidos);

    List<PedidoConDireccionDTO> toPedidoConDireccionDtoList(List<Pedido> pedidos);
}