package com.buenSabor.BackEnd.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoNotificacion {
    
    private Long pedidoId;
    private Long estadoId;
    private String estadoNombre;
    private String tiempoEstimado;
    private Date fecha;
    private Long usuarioId;
    private String usuarioNombre;
    private Long sucursalId;
    private String mensaje;
    private Date timestamp;
}
