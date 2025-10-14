package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.producto.medida.UnidadMedidaDTO;
import com.buenSabor.BackEnd.models.producto.UnidadMedida;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UnidadMedidaMapper {
    // <--[UnidadMedidaDTO dto]--
    // ==>{UnidadMedida entity, y lo que ignora *articuloList*}
    // IMPORTANTE: NO ignorar el ID - UnidadMedida está precargada en BD por
    // MeasumentInitializer
    @Mapping(target = "articuloList", ignore = true)
    UnidadMedida toEntity(UnidadMedidaDTO dto);

    // <--[UnidadMedida entity]--
    // ==>{UnidadMedidaDTO dto, y lo que ignora *-*}
    UnidadMedidaDTO toDTO(UnidadMedida entity);

    // <--[UnidadMedidaDTO dto, UnidadMedida entity]--
    // ==>{void, y lo que ignora *id,articuloList*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "articuloList", ignore = true)
    void updateFromDto(UnidadMedidaDTO dto, @org.mapstruct.MappingTarget UnidadMedida entity);
}
