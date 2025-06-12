/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.UnidadMedida;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.enums.Measument;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 *
 * @author oscarloha
 */
@Repository
public interface UnidadMedidaRepository extends BeanRepository<UnidadMedida,Long>{

    public Object findById(int i);
    
    Optional<UnidadMedida> findByUnidad(Measument unidad);
}
