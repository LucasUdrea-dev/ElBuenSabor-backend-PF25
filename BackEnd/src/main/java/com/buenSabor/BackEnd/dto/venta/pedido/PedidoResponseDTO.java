/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.pedido;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalResponseDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioResponseDTO;
import com.buenSabor.BackEnd.dto.venta.envio.TipoEnvioResponseDTO;
import com.buenSabor.BackEnd.dto.venta.estado.EstadoPedidoResponseDTO;
import com.buenSabor.BackEnd.dto.venta.pago.TipoPagoResponseDTO;
import com.buenSabor.BackEnd.dto.venta.pedidodetalle.PedidoDetalleResponseDTO;
import com.buenSabor.BackEnd.dto.venta.promocion.PromoResponseDTO;
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
public class PedidoResponseDTO {
    private Long id;
    
     

    private Date tiempoEstimado;
    private Boolean existe;
    private Date fecha;
    
    
    private List<PedidoDetalleResponseDTO> detalles;
    private EstadoPedidoResponseDTO estadoPedido;
    private SucursalResponseDTO sucursal;
    private TipoEnvioResponseDTO tipoEnvio;
    private TipoPagoResponseDTO tipoPago;
    private UsuarioResponseDTO usuario;
    private PromoResponseDTO promocion;

}
