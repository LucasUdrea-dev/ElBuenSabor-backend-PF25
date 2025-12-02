package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.venta.promocionArticulo.PromocionArticuloDTO;
import com.buenSabor.BackEnd.models.venta.PromocionArticulo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ArticuloMapper.class, PromocionMapper.class })
public interface PromocionArticuloMapper {

    // Map entity to DTO
    // <--[PromocionArticulo entity]--
    // ==> {PromocionArticuloDTO dto, y lo que ignora *-*}
    @Mapping(source = "idArticulo", target = "articulo")
    @Mapping(source = "id", target = "id")
    PromocionArticuloDTO toDto(PromocionArticulo entity);

    // Map DTO to entity
    // @InheritInverseConfiguration 
    // clarity here
    // <--[PromocionArticuloDTO dto]--
    // ==> {PromocionArticulo entity, y lo que ignora *idPromocion*}
    @Mapping(target = "idArticulo", source = "articulo")
    @Mapping(target = "idPromocion", ignore = true)
    @Mapping(target = "id", source = "id") 
    PromocionArticulo toEntity(PromocionArticuloDTO dto);

    // <--[PromocionArticuloDTO dto, PromocionArticulo entity]--
    // ==>{void, y lo que ignora *id,idPromocion*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idPromocion", ignore = true)
    @Mapping(target = "idArticulo", source = "articulo")
    void updateFromDto(PromocionArticuloDTO dto, @MappingTarget PromocionArticulo entity);
}