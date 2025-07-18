/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.pedido;

import com.buenSabor.BackEnd.enums.TypeState;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author oscarloha
 */
@Data
public class PedidoEstadoNotificacionesDTO {
    private Long pedidoId;  
    private TypeState estadoAnterior;  
    private TypeState estadoNuevo;  
    private String tiempoEstimado;  
    private Long usuarioId;  
    private Date timestamp;  
}
