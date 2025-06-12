/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.seguridad.rol.RolDTO;
import com.buenSabor.BackEnd.models.seguridad.Rol;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author oscarloha
 */
@Mapper(componentModel = "spring",uses = TipoRolMapper.class)
public interface RolMapper {

   @Mapping(target = "usuarioList", ignore = true)
   @Mapping(target = "id", ignore = true)
    Rol toEntity(RolDTO dto);

    RolDTO toDto(Rol entity);
}