package com.buenSabor.BackEnd.services.seguridad;

import com.buenSabor.BackEnd.models.seguridad.Rol;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RolService extends BeanServiceImpl<Rol, Long> {
    public RolService(BeanRepository<Rol, Long> beanRepository) {
        super(beanRepository);
    }
}
