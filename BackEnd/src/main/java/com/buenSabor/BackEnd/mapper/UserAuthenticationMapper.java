/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationRequestDTO;
import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author oscarloha
 */
@Mapper(componentModel = "spring")
public interface UserAuthenticationMapper {

    @Mapping(target = "usuario", ignore = true) // Evitamos mapeo circular
               @Mapping(target = "id", ignore = true)

    UserAuthentication toEntity(UserAuthenticationRequestDTO dto);

    UserAuthenticationRequestDTO toDto(UserAuthentication entity);

   
}