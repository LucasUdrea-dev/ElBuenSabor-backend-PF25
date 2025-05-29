package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.models.venta.Promocion;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PromocionService extends BeanServiceImpl<Promocion, Long> {
    public PromocionService(BeanRepository<Promocion, Long> beanRepository) {
        super(beanRepository);
    }
}
