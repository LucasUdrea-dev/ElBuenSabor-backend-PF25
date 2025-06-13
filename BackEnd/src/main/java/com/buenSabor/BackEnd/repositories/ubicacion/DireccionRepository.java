/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.ubicacion;

import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oscarloha
 * swagger
 */

@Repository
public interface DireccionRepository extends BeanRepository<Direccion,Long>{

    
}
