package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.models.producto.UnidadMedida;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UnidadMedidaService extends BeanServiceImpl<UnidadMedida, Long> {

    public UnidadMedidaService(BeanRepository<UnidadMedida, Long> beanRepository) {
        super(beanRepository);
    }

}
