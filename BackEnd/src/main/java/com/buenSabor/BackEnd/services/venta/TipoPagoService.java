package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.models.venta.TipoPago;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TipoPagoService extends BeanServiceImpl<TipoPago, Long> {
    public TipoPagoService(BeanRepository<TipoPago, Long> beanRepository) {
        super(beanRepository);
    }
}
