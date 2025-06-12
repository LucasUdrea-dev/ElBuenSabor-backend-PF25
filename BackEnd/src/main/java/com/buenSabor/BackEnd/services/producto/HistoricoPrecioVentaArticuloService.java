/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.models.producto.HistoricoPrecioVentaArticulo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

/**
 *
 * @author oscarloha
 */
@Service
public class HistoricoPrecioVentaArticuloService extends 
        BeanServiceImpl<HistoricoPrecioVentaArticulo,Long> {
    
    public HistoricoPrecioVentaArticuloService(BeanRepository<HistoricoPrecioVentaArticulo, Long> beanRepository) {
        super(beanRepository);
    }
    
}
