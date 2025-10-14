package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.ubicacion.ciudad.CiudadDTO;
import com.buenSabor.BackEnd.models.ubicacion.Ciudad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProvinciaMapper.class)
public interface CiudadMapper {
    // <--[CiudadDTO dto]--
    // ==>{Ciudad entity, y lo que ignora *direccionList,id*}
    @Mapping(target = "direccionList", ignore = true)
    @Mapping(target = "id", ignore = true)
    Ciudad toEntity(CiudadDTO dto);

    // <--[Ciudad entity]--
    // ==>{CiudadDTO dto, y lo que ignora *-*}
    CiudadDTO toDTO(Ciudad entity);

    // <--[CiudadDTO dto, Ciudad entity]--
    // ==>{void, y lo que ignora *id,direccionList*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "direccionList", ignore = true)
    void updateFromDto(CiudadDTO dto, @org.mapstruct.MappingTarget Ciudad entity);
}
