package com.buenSabor.BackEnd.services.user;

import com.buenSabor.BackEnd.models.user.Empleado;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService extends BeanServiceImpl<Empleado, Long> {
    public EmpleadoService(BeanRepository<Empleado, Long> beanRepository) {
        super(beanRepository);
    }
}
