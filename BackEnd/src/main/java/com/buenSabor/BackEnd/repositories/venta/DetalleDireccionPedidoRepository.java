package com.buenSabor.BackEnd.repositories.venta;

import com.buenSabor.BackEnd.models.venta.DireccionPedido;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleDireccionPedidoRepository extends BeanRepository<DireccionPedido, Long> {

}
