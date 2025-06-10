/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturado;
import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.company.SucursalRepository;
import com.buenSabor.BackEnd.repositories.producto.ArticuloManufacturadoRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author oscarloha
 */
@Service
public class ArticuloManufacturadoService extends BeanServiceImpl<ArticuloManufacturado,Long>{
    
    public ArticuloManufacturadoService(BeanRepository<ArticuloManufacturado, Long> beanRepository) {
        super(beanRepository);
    }
    
    @Autowired
    ArticuloManufacturadoRepository manufacturadoRepository;
    @Autowired
    SucursalRepository sucursalRepository;
    
    
    
    public ArticuloManufacturado crearManufacturado(ArticuloManufacturado manufacturado,Long id) {

        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> {
                    return new RuntimeException("Sucursal no encontrada con id: " + id);
                });
        
        manufacturado.setSucursal(sucursal);

        return manufacturadoRepository.save(manufacturado);
    }


    
}
