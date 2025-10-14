package com.buenSabor.BackEnd.repositories.venta;

import com.buenSabor.BackEnd.models.venta.TipoEnvio;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoEnvioRepository extends BeanRepository<TipoEnvio, Long> {

    public boolean findById(int i);

}
