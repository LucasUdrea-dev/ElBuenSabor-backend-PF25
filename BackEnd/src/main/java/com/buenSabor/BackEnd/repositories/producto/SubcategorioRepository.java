/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.Categoria;
import com.buenSabor.BackEnd.models.producto.Subcategoria;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author oscarloha
 */
@Repository
public interface SubcategorioRepository extends BeanRepository<Subcategoria,Long>{

    List<Subcategoria> findByDenominacionContainingIgnoreCase(String nombre);
    @Query("select sc from Subcategoria sc " +
            "Join sc.categoria c " +
            "where lower(c.denominacion) LIKE lower(concat('%',:texto,'%')) ")
    List<Subcategoria> filtrarPorCategoria(@Param("texto") String nombre);


}
