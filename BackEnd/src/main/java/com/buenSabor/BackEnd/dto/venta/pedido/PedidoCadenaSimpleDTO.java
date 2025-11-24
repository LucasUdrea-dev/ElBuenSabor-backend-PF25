/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.pedido;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.venta.detallepedido.DetallePedidoCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.venta.detallepromocion.DetallePromocionCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.venta.envio.TipoEnvioDTO;
import com.buenSabor.BackEnd.dto.venta.estado.EstadoPedidoDTO;
import com.buenSabor.BackEnd.dto.venta.pago.TipoPagoDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author oscarloha
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoCadenaSimpleDTO {
    
    private Long id;
    
    private String tiempoEstimado;
  
    private Boolean existe;

    private Date fecha;

    private List<DetallePedidoCadenaSimpleDTO> detallePedidoList = new ArrayList<>();

    private EstadoPedidoDTO estadoPedido;

    private SucursalCadenaSimpleDTO sucursal;

    private TipoEnvioDTO tipoEnvio;

    private TipoPagoDTO tipoPago;

    private UsuarioCadenaSimpleDTO usuario;

    private List<DetallePromocionCadenaSimpleDTO> detallePromocionList = new ArrayList<>();

    private DireccionCadenaSimpleDTO direccionPedido;
    
    private Double total;
    
}
