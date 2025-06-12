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
        @Mapping(target = "provinciaList", ignore = true)
              @Mapping(target = "id", ignore = true)
    Pais toEntity(PaisDTO dto);
    PaisDTO toDTO(Pais entity);
}
