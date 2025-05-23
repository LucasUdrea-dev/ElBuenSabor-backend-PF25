/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.repositories.producto.ArticuloRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author oscarloha
 */
@Service
public class ArticuloService extends BeanServiceImpl<Articulo, Long> {

    private final ArticuloRepository articuloRepository;

    public ArticuloService(ArticuloRepository articuloRepository) {
        super(articuloRepository);
        this.articuloRepository = articuloRepository;
    }

    // Buscar artículos por nombre
    public List<Articulo> buscarPorNombre(String nombre) throws Exception {
        try {
            return articuloRepository.findByNombreContainingIgnoreCase(nombre);
        } catch (Exception e) {
            throw new Exception("Error al buscar por nombre: " + e.getMessage());
        }
    }

    // Buscar artículos por subcategoría
    public List<Articulo> buscarPorSubcategoria(Long subCategoriaId) throws Exception {
        try {
            return articuloRepository.findBySubCategoria_Id(subCategoriaId);
        } catch (Exception e) {
            throw new Exception("Error al buscar por subcategoría: " + e.getMessage());
        }
    }

    // Obtener solo los disponibles
    public List<Articulo> listarDisponibles() throws Exception {
        try {
            return articuloRepository.findByExisteTrue();
        } catch (Exception e) {
            throw new Exception("Error al buscar artículos disponibles: " + e.getMessage());
        }
    }

    // Obtener artículos que son para elaborar
    public List<Articulo> listarParaElaborar() throws Exception {
        try {
            return articuloRepository.findByEsParaElaborarTrue();
        } catch (Exception e) {
            throw new Exception("Error al buscar artículos para elaborar: " + e.getMessage());
        }
    }

    // Filtro combinado
    public List<Articulo> buscarDisponiblesPorSubcategoria(Long subCategoriaId) throws Exception {
        try {
            return articuloRepository.findBySubCategoria_IdAndExisteTrue(subCategoriaId);
        } catch (Exception e) {
            throw new Exception("Error al buscar artículos disponibles por subcategoría: " + e.getMessage());
        }
    }
}
