package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionDTO;
import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = CiudadMapper.class)
public interface DireccionMapper {

    // <--[Direccion direccion]--
    // ==>{DireccionDTO dto, y lo que ignora *-*}
    @Mapping(source = "ciudad", target = "ciudad")
    DireccionDTO toDto(Direccion direccion);

    // <--[DireccionDTO dto]--
    // ==>{Direccion entity, y lo que ignora
    // *id,usuarioDireccionList,sucursal,direccionPedidos*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuarioDireccionList", ignore = true)
    @Mapping(target = "sucursal", ignore = true)
    @Mapping(target = "direccionPedidos", ignore = true)
    Direccion toEntity(DireccionDTO dto);

    // <--[DireccionDTO dto, Direccion entity]--
    // ==>{void, y lo que ignora
    // *id,usuarioDireccionList,sucursal,direccionPedidos*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuarioDireccionList", ignore = true)
    @Mapping(target = "sucursal", ignore = true)
    @Mapping(target = "direccionPedidos", ignore = true)
    void updateDireccionFromDto(DireccionDTO dto, @MappingTarget Direccion entity);

    // <--[List<DireccionDTO> direccionList]--
    // ==>{List<Direccion> list, y lo que ignora *-*}
    public List<Direccion> direccionDtoListToEntityList(List<DireccionDTO> direccionList);
}