/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.dto.producto.categoria.CategoriaDTO;
import com.buenSabor.BackEnd.mapper.CategoriaMapper;
import com.buenSabor.BackEnd.models.producto.*;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.producto.CategoriaRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author oscarloha
 */
@Service
public class CategoriaService extends BeanServiceImpl<Categoria,Long>{

    @Autowired
    CategoriaMapper mapper;
    @Autowired
    CategoriaRepository categoriaRepository;

    public CategoriaService(BeanRepository<Categoria, Long> beanRepository, CategoriaRepository categoriaRepository) {
        super(beanRepository);
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public CategoriaDTO crearCategoria(CategoriaDTO dto) {

        //Mapear DTO → Entidad
        Categoria categoria = mapper.toEntity(dto);

        //Persistir
        Categoria saved = categoriaRepository.save(categoria);
        // Rellenar el DTO con los IDs generados

        dto = mapper.toDTO(saved);

        //Devolver el DTO
        return dto;
    }

    @Transactional
    public CategoriaDTO actualizarCategoria(Long id, CategoriaDTO dto) {
        //Recuperar la entidad existente
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Categoria con id: " + id + " no existe"));

        //Actualizar campos simples
        categoria.setDenominacion(dto.getDenominacion());
        categoria.setImagen(dto.getImagen());
        categoria.setEsParaElaborar(dto.isEsParaElaborar());

        //Guardar cambios
        Categoria saved = categoriaRepository.save(categoria);

        //Entity -> DTO
        dto = mapper.toDTO(saved);

        return dto;
    }

    public List<Categoria> buscarPorNombre(String nombre) throws Exception {
        try {
            return categoriaRepository.findByDenominacionContainingIgnoreCase(nombre);
        } catch (Exception e) {
            throw new Exception("Error al buscar por nombre: " + e.getMessage());
        }
    }
    public List<Categoria> getByEsParaElaborar() throws Exception {
        try {
            return categoriaRepository.findCategoriaByEsParaElaborarTrue();
        } catch (Exception e) {
            throw new Exception("Error al buscar categorias para elaborar: " + e.getMessage());
        }
    }

   /* @Transactional
    public Categoria eliminarLogico(Long id) {
        //Recuperar la entidad existente
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Categoria con id: " + id + " no existe"));

        //Seteo valor y guardo
        categoria.setExiste(false);
        return categoriaRepository.save(categoria);
    }*/
}
