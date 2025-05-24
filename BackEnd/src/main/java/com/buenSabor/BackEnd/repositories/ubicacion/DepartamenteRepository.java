/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.ubicacion;

import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.models.ubicacion.Departamento;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oscarloha
 */
@Repository
public interface DepartamenteRepository extends BeanRepository<Departamento,Long>{
    
}
