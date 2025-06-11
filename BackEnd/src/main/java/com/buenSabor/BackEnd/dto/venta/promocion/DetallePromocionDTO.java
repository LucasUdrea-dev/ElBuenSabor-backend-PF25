package com.buenSabor.BackEnd.dto.venta.promocion;

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
public class DetallePromocionDTO {
    
    private Long id;
    private PedidoDTO pedido;
    private PromocionDTO promocion;
    private int cantidad;
    
}