package com.buenSabor.BackEnd.repositories.user;

import com.buenSabor.BackEnd.models.user.UsuarioDireccion;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDireccionRepository extends BeanRepository<UsuarioDireccion, Long> {
}
