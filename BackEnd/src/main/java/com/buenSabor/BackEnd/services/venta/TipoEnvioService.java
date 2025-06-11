package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.models.venta.TipoEnvio;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TipoEnvioService extends BeanServiceImpl<TipoEnvio, Long> {
    public TipoEnvioService(BeanRepository<TipoEnvio, Long> beanRepository) {
        super(beanRepository);
    }
}
