/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.config.mapperConfig;
import com.buenSabor.BackEnd.dto.ubicacion.pais.PaisCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.ubicacion.pais.PaisCreateDTO;
import com.buenSabor.BackEnd.dto.ubicacion.pais.PaisResponseDTO;
import com.buenSabor.BackEnd.dto.ubicacion.pais.PaisUpdateDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.ubicacion.Pais;
import org.mapstruct.Mapper;

/**
 *
 * @author oscarloha
 */
@Mapper(
    componentModel = "spring", 
    config = mapperConfig.class, // Configuración global
    uses = { ProvinciaMapper.class } // Para convertir la lista de provincias si el ResponseDTO la tiene
)
public interface PaisMapper extends BeanMapper<
    Pais, 
    PaisResponseDTO, 
    PaisCreateDTO, 
    PaisUpdateDTO, 
    PaisCadenaSimpleDTO
> {
}
