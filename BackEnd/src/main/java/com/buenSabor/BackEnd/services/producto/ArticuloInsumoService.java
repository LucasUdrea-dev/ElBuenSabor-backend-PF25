/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.producto.ArticuloInsumoRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author oscarloha
 */
@Service
public class ArticuloInsumoService extends BeanServiceImpl<ArticuloInsumo,Long>{
    
    @Autowired
    private ArticuloInsumoRepository articuloInsumoRepository;

    public ArticuloInsumoService(BeanRepository<ArticuloInsumo, Long> beanRepository) {
        super(beanRepository);
    }
    
    @Transactional
    public List<ArticuloInsumo> obtenerInsumosParaElaborar() throws Exception{

        try {
            return articuloInsumoRepository.findByExisteTrueAndEsParaElaborarTrue();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

}
