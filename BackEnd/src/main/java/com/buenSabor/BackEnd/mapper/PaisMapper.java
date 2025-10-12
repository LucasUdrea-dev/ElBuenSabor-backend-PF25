/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.ubicacion.pais.PaisDTO;
import com.buenSabor.BackEnd.models.ubicacion.Pais;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author oscarloha
 */
@Mapper(componentModel = "spring")
public interface PaisMapper {
        // <--[PaisDTO dto]--
        // ==>{Pais entity, y lo que ignora *provinciaList,id*}
        @Mapping(target = "provinciaList", ignore = true)
              @Mapping(target = "id", ignore = true)
        Pais toEntity(PaisDTO dto);
        // <--[Pais entity]--
        // ==>{PaisDTO dto, y lo que ignora *-*}
        PaisDTO toDTO(Pais entity);

        // <--[PaisDTO dto, Pais entity]--
        // ==>{void, y lo que ignora *id,provinciaList*}
        @Mapping(target = "id", ignore = true)
        @Mapping(target = "provinciaList", ignore = true)
        void updateFromDto(PaisDTO dto, @org.mapstruct.MappingTarget Pais entity);
}
