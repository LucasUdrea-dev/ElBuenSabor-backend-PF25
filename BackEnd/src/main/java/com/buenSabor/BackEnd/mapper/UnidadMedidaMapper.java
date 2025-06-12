/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.producto.medida.UnidadMedidaDTO;
import com.buenSabor.BackEnd.models.producto.UnidadMedida;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author oscarloha
 */
@Mapper(componentModel = "spring")
public interface UnidadMedidaMapper {
    @Mapping(target = "articuloList", ignore = true)
              @Mapping(target = "id", ignore = true)
    UnidadMedida toEntity(UnidadMedidaDTO dto);
    UnidadMedidaDTO toDTO(UnidadMedida entity);
}
