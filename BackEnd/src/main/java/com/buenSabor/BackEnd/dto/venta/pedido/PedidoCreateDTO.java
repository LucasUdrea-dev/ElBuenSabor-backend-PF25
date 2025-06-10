/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.pedido;

import com.buenSabor.BackEnd.dto.venta.envio.TipoEnvioCreateDTO;
import com.buenSabor.BackEnd.dto.venta.estado.EstadoPedidoCreateDTO;
import com.buenSabor.BackEnd.dto.venta.pago.TipoPagoCreateDTO;
import com.buenSabor.BackEnd.dto.venta.pedidodetalle.PedidoDetalleCreateDTO;
import com.buenSabor.BackEnd.models.venta.Promocion;
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
@NoArgsConstructor  
@AllArgsConstructor 
public class PedidoCreateDTO {
    
    
    

    private Date tiempoEstimado;
    private Boolean existe;
    private Date fecha;
    
    
    private List<PedidoDetalleCreateDTO> detalles;
    private EstadoPedidoCreateDTO estadoPedido;
    private Long sucursalId;
    private TipoEnvioCreateDTO tipoEnvio;
    private TipoPagoCreateDTO tipoPago;
    private Long usuarioId;
    private Long promocionId;
}
