package com.buenSabor.BackEnd.repositories.venta;

import com.buenSabor.BackEnd.models.venta.TipoPago;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPagoRepository extends BeanRepository<TipoPago, Long> {

    public Object findById(int i);

}
