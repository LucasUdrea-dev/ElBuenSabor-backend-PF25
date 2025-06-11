/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturado;
import com.buenSabor.BackEnd.models.producto.Subcategoria;
import com.buenSabor.BackEnd.models.producto.Categoria;
import com.buenSabor.BackEnd.models.producto.UnidadMedida;
import com.buenSabor.BackEnd.repositories.producto.ArticuloRepository;
import com.buenSabor.BackEnd.repositories.producto.SubcategorioRepository;
import com.buenSabor.BackEnd.repositories.producto.CategoriaRepository;
import com.buenSabor.BackEnd.repositories.producto.UnidadMedidaRepository;
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
    
    @Autowired
    private SubcategorioRepository subcategorioRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    public ArticuloService(ArticuloRepository articuloRepository) {
        super(articuloRepository);
        this.articuloRepository = articuloRepository;
    }

    public Articulo guardar(Articulo articulo) {
        try {
            // Primero manejamos la unidad de medida
            if (articulo.getUnidadMedida() != null) {
                UnidadMedida unidadMedida;
                if (articulo.getUnidadMedida().getId() != null && articulo.getUnidadMedida().getId() > 0) {
                    // Si tiene ID válido, buscamos la unidad de medida existente
                    unidadMedida = unidadMedidaRepository.findById(articulo.getUnidadMedida().getId())
                            .orElseThrow(() -> new RuntimeException("No se encontró la unidad de medida con id: " + articulo.getUnidadMedida().getId()));
                } else {
                    // Si no tiene ID o es 0, buscamos por la unidad
                    unidadMedida = unidadMedidaRepository.findByUnidad(articulo.getUnidadMedida().getUnidad())
                            .orElseThrow(() -> new RuntimeException("No se encontró la unidad de medida: " + articulo.getUnidadMedida().getUnidad()));
                }
                articulo.setUnidadMedida(unidadMedida);
            }

            if (articulo.getSubcategoria() != null) {
                Subcategoria subcategoria = articulo.getSubcategoria();
                
                // Primero guardamos la categoría si existe
                if (subcategoria.getCategoria() != null) {
                    Categoria categoria = subcategoria.getCategoria();
                    // Guardamos la categoría
                    categoria = categoriaRepository.save(categoria);
                    // Asignamos la categoría guardada a la subcategoría
                    subcategoria.setCategoria(categoria);
                }

                // Luego guardamos la subcategoría
                subcategoria = subcategorioRepository.save(subcategoria);
                // Asignamos la subcategoría guardada al artículo
                articulo.setSubcategoria(subcategoria);
            }

            // Finalmente guardamos el artículo
            return articuloRepository.save(articulo);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el artículo: " + e.getMessage());
        }
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
    
    public Articulo actualizar(Long id, Articulo articulo) {
        try {
            // Verificar si el artículo existe
            Articulo articuloExistente = articuloRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No se encontró el artículo con id: " + id));

            // Actualizar campos básicos
            articuloExistente.setNombre(articulo.getNombre());
            articuloExistente.setPrecio(articulo.getPrecio());
            articuloExistente.setImagenArticulo(articulo.getImagenArticulo());
            articuloExistente.setDescripcion(articulo.getDescripcion());
            articuloExistente.setExiste(articulo.getExiste());
            articuloExistente.setEsParaElaborar(articulo.getEsParaElaborar());

            // Manejar unidad de medida
            if (articulo.getUnidadMedida() != null && articulo.getUnidadMedida().getId() != null) {
                UnidadMedida unidadMedida = unidadMedidaRepository.findById(articulo.getUnidadMedida().getId())
                        .orElseThrow(() -> new RuntimeException("No se encontró la unidad de medida con id: " + articulo.getUnidadMedida().getId()));
                articuloExistente.setUnidadMedida(unidadMedida);
            }

            // Manejar subcategoría y categoría
            if (articulo.getSubcategoria() != null && articulo.getSubcategoria().getId() != null) {
                // Buscar la subcategoría existente
                Subcategoria subcategoriaExistente = subcategorioRepository.findById(articulo.getSubcategoria().getId())
                        .orElseThrow(() -> new RuntimeException("No se encontró la subcategoría con id: " + articulo.getSubcategoria().getId()));
                
                // Si la subcategoría tiene una categoría, buscar la categoría existente
                if (articulo.getSubcategoria().getCategoria() != null && articulo.getSubcategoria().getCategoria().getId() != null) {
                    Categoria categoriaExistente = categoriaRepository.findById(articulo.getSubcategoria().getCategoria().getId())
                            .orElseThrow(() -> new RuntimeException("No se encontró la categoría con id: " + articulo.getSubcategoria().getCategoria().getId()));
                    
                    // Actualizar campos de la categoría si es necesario
                    if (!categoriaExistente.getDenominacion().equals(articulo.getSubcategoria().getCategoria().getDenominacion())) {
                        categoriaExistente.setDenominacion(articulo.getSubcategoria().getCategoria().getDenominacion());
                        categoriaExistente = categoriaRepository.save(categoriaExistente);
                    }
                    if (!categoriaExistente.getImagen().equals(articulo.getSubcategoria().getCategoria().getImagen())) {
                        categoriaExistente.setImagen(articulo.getSubcategoria().getCategoria().getImagen());
                        categoriaExistente = categoriaRepository.save(categoriaExistente);
                    }
                    
                    // Asignar la categoría actualizada a la subcategoría
                    subcategoriaExistente.setCategoria(categoriaExistente);
                }
                
                // Actualizar campos de la subcategoría si es necesario
                if (!subcategoriaExistente.getDenominacion().equals(articulo.getSubcategoria().getDenominacion())) {
                    subcategoriaExistente.setDenominacion(articulo.getSubcategoria().getDenominacion());
                    subcategoriaExistente = subcategorioRepository.save(subcategoriaExistente);
                }
                
                // Asignar la subcategoría actualizada al artículo
                articuloExistente.setSubcategoria(subcategoriaExistente);
            }

            // Guardar y retornar el artículo actualizado
            return articuloRepository.save(articuloExistente);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el artículo: " + e.getMessage());
        }
    }
}
