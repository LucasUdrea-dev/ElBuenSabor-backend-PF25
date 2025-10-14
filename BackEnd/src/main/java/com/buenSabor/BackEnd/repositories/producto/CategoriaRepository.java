/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.Categoria;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author oscarloha
 */
@Repository
public interface CategoriaRepository extends BeanRepository<Categoria,Long>{

    List<Categoria> findByDenominacionContainingIgnoreCase(String nombre);

    //Filtra si es categoria para elaborar
    List<Categoria> findCategoriaByEsParaElaborarTrue();

    List<Categoria> findByEsParaElaborarFalse();

    //Busca si categoria existe
    //List<Categoria> findByExisteTrue();

}
