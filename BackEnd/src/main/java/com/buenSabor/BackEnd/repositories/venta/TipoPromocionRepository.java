package com.buenSabor.BackEnd.repositories.venta;

import com.buenSabor.BackEnd.models.venta.TipoPromocion;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPromocionRepository extends BeanRepository<TipoPromocion, Long> {

    public Object findById(int i);

}
