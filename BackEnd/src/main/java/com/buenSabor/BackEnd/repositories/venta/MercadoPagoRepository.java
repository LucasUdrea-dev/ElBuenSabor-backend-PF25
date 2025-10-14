package com.buenSabor.BackEnd.repositories.venta;

import com.buenSabor.BackEnd.models.venta.MercadoPago;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MercadoPagoRepository extends BeanRepository<MercadoPago, Long> {

}
