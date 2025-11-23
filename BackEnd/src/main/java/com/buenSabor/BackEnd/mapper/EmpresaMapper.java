package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.company.empresa.EmpresaCreateDTO;
import com.buenSabor.BackEnd.dto.company.empresa.EmpresaUpdateDTO;
import com.buenSabor.BackEnd.dto.company.empresa.EmpresaResponseDTO;
import com.buenSabor.BackEnd.models.company.Empresa;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;

@Mapper(componentModel = "spring", uses = SucursalMapper.class)
public interface EmpresaMapper {

    EmpresaMapper mapper = Mappers.getMapper(EmpresaMapper.class);

    // // <--[Empresa entity]--
    // // ==>{EmpresaCreateDTO dto, y lo que ignora *-*}
    // Empresa toEntity(EmpresaCreateDTO dto);
    //
    // // <--[EmpresaCreateDTO dto, Empresa entity]--
    // // ==>{void, y lo que ignora *id,sucursalList*}
    // @org.mapstruct.Mapping(target = "id", ignore = true)
    // @org.mapstruct.Mapping(target = "sucursalList", ignore = true)
    // void updateFromDto(EmpresaCreateDTO dto, @org.mapstruct.MappingTarget Empresa
    // entity);
    //
    // @Mapping(target = "id", ignore = true)
    // @Mapping(target = "sucursalList", ignore = true)
    // Empresa toEntity(EmpresaUpdateDTO dto);
    //
    // @Mapping(target = "id", ignore = true)
    // @Mapping(target = "sucursalList", ignore = true)
    // void updateFromUpdateDto(EmpresaUpdateDTO dto, @org.mapstruct.MappingTarget
    // Empresa entity);
    //
    // EmpresaResponseDTO toResponseDto(Empresa entity);
    //
    // List<EmpresaResponseDTO> toResponseDtoList(List<Empresa> empresas);
    //

    // [entity] <--crear--> [DTO]

    Empresa toEntity(EmpresaCreateDTO dto);

    // -------------------------------------------------------------------------

    // [entity] <--update--> [DTO]

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sucursalList", ignore = true)
    void updateFromDto(EmpresaUpdateDTO dto, @org.mapstruct.MappingTarget Empresa entity);

    // -------------------------------------------------------------------------

    // [entity] <--response--> [dto]

    EmpresaResponseDTO toResponseDto(Empresa entity);

    // {[entity]} <--responseList--> {[dto]}

    List<EmpresaResponseDTO> toResponseDtoList(List<Empresa> empresas);

    public void updateFromUpdateDto(EmpresaUpdateDTO dto, Empresa empresa);

    public ResponseEntity<?> toDto();

}
