package com.buenSabor.BackEnd.repositories.venta;

import com.buenSabor.BackEnd.models.venta.PromocionArticulo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePromocionArticuloRepository extends BeanRepository<PromocionArticulo, Long> {

}
