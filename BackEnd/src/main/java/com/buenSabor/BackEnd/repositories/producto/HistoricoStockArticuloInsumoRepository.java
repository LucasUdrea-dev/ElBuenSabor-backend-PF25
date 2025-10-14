/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.HistoricoStockArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author oscarloha
 */
@Repository
public interface HistoricoStockArticuloInsumoRepository extends 
        BeanRepository<HistoricoStockArticuloInsumo,Long>{
    
    HistoricoStockArticuloInsumo findTopByIdstockarticuloInsumoOrderByFechaActualizacionDesc(StockArticuloInsumo stock);
    
    List<HistoricoStockArticuloInsumo> findByIdstockarticuloInsumoOrderByFechaActualizacionDesc(StockArticuloInsumo stock);
    
    List<HistoricoStockArticuloInsumo> findByIdstockarticuloInsumo_IdOrderByFechaActualizacionDesc(Long stockId);
}
