package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.config.mapperConfig;
import com.buenSabor.BackEnd.dto.user.empleado.EmpleadoCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.user.empleado.EmpleadoCreateDTO;
import com.buenSabor.BackEnd.dto.user.empleado.EmpleadoResponseDTO;
import com.buenSabor.BackEnd.dto.user.empleado.EmpleadoUpdateDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.user.Empleado;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring", 
    config = mapperConfig.class, // Limpia el código de "ignores"
    uses = { 
        RolMapper.class, 
        UserAuthenticationMapper.class, 
        TelefonoMapper.class, 
        SucursalMapper.class,
        DireccionMapper.class // Agregado por si quieres mapear direcciones
    }
)
public interface EmpleadoMapper extends BeanMapper<
    Empleado, 
    EmpleadoResponseDTO, 
    EmpleadoCreateDTO, 
    EmpleadoUpdateDTO, 
    EmpleadoCadenaSimpleDTO
> {
    
}
