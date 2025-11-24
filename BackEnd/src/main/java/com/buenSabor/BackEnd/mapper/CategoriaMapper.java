package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.config.mapperConfig;
import com.buenSabor.BackEnd.dto.producto.categoria.CategoriaCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.producto.categoria.CategoriaCreateDTO;
import com.buenSabor.BackEnd.dto.producto.categoria.CategoriaResponseDTO;
import com.buenSabor.BackEnd.dto.producto.categoria.CategoriaUpdateDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.producto.Categoria;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", config = mapperConfig.class)
public interface CategoriaMapper extends BeanMapper<
    Categoria, 
    CategoriaResponseDTO, 
    CategoriaCreateDTO, 
    CategoriaUpdateDTO, 
    CategoriaCadenaSimpleDTO
> {
    
    
    @AfterMapping
    default void linkSubcategorias(@MappingTarget Categoria categoria) {
        if (categoria.getSubcategorias() != null) {
            categoria.getSubcategorias().forEach(sub -> sub.setCategoria(categoria));
        }
    }
    
}
