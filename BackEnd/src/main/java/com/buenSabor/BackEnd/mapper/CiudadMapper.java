/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.ubicacion.ciudad.CiudadDTO;
import com.buenSabor.BackEnd.models.ubicacion.Ciudad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author oscarloha
 */
@Mapper(componentModel = "spring", uses = ProvinciaMapper.class)
public interface CiudadMapper {
    @Mapping(target = "direccionList", ignore = true)
              @Mapping(target = "id", ignore = true)
    Ciudad toEntity(CiudadDTO dto);
    CiudadDTO toDTO(Ciudad entity);
}
