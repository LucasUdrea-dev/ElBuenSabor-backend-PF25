package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.config.mapperConfig;
import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionCreateDTO;
import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionResponseDTO;
import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionUpdateDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import org.mapstruct.Mapper;
@Mapper(
    componentModel = "spring", 
    config = mapperConfig.class, // Aplica tu configuración global
    uses = { CiudadMapper.class } // Para convertir automáticamente la Ciudad <-> CiudadDTO
)
public interface DireccionMapper extends BeanMapper<
    Direccion, 
    DireccionResponseDTO, 
    DireccionCreateDTO, 
    DireccionUpdateDTO, 
    DireccionCadenaSimpleDTO
> {
    
}