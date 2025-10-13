/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.producto.categoria.CategoriaDTO;
import com.buenSabor.BackEnd.models.producto.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author oscarloha
 */
@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    // <--[CategoriaDTO dto]--
    // ==>{Categoria entity, y lo que ignora *id,subcategorias*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subcategorias", ignore = true)
    Categoria toEntity(CategoriaDTO dto);

    // <--[Categoria entity]--
    // ==>{CategoriaDTO dto, y lo que ignora *-*}
    CategoriaDTO toDTO(Categoria entity);

    // <--[CategoriaDTO dto, Categoria entity]--
    // ==>{void, y lo que ignora *id,subcategorias*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subcategorias", ignore = true)
    void updateFromDto(CategoriaDTO dto, @org.mapstruct.MappingTarget Categoria entity);
}
