/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.user.telefono.TelefonoDTO;
import com.buenSabor.BackEnd.models.user.Telefono;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author oscarloha
 */

@Mapper(componentModel = "spring")
public interface TelefonoMapper {

    // <--[TelefonoDTO dto]--
    // ==>{Telefono entity, y lo que ignora *usuario,id*}
    @Mapping(target = "usuario", ignore = true) // Evitamos mapeo recursivo
               @Mapping(target = "id", ignore = true)
    Telefono toEntity(TelefonoDTO dto);

    // <--[Telefono entity]--
    // ==>{TelefonoDTO dto, y lo que ignora *-*}
    TelefonoDTO toDto(Telefono entity);

    // <--[List<TelefonoDTO> telefonoList]--
    // ==>{List<Telefono> list, y lo que ignora *-*}
    public List<Telefono> telefonoDtoListToEntityList(List<TelefonoDTO> telefonoList);

    // <--[TelefonoDTO dto, Telefono entity]--
    // ==>{void, y lo que ignora *id,usuario*}
    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "usuario", ignore = true)
    void updateFromDto(TelefonoDTO dto, @org.mapstruct.MappingTarget Telefono entity);
}