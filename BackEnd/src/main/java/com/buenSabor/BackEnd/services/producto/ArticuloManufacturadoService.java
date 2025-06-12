/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.models.producto.ArticuloManufacturado;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturadoDetalleInsumo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.producto.ArticuloManufacturadoRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author oscarloha
 */
@Service
public class ArticuloManufacturadoService extends BeanServiceImpl<ArticuloManufacturado,Long>{
    
    @Autowired
    private ArticuloManufacturadoRepository articuloManufacturadoRepository;

    public ArticuloManufacturadoService(BeanRepository<ArticuloManufacturado, Long> beanRepository) {
        super(beanRepository);
    }
    
    @Override
    @Transactional
    public ArticuloManufacturado save(ArticuloManufacturado articuloManufacturado) throws Exception{

        try {
            
            if (articuloManufacturado.getDetalleInsumos() != null) {
                
                for (ArticuloManufacturadoDetalleInsumo detalleInsumo : articuloManufacturado.getDetalleInsumos()) {
                    
                    detalleInsumo.setArticuloManufacturado(articuloManufacturado);

                }

            }

            return articuloManufacturadoRepository.save(articuloManufacturado);

        } catch (Exception e) {
            System.err.println("Error al guardar Articulo Manufacturado: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Error al guardar el Articulo Manufacturado: " + e.getMessage(), e);
        }

    }

    @Override
    @Transactional
    public ArticuloManufacturado update(Long id, ArticuloManufacturado articuloManufacturado) throws Exception{

        articuloManufacturado.setId(id);

        try {
            
            if (articuloManufacturado.getDetalleInsumos() != null) {
                
                for (ArticuloManufacturadoDetalleInsumo detalleInsumo : articuloManufacturado.getDetalleInsumos()) {
                    
                    detalleInsumo.setArticuloManufacturado(articuloManufacturado);

                }

            }

            return articuloManufacturadoRepository.save(articuloManufacturado);

        } catch (Exception e) {
            System.err.println("Error al actualizar Articulo Manufacturado: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Error al actualizar el Articulo Manufacturado: " + e.getMessage(), e);
        }

    }

}
