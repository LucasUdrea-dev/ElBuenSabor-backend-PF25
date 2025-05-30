/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalDTO;
import com.buenSabor.BackEnd.models.company.Sucursal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author oscarloha
 */
@Mapper (componentModel = "spring", uses = {DireccionMapper.class, EmpresaMapper.class})
public interface SucursalMapper {
    
    SucursalMapper mapper = Mappers.getMapper(SucursalMapper.class);
    
       @Mapping(target = "empleadoList", ignore = true)
       @Mapping(target = "stockArticuloInsumoList", ignore = true)
       @Mapping(target = "pedidoList", ignore = true)
       @Mapping(target = "promocionList", ignore = true)
               @Mapping(target = "id", ignore = true)
              @Mapping(target = "empresa", ignore = true)
     Sucursal toEntity(SucursalDTO dto);
      @Mapping(target = "empresaId", ignore = true)
    SucursalDTO toDto(Sucursal entity);
}
