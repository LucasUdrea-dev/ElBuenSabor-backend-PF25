package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.models.venta.DetallePromocion;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePedidoPromocionRepository extends BeanRepository<DetallePromocion, Long> {

}
