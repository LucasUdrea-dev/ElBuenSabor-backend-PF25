package com.buenSabor.BackEnd.dto.venta.estado;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoPedidoDTO {
    private Long id;
    private String nombreEstado;
}
