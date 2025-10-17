package com.buenSabor.BackEnd.repositories.ubicacion;

import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.models.ubicacion.Ciudad;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface CiudadRepository extends BeanRepository<Ciudad, Long> {
    Optional<Ciudad> findByNombreAndProvinciaNombreAndProvincia_Pais_Nombre(String ciudad, String provincia,
            String pais);

}
