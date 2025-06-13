/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.pedido;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import com.buenSabor.BackEnd.dto.venta.detallepedido.DetallePedidoDTO;
import com.buenSabor.BackEnd.dto.venta.detallepromocion.DetallePromocionDTO;
import com.buenSabor.BackEnd.dto.venta.envio.TipoEnvioDTO;
import com.buenSabor.BackEnd.dto.venta.estado.EstadoPedidoDTO;
import com.buenSabor.BackEnd.dto.venta.pago.TipoPagoDTO;
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
    private String tiempoEstimado;
    private Boolean existe;
    private Date fecha;

    // Use DTOs for nested entities
    private List<DetallePedidoDTO> detallePedidoList;

    private EstadoPedidoDTO estadoPedido;
    private SucursalDTO sucursal;
    private TipoEnvioDTO tipoEnvio;
    private TipoPagoDTO tipoPago;
    private UsuarioDTO usuario; 

   
    private List<DetallePromocionDTO> detallePromocionList;

   
   
}
