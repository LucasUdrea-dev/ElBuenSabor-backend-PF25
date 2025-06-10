package com.buenSabor.BackEnd.services.user;

import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends BeanServiceImpl<Usuario, Long> {
    public UsuarioService(BeanRepository<Usuario, Long> beanRepository) {
        super(beanRepository);
    }
}
