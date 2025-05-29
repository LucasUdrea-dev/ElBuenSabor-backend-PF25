/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author oscarloha
 */
@Mapper (componentModel = "spring")
public interface SucursalMapper {
    
    SucursalMapper mapper = Mappers.getMapper(SucursalMapper.class);
}
