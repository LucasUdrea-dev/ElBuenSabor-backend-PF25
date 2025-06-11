package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.models.venta.MercadoPago;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MercadoPagoService extends BeanServiceImpl<MercadoPago, Long> {
    public MercadoPagoService(BeanRepository<MercadoPago, Long> beanRepository) {
        super(beanRepository);
    }
}
