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
        @Mapping(target = "ciudadList", ignore = true)
              @Mapping(target = "id", ignore = true)
    Provincia toEntity(ProvinciaDTO dto);
    ProvinciaDTO toDTO(Provincia entity);
}

