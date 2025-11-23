package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalCreateDTO;
import com.buenSabor.BackEnd.dto.company.sucursal.SucursalResponseDTO;
import com.buenSabor.BackEnd.dto.company.sucursal.SucursalUpdateDTO;
import com.buenSabor.BackEnd.dto.company.sucursal.SucursalDTO;
import com.buenSabor.BackEnd.models.company.Sucursal;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { DireccionMapper.class, EmpresaMapper.class })
public interface SucursalMapper {

    SucursalMapper mapper = Mappers.getMapper(SucursalMapper.class);

    // <--[SucursalDTO dto]--
    // ==>{Sucursal entity, y lo que ignora
    // *empleadoList,stockArticuloInsumoList,pedidoList,promocionList,id,empresa*}
    @Mapping(target = "empleadoList", ignore = true)
    @Mapping(target = "stockArticuloInsumoList", ignore = true)
    @Mapping(target = "pedidoList", ignore = true)
    @Mapping(target = "promocionList", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    Sucursal toEntity(SucursalDTO dto);

    // <--[Sucursal entity]--
    // ==>{SucursalDTO dto, y lo que ignora *-*}
    SucursalDTO toDto(Sucursal entity);

    // <--[List<Sucursal> findAll]--
    // ==>{List<SucursalDTO> list, y lo que ignora *-*}
    public List<SucursalDTO> toDtoList(List<Sucursal> findAll);

    // <--[SucursalDTO dto, Sucursal entity]--
    // ==>{void, y lo que ignora
    // *id,empleadoList,stockArticuloInsumoList,pedidoList,promocionList,empresa*}
    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "empleadoList", ignore = true)
    @org.mapstruct.Mapping(target = "stockArticuloInsumoList", ignore = true)
    @org.mapstruct.Mapping(target = "pedidoList", ignore = true)
    @org.mapstruct.Mapping(target = "promocionList", ignore = true)
    @org.mapstruct.Mapping(target = "empresa", ignore = true)
    void updateFromDto(SucursalDTO dto, @org.mapstruct.MappingTarget Sucursal entity);

    @Mapping(target = "id", ignore = true)
    Sucursal toEntity(SucursalCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(SucursalUpdateDTO dto, @org.mapstruct.MappingTarget Sucursal entity);

    SucursalResponseDTO toResponseDto(Sucursal entity);

    public List<SucursalResponseDTO> toResponseDtoList(List<Sucursal> findAllExistente);
}
