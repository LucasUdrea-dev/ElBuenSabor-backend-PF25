package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.models.venta.PromocionArticulo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PromocionArticuloService extends BeanServiceImpl<PromocionArticulo, Long> {
    public PromocionArticuloService(BeanRepository<PromocionArticulo, Long> beanRepository) {
        super(beanRepository);
    }
}
