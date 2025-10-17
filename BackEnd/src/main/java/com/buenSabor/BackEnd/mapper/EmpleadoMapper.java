package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.user.empleado.EmpleadoDTO;
import com.buenSabor.BackEnd.models.user.Empleado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        RolMapper.class,
        UserAuthenticationMapper.class,
        TelefonoMapper.class,
        SucursalMapper.class
}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmpleadoMapper {

    // --- Entity to DTO ---
    @Mapping(source = "idSucursal", target = "sucursal")
    @Mapping(source = "rol", target = "rol")
    @Mapping(source = "userAuthentication", target = "userAuthentication")
    @Mapping(source = "telefonoList", target = "telefonoList")
    @Mapping(target = "direccionList", ignore = true)
    EmpleadoDTO toDto(Empleado empleado);

    // --- DTO to Entity ---
    @Mapping(source = "sucursal", target = "idSucursal")
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "userAuthentication", ignore = true)
    @Mapping(target = "telefonoList", ignore = true)
    @Mapping(target = "usuarioDireccionList", ignore = true)
    @Mapping(target = "pedidoList", ignore = true)
    Empleado toEntity(EmpleadoDTO dto);

    // --- Update Entity from DTO ---
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "sucursal", target = "idSucursal")
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "userAuthentication", ignore = true)
    @Mapping(target = "telefonoList", ignore = true)
    @Mapping(target = "usuarioDireccionList", ignore = true)
    @Mapping(target = "pedidoList", ignore = true)
    void updateEmpleadoFromDto(EmpleadoDTO dto, @MappingTarget Empleado empleado);

    // --- List mappings ---
    List<EmpleadoDTO> toDtoList(List<Empleado> empleados);

    List<Empleado> toEntityList(List<EmpleadoDTO> dtos);
}
