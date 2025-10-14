package com.buenSabor.BackEnd.repositories.company;

import com.buenSabor.BackEnd.models.company.Empresa;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends BeanRepository<Empresa, Long> {

}
