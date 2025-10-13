/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.ubicacion.provincia.ProvinciaDTO;
import com.buenSabor.BackEnd.models.ubicacion.Provincia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author oscarloha
 */
@Mapper(componentModel = "spring", uses = PaisMapper.class)
public interface ProvinciaMapper {
        // <--[ProvinciaDTO dto]--
        // ==>{Provincia entity, y lo que ignora *ciudadList,id*}
        @Mapping(target = "ciudadList", ignore = true)
              @Mapping(target = "id", ignore = true)
        Provincia toEntity(ProvinciaDTO dto);
        // <--[Provincia entity]--
        // ==>{ProvinciaDTO dto, y lo que ignora *-*}
        ProvinciaDTO toDTO(Provincia entity);

        // <--[ProvinciaDTO dto, Provincia entity]--
        // ==>{void, y lo que ignora *id,ciudadList*}
        @Mapping(target = "id", ignore = true)
        @Mapping(target = "ciudadList", ignore = true)
        void updateFromDto(ProvinciaDTO dto, @org.mapstruct.MappingTarget Provincia entity);
}

