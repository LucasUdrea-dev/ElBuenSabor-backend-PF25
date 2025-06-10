/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.repositories.producto.ArticuloRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import java.util.List;
<<<<<<< HEAD
import java.util.Optional;

=======
>>>>>>> origin/main
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

    public List<Articulo> listarDisponibles() throws Exception {
        try {
            return articuloRepository.findByExisteTrue();
        } catch (Exception e) {
            throw new Exception("Error al buscar artículos disponibles: " + e.getMessage());
        }
    }

    public List<Articulo> listarParaElaborar() throws Exception {
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
<<<<<<< HEAD

    public List<Articulo> buscarArticuloSiEsParaElaborarYExiste() throws Exception{
        try {
            Optional<List<Articulo>> listOptional = articuloRepository.findArticuloByEsParaElaborarTrueAndExisteTrue();
            return listOptional.get();
        }catch (Exception e){
            throw new Exception("Error al buscar articulos con filtro: Es para elaborar y Existe. Message: " + e.getMessage());
        }
    }
=======
>>>>>>> origin/main
}
