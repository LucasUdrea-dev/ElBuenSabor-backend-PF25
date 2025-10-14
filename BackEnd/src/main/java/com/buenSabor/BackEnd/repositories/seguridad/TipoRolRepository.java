package com.buenSabor.BackEnd.repositories.seguridad;

import com.buenSabor.BackEnd.enums.TypeRol;
import com.buenSabor.BackEnd.models.seguridad.TipoRol;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoRolRepository extends BeanRepository<TipoRol, Long> {

    public Object findById(int i);

    public Optional<TipoRol> findByRol(TypeRol rol);
}
