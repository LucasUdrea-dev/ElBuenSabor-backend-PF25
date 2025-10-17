package com.buenSabor.BackEnd.dto.venta.detallepedido;

import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedidoDTO {

    private Long id;

    private ArticuloDTO articulo;

    private Integer cantidad;
}
