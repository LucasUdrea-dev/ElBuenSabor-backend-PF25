package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.venta.detallepromocion.DetallePromocionDTO;
import com.buenSabor.BackEnd.models.venta.DetallePromocion;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {
        PromocionMapper.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DetallePromocionMapper {

    // <--[DetallePromocion detallePromocion]--
    // ==>{DetallePromocionDTO dto, y lo que ignora *-*}
    @Mapping(source = "promocion", target = "promocion")
    // @Mapping(target = "pedido", ignore = true)
    DetallePromocionDTO toDto(DetallePromocion detallePromocion);

    // <--[DetallePromocionDTO dto]--
    // ==>{DetallePromocion entity, y lo que ignora *pedido,id*}
    @Mapping(source = "promocion", target = "promocion")
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "id", ignore = true)
    DetallePromocion toEntity(DetallePromocionDTO dto);

    // <--[DetallePromocionDTO dto, DetallePromocion entity]--
    // ==>{void, y lo que ignora *id,pedido*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    void updateDetallePromocionFromDto(DetallePromocionDTO dto, @MappingTarget DetallePromocion entity);

    // <--[List<DetallePromocion> detallePromociones]--
    // ==>{List<DetallePromocionDTO> list, y lo que ignora *-*}
    List<DetallePromocionDTO> toDtoList(List<DetallePromocion> detallePromociones);
}