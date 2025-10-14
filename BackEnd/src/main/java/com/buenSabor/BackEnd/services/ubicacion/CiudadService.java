package com.buenSabor.BackEnd.services.ubicacion;

import com.buenSabor.BackEnd.models.ubicacion.Ciudad;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CiudadService extends BeanServiceImpl<Ciudad, Long> {

    public CiudadService(BeanRepository<Ciudad, Long> beanRepository) {
        super(beanRepository);
    }

}
