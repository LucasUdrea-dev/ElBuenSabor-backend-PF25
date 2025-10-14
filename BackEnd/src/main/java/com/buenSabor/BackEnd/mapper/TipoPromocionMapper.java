package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.venta.tipopromo.TipoPromocionDTO;
import com.buenSabor.BackEnd.models.venta.TipoPromocion;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TipoPromocionMapper {
    // <--[TipoPromocion entity]--
    // ==>{TipoPromocionDTO dto, y lo que ignora *-*}
    TipoPromocionDTO toDto(TipoPromocion entity);

    // <--[TipoPromocionDTO dto]--
    // ==>{TipoPromocion entity, y lo que ignora *promocionList*}
    // IMPORTANTE: NO ignorar el ID para evitar crear duplicados
    @Mapping(target = "promocionList", ignore = true)
    TipoPromocion toEntity(TipoPromocionDTO dto);

    // <--[List<TipoPromocion> tipoPromociones]--
    // ==>{List<TipoPromocionDTO> list, y lo que ignora *-*}
    public List<TipoPromocionDTO> toDtoList(List<TipoPromocion> tipoPromociones);

    // <--[TipoPromocionDTO dto, TipoPromocion entity]--
    // ==>{void, y lo que ignora *id,promocionList*}
    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "promocionList", ignore = true)
    void updateFromDto(TipoPromocionDTO dto, @org.mapstruct.MappingTarget TipoPromocion entity);
}