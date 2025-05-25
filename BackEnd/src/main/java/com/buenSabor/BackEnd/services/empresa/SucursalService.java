/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.empresa;

import com.buenSabor.BackEnd.models.empresa.Sucursal;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

/**
 *
 * @author oscarloha
 */
@Service
public class SucursalService extends BeanServiceImpl<Sucursal,Long>{
    
    public SucursalService(BeanRepository<Sucursal, Long> beanRepository) {
        super(beanRepository);
    }
    
}
