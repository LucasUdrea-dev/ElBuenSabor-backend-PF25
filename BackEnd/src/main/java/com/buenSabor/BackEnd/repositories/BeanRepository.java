package com.buenSabor.BackEnd.repositories;


import com.buenSabor.BackEnd.models.bean.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean //Esta interface no puede ser instanciada
public interface BeanRepository <E extends Bean, ID extends Serializable> extends JpaRepository<E,ID> {


}
