/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.models.producto.HistoricoStockArticuloInsumo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.producto.HistoricoStockArticuloInsumoRepository;
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
public class HistoricoStockArticuloInsumoService extends BeanServiceImpl<HistoricoStockArticuloInsumo,Long>{
    
    @Autowired
    private HistoricoStockArticuloInsumoRepository historicoStockArticuloInsumoRepository;

    public HistoricoStockArticuloInsumoService
        (BeanRepository<HistoricoStockArticuloInsumo, Long> beanRepository) {
        super(beanRepository);
    }

    @Transactional
    public List<HistoricoStockArticuloInsumo> buscarHistorialPorArticulo(Long id) throws Exception{

        try {
            return historicoStockArticuloInsumoRepository.findByStockArticuloInsumo_ArticuloInsumo_Id(id);
        } catch (Exception e) {
            throw new Exception("Error al buscar historicos de stock por articulo: " + e.getMessage());
        }

    }
    
}
