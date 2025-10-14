package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.UnidadMedida;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.enums.Measument;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UnidadMedidaRepository extends BeanRepository<UnidadMedida, Long> {

    public Object findById(int i);

    Optional<UnidadMedida> findByUnidad(Measument unidad);
}
