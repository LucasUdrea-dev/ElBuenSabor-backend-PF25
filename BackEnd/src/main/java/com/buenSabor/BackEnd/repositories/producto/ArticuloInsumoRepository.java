/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oscarloha
 */
@Repository
public interface ArticuloInsumoRepository extends BeanRepository<ArticuloInsumo,Long>{
    
    // Buscar insumos por nombre
    List<ArticuloInsumo> findByNombreContainingIgnoreCase(String nombre);

    // Buscar por subcategoría
    List<ArticuloInsumo> findBySubcategoria_Id(Long subcategoriaId);

    // Filtrar insumos que están disponibles
    List<ArticuloInsumo> findByExisteTrue();

}
