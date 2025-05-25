/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.ubicacion;

import com.buenSabor.BackEnd.models.ubicacion.Departamento;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

/**
 *
 * @author oscarloha
 */
@Service
public class DepartamentoService extends BeanServiceImpl<Departamento,Long>{
    
    public DepartamentoService(BeanRepository<Departamento, Long> beanRepository) {
        super(beanRepository);
    }
    
}
