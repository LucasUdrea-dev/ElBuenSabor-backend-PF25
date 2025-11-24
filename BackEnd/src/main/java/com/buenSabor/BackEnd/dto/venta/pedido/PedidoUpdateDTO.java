/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.pedido;

import com.buenSabor.BackEnd.dto.venta.detallepedido.DetallePedidoUpdateDTO;
import com.buenSabor.BackEnd.dto.venta.detallepromocion.DetallePromocionUpdateDTO;
import com.buenSabor.BackEnd.dto.venta.envio.TipoEnvioDTO;
import com.buenSabor.BackEnd.dto.venta.pago.TipoPagoDTO;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.venta.EstadoPedido;
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
public class PedidoUpdateDTO {
    
    private String tiempoEstimado;
  
    private Boolean existe;

    private Date fecha;

    private List<DetallePedidoUpdateDTO> detallePedidoList = new ArrayList<>();

    private EstadoPedido estadoPedido;

    private Long sucursalId;

    private TipoEnvioDTO tipoEnvio;

    private TipoPagoDTO tipoPago;

    private Long usuarioId;

    private List<DetallePromocionUpdateDTO> detallePromocionList = new ArrayList<>();

    private Long direccionPedidoId;
    
}
