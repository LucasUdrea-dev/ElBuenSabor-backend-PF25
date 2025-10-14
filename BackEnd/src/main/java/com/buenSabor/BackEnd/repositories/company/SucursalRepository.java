package com.buenSabor.BackEnd.repositories.company;

import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends BeanRepository<Sucursal, Long> {

    public List<Sucursal> findByExisteIsTrue();

}
