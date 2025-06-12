package com.buenSabor.BackEnd.services.user;

import com.buenSabor.BackEnd.models.user.Telefono;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TelefonoService extends BeanServiceImpl<Telefono,Long> {
    public TelefonoService(BeanRepository<Telefono, Long> beanRepository) {
        super(beanRepository);
    }
}
