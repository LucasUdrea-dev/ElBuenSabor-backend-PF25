package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.producto.subcategoria.SubcategoriaCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.producto.subcategoria.SubcategoriaCreateDTO;
import com.buenSabor.BackEnd.dto.producto.subcategoria.SubcategoriaResponseDTO;
import com.buenSabor.BackEnd.dto.producto.subcategoria.SubcategoriaUpdateDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.producto.Subcategoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = CategoriaMapper.class)
public interface SubcategoriaMapper extends
        BeanMapper<Subcategoria, SubcategoriaResponseDTO, SubcategoriaCreateDTO, SubcategoriaUpdateDTO, SubcategoriaCadenaSimpleDTO> {

    // <--[SubcategoriaDTO dto]--
    // ==>{Subcategoria entity, y lo que ignora *id,articuloList*}
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "articuloList", ignore = true)
    Subcategoria toEntity(SubcategoriaCreateDTO dto);

    // <--[Subcategoria entity]--
    // ==>{SubcategoriaDTO dto, y lo que ignora *-*}
    @Override
    SubcategoriaResponseDTO toResponseDto(Subcategoria entity);

    // <--[SubcategoriaDTO dto, Subcategoria entity]--
    // ==>{void, y lo que ignora *id,articuloList*}
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "articuloList", ignore = true)
    void updateFromUpdateDto(SubcategoriaUpdateDTO dto, @MappingTarget Subcategoria entity);

}
