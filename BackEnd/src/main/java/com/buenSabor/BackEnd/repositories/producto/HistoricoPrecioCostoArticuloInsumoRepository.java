/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.HistoricoPrecioCostoArticuloInsumo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author oscarloha
 */
@Repository
public interface HistoricoPrecioCostoArticuloInsumoRepository extends 
        BeanRepository<HistoricoPrecioCostoArticuloInsumo,Long>{
    
    HistoricoPrecioCostoArticuloInsumo findTopByIdArticuloInsumoOrderByFechaDesc(ArticuloInsumo articuloInsumo);
    
    List<HistoricoPrecioCostoArticuloInsumo> findByIdArticuloInsumoOrderByFechaDesc(ArticuloInsumo articuloInsumo);
    
    List<HistoricoPrecioCostoArticuloInsumo> findByIdArticuloInsumo_IdOrderByFechaDesc(Long articuloInsumoId);
}
