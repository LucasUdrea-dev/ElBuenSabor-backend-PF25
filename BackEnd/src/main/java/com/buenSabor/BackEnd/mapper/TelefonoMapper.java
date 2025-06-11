/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.user.telefono.TelefonoDTO;
import com.buenSabor.BackEnd.models.user.Telefono;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author oscarloha
 */

@Mapper(componentModel = "spring")
public interface TelefonoMapper {

    @Mapping(target = "usuario", ignore = true) // Evitamos mapeo recursivo
               @Mapping(target = "id", ignore = true)

    Telefono toEntity(TelefonoDTO dto);

    TelefonoDTO toDto(Telefono entity);
}