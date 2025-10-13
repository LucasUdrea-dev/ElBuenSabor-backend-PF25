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

    // <--[SubcategoriaDTO dto]--
    // ==>{Subcategoria entity, y lo que ignora *id,articuloList*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "articuloList", ignore = true)
    Subcategoria toEntity(SubcategoriaDTO dto);
    
    // <--[Subcategoria entity]--
    // ==>{SubcategoriaDTO dto, y lo que ignora *-*}
    SubcategoriaDTO toDto(Subcategoria entity);

    // <--[SubcategoriaDTO dto, Subcategoria entity]--
    // ==>{void, y lo que ignora *id,articuloList*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "articuloList", ignore = true)
    void updateFromDto(SubcategoriaDTO dto, @org.mapstruct.MappingTarget Subcategoria entity);
}
