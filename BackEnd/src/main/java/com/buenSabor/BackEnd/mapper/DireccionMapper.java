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

   
    @Mapping(source = "ciudad", target = "ciudad")
    DireccionDTO toDto(Direccion direccion);

    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "usuarioList", ignore = true) 
    @Mapping(target = "sucursal", ignore = true) 
    @Mapping(target = "direccionPedidos", ignore = true) 
    Direccion toEntity(DireccionDTO dto);

    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "usuarioList", ignore = true) 
    @Mapping(target = "sucursal", ignore = true) 
    @Mapping(target = "direccionPedidos", ignore = true) 
    void updateDireccionFromDto(DireccionDTO dto, @MappingTarget Direccion entity);

    public List<Direccion> direccionDtoListToEntityList(List<DireccionDTO> direccionList);
}