package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.config.mapperConfig;
import com.buenSabor.BackEnd.dto.company.empresa.EmpresaCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.company.empresa.EmpresaCreateDTO;
import com.buenSabor.BackEnd.dto.company.empresa.EmpresaUpdateDTO;
import com.buenSabor.BackEnd.dto.company.empresa.EmpresaResponseDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.company.Empresa;
import org.mapstruct.Mapper;
@Mapper(
    componentModel = "spring", 
    config = mapperConfig.class, // Configuración global
    uses = { SucursalMapper.class } // Para convertir la lista de sucursales
)
public interface EmpresaMapper extends BeanMapper<
    Empresa, 
    EmpresaResponseDTO, 
    EmpresaCreateDTO, 
    EmpresaUpdateDTO, 
    EmpresaCadenaSimpleDTO
> {

}
