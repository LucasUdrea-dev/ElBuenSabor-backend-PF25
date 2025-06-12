/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.producto;


import com.buenSabor.BackEnd.dto.producto.categoria.CategoriaConSubcategoriasDTO;
import com.buenSabor.BackEnd.models.producto.Categoria;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.producto.CategoriaRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author oscarloha
 */
@Service
public class CategoriaService extends BeanServiceImpl<Categoria,Long>{
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    public CategoriaService(BeanRepository<Categoria, Long> beanRepository) {
        super(beanRepository);
    }

    @Transactional
    public List<CategoriaConSubcategoriasDTO> findParaVenta() throws Exception{
        try{

            List<Categoria> categorias = categoriaRepository.findByEsParaElaborarFalse();

            List<CategoriaConSubcategoriasDTO> categoriasConSubcategorias = new ArrayList<>();

            for (Categoria categoria : categorias) {
                
                CategoriaConSubcategoriasDTO categoriaResponseDTO = new CategoriaConSubcategoriasDTO();

                categoriaResponseDTO.setId(categoria.getId());
                categoriaResponseDTO.setDenominacion(categoria.getDenominacion());
                categoriaResponseDTO.setImagen(categoria.getImagen());
                categoriaResponseDTO.setEsParaElaborar(categoria.getEsParaElaborar());
                categoriaResponseDTO.setSubcategorias(categoria.getSubcategorias());

                categoriasConSubcategorias.add(categoriaResponseDTO);

            }

            return categoriasConSubcategorias;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
}
