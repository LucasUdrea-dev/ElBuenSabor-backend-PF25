/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.ubicacion.provincia.ProvinciaCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.ubicacion.provincia.ProvinciaCreateDTO;
import com.buenSabor.BackEnd.dto.ubicacion.provincia.ProvinciaResponseDTO;
import com.buenSabor.BackEnd.dto.ubicacion.provincia.ProvinciaUpdateDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.ubicacion.Provincia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 *
 * @author oscarloha
 */
@Mapper(componentModel = "spring", uses = PaisMapper.class)
public interface ProvinciaMapper extends
        BeanMapper<Provincia, ProvinciaResponseDTO, ProvinciaCreateDTO, ProvinciaUpdateDTO, ProvinciaCadenaSimpleDTO> {
    // <--[ProvinciaDTO dto]--
    // ==>{Provincia entity, y lo que ignora *ciudadList,id*}
    @Override
    @Mapping(target = "ciudadList", ignore = true)
    @Mapping(target = "id", ignore = true)
    Provincia toEntity(ProvinciaCreateDTO dto);

    // <--[Provincia entity]--
    // ==>{ProvinciaDTO dto, y lo que ignora *-*}
    @Override
    ProvinciaResponseDTO toResponseDto(Provincia entity);

    // <--[ProvinciaDTO dto, Provincia entity]--
    // ==>{void, y lo que ignora *id,ciudadList*}
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ciudadList", ignore = true)
    void updateFromUpdateDto(ProvinciaUpdateDTO dto, @MappingTarget Provincia entity);
}
