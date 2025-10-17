package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.venta.promocion.PromocionDTO;
import com.buenSabor.BackEnd.dto.venta.promocion.PromocionLiteDTO;
import com.buenSabor.BackEnd.models.venta.Promocion;
import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {
        SucursalMapper.class,
        TipoPromocionMapper.class,
        PromocionArticuloMapper.class
}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PromocionMapper {

    @Mapping(source = "sucursal", target = "sucursal")
    @Mapping(source = "tipoPromocion", target = "tipoPromocion")
    @Mapping(source = "promocionArticuloList", target = "promocionArticuloList")
    PromocionDTO toDto(Promocion promocion);

    @Mapping(source = "sucursal", target = "sucursal")
    @Mapping(source = "tipoPromocion", target = "tipoPromocion")
    @Mapping(source = "promocionArticuloList", target = "promocionArticuloList")
    @Mapping(target = "detallePromocionList", ignore = true)
    Promocion toEntity(PromocionDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "detallePromocionList", ignore = true)
    @Mapping(target = "promocionArticuloList", ignore = true)
    void updatePromocionFromDto(PromocionDTO dto, @MappingTarget Promocion entity);

    List<PromocionDTO> toDtoList(List<Promocion> findAll);

    Set<PromocionDTO> toDtoSet(Set<Promocion> entities);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "denominacion", target = "denominacion")
    @Mapping(source = "descripcion", target = "descripcion")
    @Mapping(source = "precioRebajado", target = "precioRebajado")
    @Mapping(source = "imagen", target = "imagen")
    PromocionLiteDTO toPromocionLiteDto(Promocion promocion);

    List<PromocionLiteDTO> toPromocionLiteDtoList(List<Promocion> promociones);
}