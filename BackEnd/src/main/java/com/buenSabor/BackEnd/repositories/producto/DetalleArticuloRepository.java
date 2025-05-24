/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.DetalleArticulo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oscarloha
 */
@Repository
public interface DetalleArticuloRepository extends BeanRepository<DetalleArticulo,Long>{
    
}
