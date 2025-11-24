package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.config.mapperConfig;
import com.buenSabor.BackEnd.dto.user.telefono.TelefonoCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.user.telefono.TelefonoCreateDTO;
import com.buenSabor.BackEnd.dto.user.telefono.TelefonoResponseDTO;
import com.buenSabor.BackEnd.dto.user.telefono.TelefonoUpdateDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.user.Telefono;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = mapperConfig.class)
public interface TelefonoMapper extends
        BeanMapper<Telefono, TelefonoResponseDTO, TelefonoCreateDTO, TelefonoUpdateDTO, TelefonoCadenaSimpleDTO> {

    // <--[TelefonoDTO dto]--
    // ==>{Telefono entity, y lo que ignora *usuario,id*}
    @Override
    @Mapping(target = "usuario", ignore = true)
    Telefono toEntity(TelefonoCreateDTO createDto);

    // <--[Telefono entity]--
    // ==>{TelefonoDTO dto, y lo que ignora *-*}
    @Override
    TelefonoResponseDTO toResponseDto(Telefono entity);

    // <--[List<TelefonoDTO> telefonoList]--
    // ==>{List<Telefono> list, y lo que ignora *-*}
    @Override
    List<Telefono> toEntityList(List<TelefonoCreateDTO> createDtoList);

    // <--[TelefonoDTO dto, Telefono entity]--
    // ==>{void, y lo que ignora *id,usuario*}
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateFromUpdateDto(TelefonoUpdateDTO updateDto, @org.mapstruct.MappingTarget Telefono entity);
}