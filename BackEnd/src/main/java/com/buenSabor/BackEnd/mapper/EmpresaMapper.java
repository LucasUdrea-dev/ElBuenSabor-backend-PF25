/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.company.empresa.EmpresaDTO;
import com.buenSabor.BackEnd.models.company.Empresa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author oscarloha
 */
@Mapper (componentModel = "spring")
public interface EmpresaMapper {
    
    EmpresaMapper mapper = Mappers.getMapper(EmpresaMapper.class);
    
       @Mapping(target = "sucursalList", ignore = true)
              @Mapping(target = "id", ignore = true)
    Empresa toEntity(EmpresaDTO dto);
    EmpresaDTO toDto(Empresa entity);
        
//    EmpresaResponseDTO EmpresaToResponse(Empresa empresa);
    
}
