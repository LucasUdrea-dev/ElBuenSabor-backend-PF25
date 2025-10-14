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

    // <--[UserAuthenticationRequestDTO dto]--
    // ==>{UserAuthentication entity, y lo que ignora *usuario,id,firebaseUid,authorities*}
    @Mapping(target = "usuario", ignore = true) // Evitamos mapeo circular
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firebaseUid", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    UserAuthentication toEntity(UserAuthenticationRequestDTO dto);

    // <--[UserAuthentication entity]--
    // ==>{UserAuthenticationRequestDTO dto, y lo que ignora *-*}
    UserAuthenticationRequestDTO toDto(UserAuthentication entity);

    // <--[UserAuthenticationRequestDTO dto, UserAuthentication entity]--
    // ==>{void, y lo que ignora *id,usuario,firebaseUid,authorities*}
    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "usuario", ignore = true)
    @org.mapstruct.Mapping(target = "firebaseUid", ignore = true)
    @org.mapstruct.Mapping(target = "authorities", ignore = true)
    void updateFromDto(UserAuthenticationRequestDTO dto, @org.mapstruct.MappingTarget UserAuthentication entity);
   
}