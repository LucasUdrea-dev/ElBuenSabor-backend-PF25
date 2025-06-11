/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

/**
 *
 * @author oscarloha
 */

import com.buenSabor.BackEnd.dto.venta.pedido.PedidoDTO;
import com.buenSabor.BackEnd.models.venta.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;


@Mapper(componentModel = "spring",
        uses = {
            EstadoPedidoMapper.class,
            SucursalMapper.class,
            TipoEnvioMapper.class,
            TipoPagoMapper.class,
            UsuarioMapper.class, // Keep UsuarioMapper if it exists and is intended to map UsuarioDTO to Usuario
            DetallePedidoMapper.class,
            DetallePromocionMapper.class,
            // If you have DTOs for these, ensure their mappers are here:
            // DireccionMapper.class,
            // CiudadMapper.class,
            // ProvinciaMapper.class,
            // PaisMapper.class,
            // TelefonoMapper.class,
            // RolMapper.class,
            // TipoRolMapper.class,
            // UserAuthenticationMapper.class
        },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PedidoMapper {

    // --- toDto (Entity to DTO) ---
    @Mapping(source = "estadoPedido", target = "estadoPedido")
    @Mapping(source = "sucursal", target = "sucursal")
    @Mapping(source = "tipoEnvio", target = "tipoEnvio")
    @Mapping(source = "tipoPago", target = "tipoPago")
    @Mapping(source = "usuario", target = "usuario")
    @Mapping(source = "detallePedidoList", target = "detallePedidoList")
    @Mapping(source = "detallePromocionList", target = "detallePromocionList")
    // Assuming DireccionPedido entity and DTO exist and are part of Pedido
//    @Mapping(source = "direccionPedido", target = "direccionPedido") // Uncomment if Pedido has DireccionPedido
    PedidoDTO toDto(Pedido pedido);

    // --- toEntity (DTO to Entity) ---
    @Mapping(source = "estadoPedido", target = "estadoPedido")
    @Mapping(source = "sucursal", target = "sucursal")
    @Mapping(source = "tipoEnvio", target = "tipoEnvio")
    @Mapping(source = "tipoPago", target = "tipoPago")

    
    @Mapping(target = "usuario", ignore = true)

    @Mapping(target = "detallePedidoList", ignore = true)
    @Mapping(target = "detallePromocionList", ignore = true)
    @Mapping(target = "direccionPedido", ignore = true) // Ignore if service handles DireccionPedido
    Pedido toEntity(PedidoDTO dto);


    // --- updatePedidoFromDto (DTO to existing Entity) ---
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "detallePedidoList", ignore = true)
    @Mapping(target = "detallePromocionList", ignore = true)
    @Mapping(target = "direccionPedido", ignore = true)

    // Also ignore Usuario here, as it's typically set once and not directly updated
    // via a Pedido update DTO, especially not its sub-collections.
    @Mapping(target = "usuario", ignore = true) // <--- CRITICAL FIX: Ignore Usuario here too

    @Mapping(source = "tiempoEstimado", target = "tiempoEstimado")
    @Mapping(source = "existe", target = "existe")
    @Mapping(source = "fecha", target = "fecha")
    @Mapping(source = "estadoPedido", target = "estadoPedido")
    @Mapping(source = "sucursal", target = "sucursal")
    @Mapping(source = "tipoEnvio", target = "tipoEnvio")
    @Mapping(source = "tipoPago", target = "tipoPago")
    void updatePedidoFromDto(PedidoDTO dto, @MappingTarget Pedido entity);


    List<PedidoDTO> toDtoList(List<Pedido> pedidos);
}