/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.ubicacion;

import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.models.ubicacion.Casa;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oscarloha
 */

@Repository
public interface CasaRepository extends BeanRepository<Casa,Long>{
    
}
