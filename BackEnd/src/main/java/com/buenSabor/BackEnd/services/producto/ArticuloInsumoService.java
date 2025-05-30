/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.producto;


import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.company.SucursalRepository;
import com.buenSabor.BackEnd.repositories.producto.ArticuloInsumoRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author oscarloha
 */
@Service
public class ArticuloInsumoService extends BeanServiceImpl<ArticuloInsumo,Long>{

    public ArticuloInsumoService(BeanRepository<ArticuloInsumo, Long> beanRepository) {
        super(beanRepository);
    }

    @Autowired
    SucursalRepository sucursalRepository;
    @Autowired
    ArticuloInsumoRepository insumoRepository;
    
    public ArticuloInsumo crearInsumo(ArticuloInsumo insumo,Long id) {

        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> {
                    return new RuntimeException("Sucursal no encontrada con id: " + id);
                });
        
        insumo.getStockArticuloInsumo().setSucursal(sucursal);

        return insumoRepository.save(insumo);
    }
    
}