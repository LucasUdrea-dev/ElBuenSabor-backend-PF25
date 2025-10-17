package com.buenSabor.BackEnd.repositories.ubicacion;

import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.models.ubicacion.Pais;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends BeanRepository<Pais, Long> {
    Optional<Pais> findByNombre(String nombre);

}
