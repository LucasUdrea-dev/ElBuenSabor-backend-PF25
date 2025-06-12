/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oscarloha
 */
@Repository
public interface ArticuloRepository extends BeanRepository<Articulo,Long>{
    // Buscar artículos por nombre
    List<Articulo> findByNombreContainingIgnoreCase(String nombre);

    // Buscar por subcategoría
    List<Articulo> findBySubcategoria_Id(Long subcategoriaId);

    // Filtrar artículos que están disponibles
    List<Articulo> findByExisteTrue();

    // Buscar por si son para elaborar
    List<Articulo> findByEsParaElaborarTrue();

    // Combinación de filtros
    List<Articulo> findBySubcategoria_IdAndExisteTrue(Long subcategoriaId);

    // Busca artículos que son para elaborar y existen
    List<Articulo> findByEsParaElaborarTrueAndExisteTrue();
}
