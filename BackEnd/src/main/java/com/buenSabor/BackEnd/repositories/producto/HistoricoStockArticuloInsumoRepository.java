/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.HistoricoStockArticuloInsumo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 *
 * @author oscarloha
 */
@Repository
public interface HistoricoStockArticuloInsumoRepository extends BeanRepository<HistoricoStockArticuloInsumo,Long>{
    List<HistoricoStockArticuloInsumo> findByStockArticuloInsumo_ArticuloInsumo_Id(Long id);
}
