/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.seguridad.tiporol.TipoRolDTO;
import com.buenSabor.BackEnd.models.seguridad.TipoRol;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 *
 * @author oscarloha
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TipoRolMapper {

    // <--[TipoRol tipoRol]--
    // ==>{TipoRolDTO dto, y lo que ignora *-*}
    TipoRolDTO toDto(TipoRol tipoRol);

    // <--[List<TipoRol> tipoRoles]--
    // ==>{List<TipoRolDTO> list, y lo que ignora *-*}
    List<TipoRolDTO> toDtoList(List<TipoRol> tipoRoles);

    // <--[TipoRolDTO dto]--
    // ==>{TipoRol entity, y lo que ignora *id,rolList*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rolList", ignore = true)
    TipoRol toEntity(TipoRolDTO dto);

    // <--[TipoRolDTO dto, TipoRol entity]--
    // ==>{void, y lo que ignora *id,rolList*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rolList", ignore = true)
    void updateFromDto(TipoRolDTO dto, @MappingTarget TipoRol entity);
}
