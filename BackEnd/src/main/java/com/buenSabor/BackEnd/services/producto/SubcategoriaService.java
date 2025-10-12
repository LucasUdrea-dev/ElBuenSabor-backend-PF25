/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.dto.producto.subcategoria.SubcategoriaDTO;
import com.buenSabor.BackEnd.mapper.SubcategoriaMapper;
import com.buenSabor.BackEnd.models.producto.Categoria;
import com.buenSabor.BackEnd.models.producto.Subcategoria;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.producto.CategoriaRepository;
import com.buenSabor.BackEnd.repositories.producto.SubcategorioRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author oscarloha
 */
@Service
public class SubcategoriaService extends BeanServiceImpl<Subcategoria, Long> {

    @Autowired
    SubcategorioRepository subcategoriaRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    SubcategoriaMapper mapper;

    public SubcategoriaService(BeanRepository<Subcategoria, Long> beanRepository,
            SubcategorioRepository subcategoriaRepository) {
        super(beanRepository);
        this.subcategoriaRepository = subcategoriaRepository;
    }

    @Transactional
    public SubcategoriaDTO crearSubcategoria(SubcategoriaDTO dto) {

        // Mapear DTO → Entidad
        Subcategoria subcategoria = mapper.toEntity(dto);

        // Recupera la categoría existente
        Long idCategoria = dto.getCategoria().getId();
        Categoria categoriaExistente = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException(
                        "No existe categoría con id=" + idCategoria));

        subcategoria.setCategoria(categoriaExistente);

        // Persistir
        Subcategoria subcategoriaSaved = subcategoriaRepository.save(subcategoria);

        // Rellenar el DTO con los IDs generados
        dto = mapper.toDto(subcategoriaSaved);

        // Devolver el DTO
        return dto;
    }

    @Transactional
    public SubcategoriaDTO actualizarSubcategoria(Long id, SubcategoriaDTO dto) {
        // Recuperar la entidad existente
        Subcategoria subcategoria = subcategoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Subcategoria con id: " + id + " no existe."));

        // Actualizar campos simples
        subcategoria.setDenominacion(dto.getDenominacion());

        // Recupero Categoria
        Long idCategoria = dto.getCategoria().getId();
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException(
                        "Categoria con id : " + idCategoria + " no existe"));

        if (categoria != null) {
            subcategoria.setCategoria(categoria);
        }

        // Guardar cambios
        Subcategoria subcategoriaUpdated = subcategoriaRepository.save(subcategoria);

        // Entity -> DTO
        dto = mapper.toDto(subcategoriaUpdated);

        return dto;
    }

    public List<Subcategoria> buscarPorNombre(String nombre) throws Exception {
        try {
            return subcategoriaRepository.findByDenominacionContainingIgnoreCase(nombre);
        } catch (Exception e) {
            throw new Exception("Error al buscar por nombre: " + e.getMessage());
        }
    }

    public List<Subcategoria> buscarPorNombreCategoria(String nombre) throws Exception {
        try {
            return subcategoriaRepository.filtrarPorCategoria(nombre);
        } catch (Exception e) {
            throw new Exception("Error al buscar por nombre: " + e.getMessage());
        }
    }

    /*
     * @Transactional
     * public Subcategoria eliminarLogico(Long id) {
     * //Recuperar la entidad existente
     * Subcategoria subcategoria = subcategoriaRepository.findById(id)
     * .orElseThrow(() -> new RuntimeException("Subcategoria con id: " + id +
     * " no existe"));
     * 
     * //Seteo valor y guardo
     * subcategoria.setExiste(false);
     * return categoriaRepository.save(subcategoria);
     * }
     */
}
