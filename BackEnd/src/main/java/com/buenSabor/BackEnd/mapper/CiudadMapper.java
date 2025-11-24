package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.config.mapperConfig;
import com.buenSabor.BackEnd.dto.ubicacion.ciudad.CiudadCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.ubicacion.ciudad.CiudadCreateDTO;
import com.buenSabor.BackEnd.dto.ubicacion.ciudad.CiudadResponseDTO;
import com.buenSabor.BackEnd.dto.ubicacion.ciudad.CiudadUpdateDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.ubicacion.Ciudad;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring", 
    config = mapperConfig.class, 
    uses = { ProvinciaMapper.class } 
)
public interface CiudadMapper extends BeanMapper<
    Ciudad, 
    CiudadResponseDTO, 
    CiudadCreateDTO, 
    CiudadUpdateDTO, 
    CiudadCadenaSimpleDTO
> {
  
}
