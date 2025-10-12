/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.ArticuloManufacturado;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author oscarloha
 */
@Repository
public interface ArticuloManufacturadoRepository extends BeanRepository<ArticuloManufacturado, Long> {
    // Buscar insumos por nombre
    List<ArticuloManufacturado> findByNombreContainingIgnoreCase(String nombre);

    // Buscar por subcategoría
    List<ArticuloManufacturado> findBySubcategoria_Id(Long subcategoriaId);

    // Filtrar insumos que están disponibles
    List<ArticuloManufacturado> findByExisteTrue();

}
