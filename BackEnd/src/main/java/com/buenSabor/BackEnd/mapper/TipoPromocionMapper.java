/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.venta.tipopromo.TipoPromocionDTO;
import com.buenSabor.BackEnd.models.venta.TipoPromocion;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author oscarloha
 */
@Mapper(componentModel = "spring")
public interface TipoPromocionMapper {
    TipoPromocionDTO toDto(TipoPromocion entity);
    @Mapping(target = "promocionList", ignore = true)
    TipoPromocion toEntity(TipoPromocionDTO dto);

    public List<TipoPromocionDTO> toDtoList(List<TipoPromocion> tipoPromociones);
}