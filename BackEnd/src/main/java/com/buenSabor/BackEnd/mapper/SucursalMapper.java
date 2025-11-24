package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.config.mapperConfig;
import com.buenSabor.BackEnd.dto.company.sucursal.SucursalCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.company.sucursal.SucursalCreateDTO;
import com.buenSabor.BackEnd.dto.company.sucursal.SucursalResponseDTO;
import com.buenSabor.BackEnd.dto.company.sucursal.SucursalUpdateDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.company.Sucursal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", config = mapperConfig.class, uses = { DireccionMapper.class, EmpresaMapper.class })
public interface SucursalMapper extends
        BeanMapper<Sucursal, SucursalResponseDTO, SucursalCreateDTO, SucursalUpdateDTO, SucursalCadenaSimpleDTO> {

    // <--[SucursalDTO dto]--
    // ==>{Sucursal entity, y lo que ignora
    // *empleadoList,stockArticuloInsumoList,pedidoList,promocionList,id,empresa*}

    // <--[Sucursal entity]--
    // ==>{SucursalDTO dto, y lo que ignora *-*}

    // <--[List<Sucursal> findAll]--
    // ==>{List<SucursalDTO> list, y lo que ignora *-*}

    // <--[SucursalDTO dto, Sucursal entity]--
    // ==>{void, y lo que ignora
    // *id,empleadoList,stockArticuloInsumoList,pedidoList,promocionList,empresa*}

    @Override
    @Mapping(target = "id", ignore = true)
    Sucursal toEntity(SucursalCreateDTO dto);

    @Override
    @Mapping(target = "id", ignore = true)
    void updateFromUpdateDto(SucursalUpdateDTO dto, @MappingTarget Sucursal entity);

    @Override
    SucursalResponseDTO toResponseDto(Sucursal entity);

}
