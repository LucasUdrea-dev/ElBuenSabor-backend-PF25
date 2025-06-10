package com.buenSabor.BackEnd.services.seguridad;

import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService extends BeanServiceImpl<UserAuthentication, Long> {
    public UserAuthenticationService(BeanRepository<UserAuthentication, Long> beanRepository) {
        super(beanRepository);
    }
}
