package com.buenSabor.BackEnd.dto.venta.pedido;

import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DireccionPedidoDTO {
    private Long id;
    private DireccionDTO direccion;
}
