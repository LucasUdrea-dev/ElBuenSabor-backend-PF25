package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.company.empresa.EmpresaDTO;
import com.buenSabor.BackEnd.models.company.Empresa;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = SucursalMapper.class)
public interface EmpresaMapper {

    EmpresaMapper mapper = Mappers.getMapper(EmpresaMapper.class);

    // <--[EmpresaDTO dto]--
    // ==>{Empresa entity, y lo que ignora *sucursalList,id*}
    @Mapping(target = "sucursalList", ignore = true)
    @Mapping(target = "id", ignore = true)
    Empresa toEntity(EmpresaDTO dto);

    // <--[Empresa entity]--
    // ==>{EmpresaDTO dto, y lo que ignora *-*}
    EmpresaDTO toDto(Empresa entity);

    // <--[List<Empresa> empresas]--
    // ==>{List<EmpresaDTO> list, y lo que ignora *-*}
    List<EmpresaDTO> toDtoList(List<Empresa> empresas);

    // <--[EmpresaDTO dto, Empresa entity]--
    // ==>{void, y lo que ignora *id,sucursalList*}
    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "sucursalList", ignore = true)
    void updateFromDto(EmpresaDTO dto, @org.mapstruct.MappingTarget Empresa entity);

}
