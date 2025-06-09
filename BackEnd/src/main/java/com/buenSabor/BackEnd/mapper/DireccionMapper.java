/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionDTO;
import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author oscarloha
 */
@Mapper(componentModel = "spring", uses = CiudadMapper.class)
public interface DireccionMapper {
     @Mapping(target = "sucursal", ignore = true)
     @Mapping(target = "usuarioList", ignore = true)
     @Mapping(target = "direccionPedidos", ignore = true)
     @Mapping(target = "id", ignore = true)
    Direccion toEntity(DireccionDTO dto);
    DireccionDTO toDTO(Direccion entity);
}

