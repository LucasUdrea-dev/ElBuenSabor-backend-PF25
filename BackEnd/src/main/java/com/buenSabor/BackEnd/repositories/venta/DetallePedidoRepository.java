package com.buenSabor.BackEnd.repositories.venta;

import com.buenSabor.BackEnd.models.venta.DetallePedido;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePedidoRepository extends BeanRepository<DetallePedido, Long> {
}
