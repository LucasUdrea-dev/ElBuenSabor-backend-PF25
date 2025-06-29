/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.repositories.producto.ArticuloRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 *
 * @author oscarloha
 */
@Service
@Transactional
public class ArticuloService extends BeanServiceImpl<Articulo, Long> {

    @Autowired
    private ArticuloRepository articuloRepository;
    
    public ArticuloService(ArticuloRepository articuloRepository) {
        super(articuloRepository);
        this.articuloRepository = articuloRepository;
    }

    public List<Articulo> buscarPorNombre(String nombre) throws Exception {
        try {
            return articuloRepository.findByNombreContainingIgnoreCase(nombre);
        } catch (Exception e) {
            throw new Exception("Error al buscar por nombre: " + e.getMessage());
        }
    }

    public List<Articulo> buscarPorSubcategoria(Long subCategoriaId) throws Exception {
        try {
            return articuloRepository.findBySubcategoria_Id(subCategoriaId);
        } catch (Exception e) {
            throw new Exception("Error al buscar por subcategoría: " + e.getMessage());
        }
    }

    public List<Articulo> findByExisteTrue() throws Exception {
        try {
            return articuloRepository.findByExisteTrue();
        } catch (Exception e) {
            throw new Exception("Error al buscar artículos disponibles: " + e.getMessage());
        }
    }

    public List<Articulo> findByEsParaElaborarTrue() throws Exception {
        try {
            return articuloRepository.findByEsParaElaborarTrue();
        } catch (Exception e) {
            throw new Exception("Error al buscar artículos para elaborar: " + e.getMessage());
        }
    }

    public List<Articulo> buscarDisponiblesPorSubcategoria(Long subcategoriaId) throws Exception {
        try {
            return articuloRepository.findBySubcategoria_IdAndExisteTrue(subcategoriaId);
        } catch (Exception e) {
            throw new Exception("Error al buscar artículos disponibles por subcategoría: " + e.getMessage());
        }
    }

    public List<Articulo> buscarArticuloSiEsParaElaborarYExiste() throws Exception{
        try {
            return articuloRepository.findByEsParaElaborarTrueAndExisteTrue();
        }catch (Exception e){
            throw new Exception("Error al buscar articulos con filtro: Es para elaborar y Existe. Message: " + e.getMessage());
        }
    }    
    
}
