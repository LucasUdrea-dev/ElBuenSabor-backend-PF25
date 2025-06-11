/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.pedido;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalDTO;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author oscarloha
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private Long id; // Assuming 'Bean' provides the ID
    private Date tiempoEstimado;
    private Boolean existe;
    private Date fecha;

    // Use DTOs for nested entities
    private List<DetallePedidoDTO> detallePedidoList;

    private EstadoPedidoDTO estadoPedido;
    private SucursalDTO sucursal;
    private TipoEnvioDTO tipoEnvio;
    private TipoPagoDTO tipoPago;
    private UsuarioDTO usuario; // Renamed from 'cliente' for consistency with 'Usuario' entity

    // For collections that might cause recursion or are heavily nested,
    // consider if they are truly needed in the main PedidoDTO or if
    // they should be fetched via separate endpoints.
    // For now, including them with their respective DTOs.
    private List<DetallePromocionDTO> detallePromocionList;

    // Assuming DireccionPedido has its own DTO
    private DireccionPedidoDTO direccionPedido;
}
