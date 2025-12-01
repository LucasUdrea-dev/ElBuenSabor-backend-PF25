package com.buenSabor.BackEnd.dto.estadisticas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoEstadoPedidoDTO {
    private Long id;
    private Date fechaCambio;
    private Long pedidoId;
    private String nombreEstadoPedido;
    private String observaciones;
}
