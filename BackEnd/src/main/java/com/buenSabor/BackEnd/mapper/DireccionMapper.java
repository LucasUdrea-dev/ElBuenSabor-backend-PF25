/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionDTO;
import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 *
 * @author oscarloha
 */

@Mapper(componentModel = "spring",
        uses = CiudadMapper.class)
public interface DireccionMapper {

    // You might want to map the nested 'ciudad' from entity to DTO if it's included in DireccionDTO
    @Mapping(source = "ciudad", target = "ciudad")
    DireccionDTO toDto(Direccion direccion);

    @Mapping(target = "id", ignore = true) // ID handled by DB
    @Mapping(target = "usuarioList", ignore = true) // Relationships not mapped from DTO
    @Mapping(target = "sucursal", ignore = true) // Relationships not mapped from DTO
    @Mapping(target = "direccionPedidos", ignore = true) // Relationships not mapped from DTO
    // If Ciudad entity has a "direccionList" and you map from CiudadDTO, you might need:
    // @Mapping(target = "ciudad.direccionList", ignore = true)
    // Similarly for Provincia and Pais:
    // @Mapping(target = "ciudad.provincia.ciudadList", ignore = true)
    // @Mapping(target = "ciudad.provincia.pais.provinciaList", ignore = true)
    Direccion toEntity(DireccionDTO dto);

    @Mapping(target = "id", ignore = true) // Don't update ID
    @Mapping(target = "usuarioList", ignore = true) // Relationships not updated from DTO
    @Mapping(target = "sucursal", ignore = true) // Relationships not updated from DTO
    @Mapping(target = "direccionPedidos", ignore = true) // Relationships not updated from DTO
    // Same for nested entities' inverse collections if they appear as warnings:
    // @Mapping(target = "ciudad.direccionList", ignore = true)
    // @Mapping(target = "ciudad.provincia.ciudadList", ignore = true)
    // @Mapping(target = "ciudad.provincia.pais.provinciaList", ignore = true)
    void updateDireccionFromDto(DireccionDTO dto, @MappingTarget Direccion entity);

    public List<Direccion> direccionDtoListToEntityList(List<DireccionDTO> direccionList);
}