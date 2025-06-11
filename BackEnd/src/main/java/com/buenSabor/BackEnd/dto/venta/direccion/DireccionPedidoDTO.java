package com.buenSabor.BackEnd.dto.venta.direccion;

import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionDTO;
import com.buenSabor.BackEnd.dto.venta.pedido.PedidoDTO;
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
@AllArgsConstructor
@NoArgsConstructor
public class DireccionPedidoDTO {

    private Long id;
    private PedidoDTO pedido;
    private DireccionDTO direccion;

}
