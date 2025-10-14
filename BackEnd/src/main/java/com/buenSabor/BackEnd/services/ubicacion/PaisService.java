package com.buenSabor.BackEnd.services.ubicacion;

import com.buenSabor.BackEnd.models.ubicacion.Pais;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PaisService extends BeanServiceImpl<Pais, Long> {

    public PaisService(BeanRepository<Pais, Long> beanRepository) {
        super(beanRepository);
    }

}
