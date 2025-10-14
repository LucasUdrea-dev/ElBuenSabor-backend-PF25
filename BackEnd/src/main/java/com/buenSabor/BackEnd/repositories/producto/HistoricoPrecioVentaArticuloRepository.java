/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.models.producto.HistoricoPrecioVentaArticulo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author oscarloha
 */
@Repository
public interface HistoricoPrecioVentaArticuloRepository extends 
        BeanRepository<HistoricoPrecioVentaArticulo,Long>{
    
    HistoricoPrecioVentaArticulo findTopByIdArticuloOrderByFechaDesc(Articulo articulo);
    
    List<HistoricoPrecioVentaArticulo> findByIdArticuloOrderByFechaDesc(Articulo articulo);
    
    List<HistoricoPrecioVentaArticulo> findByIdArticulo_IdOrderByFechaDesc(Long articuloId);
}
