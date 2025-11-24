/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.pedido;

import com.buenSabor.BackEnd.dto.venta.envio.TipoEnvioDTO;
import com.buenSabor.BackEnd.dto.venta.estado.EstadoPedidoDTO;
import com.buenSabor.BackEnd.dto.venta.pago.TipoPagoDTO;
import java.util.Date;
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
public class PedidoResponseDTO {
    
    private Long id;
    
    private String tiempoEstimado;
  
    private Boolean existe;

    private Date fecha;

    private EstadoPedidoDTO estadoPedido;

    private Long sucursalId;

    private TipoEnvioDTO tipoEnvio;

    private TipoPagoDTO tipoPago;

    private Long usuarioId;

    private Double total;
    
}
