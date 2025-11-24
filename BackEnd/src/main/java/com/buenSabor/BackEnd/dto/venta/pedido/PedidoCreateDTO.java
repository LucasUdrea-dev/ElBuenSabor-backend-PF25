/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.pedido;

import com.buenSabor.BackEnd.dto.venta.detallepedido.DetallePedidoCreateDTO;
import com.buenSabor.BackEnd.dto.venta.detallepromocion.DetallePromocionCreateDTO;
import com.buenSabor.BackEnd.dto.venta.envio.TipoEnvioDTO;
import com.buenSabor.BackEnd.dto.venta.estado.EstadoPedidoDTO;
import com.buenSabor.BackEnd.dto.venta.pago.TipoPagoDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class PedidoCreateDTO {
    
    @NotBlank(message = "El id del pedido es obligatorio")
    private String tiempoEstimado;
    
    @NotNull(message = "La existencia es obligatoria")
    private Boolean existe;
    
    @NotNull(message = "La fecha es obligatoria")
    private Date fecha;
    
    @NotNull(message = "Un detalle pedido es obligatorio")
    private List<DetallePedidoCreateDTO> detallePedidoList = new ArrayList<>();
    
    @NotNull(message = "El estado pedido es obligatorio")
    private EstadoPedidoDTO estadoPedido;
    
    @NotNull(message = "El id de la sucursal es obligatorio")
    private Long sucursalId;
    
    @NotNull(message = "El tipo de envio es obligatorio")
    private TipoEnvioDTO tipoEnvio;
    
    @NotNull(message = "El tipo de pago es obligatorio")
    private TipoPagoDTO tipoPago;
    
    @NotNull(message = "El id del usuario es obligatorio")
    private Long usuarioId;
    
    @NotNull(message = "Un detalle de al promocion es oblogatorio")
    private List<DetallePromocionCreateDTO> detallePromocionList = new ArrayList<>();
    
    @NotNull(message = "El id de la direccion es obligatorio")
    private Long direccionPedidoId;

}
