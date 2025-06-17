/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.producto.subcategoria.SubcategoriaDTO;
import com.buenSabor.BackEnd.models.producto.Subcategoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author oscarloha
 */
@Mapper(componentModel = "spring", uses = CategoriaMapper.class)
public interface SubcategoriaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "articuloList", ignore = true)
    Subcategoria toEntity(SubcategoriaDTO dto);
    
    SubcategoriaDTO toDto(Subcategoria entity);
}
