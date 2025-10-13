/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.seguridad.rol.RolDTO;
import com.buenSabor.BackEnd.models.seguridad.Rol;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 *
 * @author oscarloha
 */
@Mapper(componentModel = "spring", uses = TipoRolMapper.class)
public interface RolMapper {

    // <--[RolDTO dto]--
    // ==>{Rol entity, y lo que ignora *usuarioList*}
    // IMPORTANTE: NO ignorar el ID para evitar crear duplicados de Rol
    @Mapping(target = "usuarioList", ignore = true)
    Rol toEntity(RolDTO dto);

    // <--[Rol entity]--
    // ==>{RolDTO dto, y lo que ignora *-*}
    RolDTO toDto(Rol entity);

    // <--[RolDTO dto, Rol entity]--
    // ==>{void, y lo que ignora *id,usuarioList*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuarioList", ignore = true)
    void updateFromDto(RolDTO dto, @MappingTarget Rol entity);
}