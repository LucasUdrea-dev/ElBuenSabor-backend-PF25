package com.buenSabor.BackEnd.repositories.user;

import com.buenSabor.BackEnd.models.user.Telefono;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefonoRepository extends BeanRepository<Telefono, Long> {

}
