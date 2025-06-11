package com.buenSabor.BackEnd.services.seguridad;

import com.buenSabor.BackEnd.models.seguridad.TipoRol;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TipoRolService extends BeanServiceImpl<TipoRol, Long> {
    public TipoRolService(BeanRepository<TipoRol, Long> beanRepository) {
        super(beanRepository);
    }
}
