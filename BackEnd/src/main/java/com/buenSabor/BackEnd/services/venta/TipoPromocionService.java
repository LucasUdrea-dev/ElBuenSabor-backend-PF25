package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.models.venta.TipoPromocion;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TipoPromocionService extends BeanServiceImpl<TipoPromocion, Long> {
    public TipoPromocionService(BeanRepository<TipoPromocion, Long> beanRepository) {
        super(beanRepository);
    }
}
