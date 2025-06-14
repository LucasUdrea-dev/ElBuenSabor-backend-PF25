/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.company;

import com.buenSabor.BackEnd.models.company.Empresa;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.company.EmpresaRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author oscarloha
 */
@Service
public class EmpresaService extends BeanServiceImpl<Empresa, Long> {

    @Autowired
    EmpresaRepository empresaRepository;

    public EmpresaService(BeanRepository<Empresa, Long> beanRepository) {
        super(beanRepository);
    }
//    
//    Page<Empresa> findAllPaged(Pageable pageable);

 
}
