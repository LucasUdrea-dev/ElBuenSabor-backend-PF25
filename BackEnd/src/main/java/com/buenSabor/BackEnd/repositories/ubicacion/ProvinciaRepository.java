package com.buenSabor.BackEnd.repositories.ubicacion;

import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.models.ubicacion.Provincia;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepository extends BeanRepository<Provincia, Long> {

}
