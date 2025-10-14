package com.buenSabor.BackEnd.repositories.venta;

import com.buenSabor.BackEnd.models.venta.Promocion;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocionRepository extends BeanRepository<Promocion, Long> {

    public List<Promocion> findByDenominacionContainingIgnoreCase(String denominacion);

    List<Promocion> findByExisteTrue();

    List<Promocion> findByExisteTrueAndSucursalId(Long sucursalId);
}
