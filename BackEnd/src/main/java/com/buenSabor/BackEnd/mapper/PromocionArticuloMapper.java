package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.config.mapperConfig;
import com.buenSabor.BackEnd.dto.venta.promocionArticulo.PromocionArticuloCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.venta.promocionArticulo.PromocionArticuloCreateDTO;
import com.buenSabor.BackEnd.dto.venta.promocionArticulo.PromocionArticuloResponseDTO;
import com.buenSabor.BackEnd.dto.venta.promocionArticulo.PromocionArticuloUpdateDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.venta.PromocionArticulo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
@Mapper(
    componentModel = "spring", 
    config = mapperConfig.class, // Configuración global
    uses = { 
        ArticuloMapper.class // Solo necesitamos convertir el Articulo. 
        // NO agregues PromocionMapper aquí para evitar ciclos (Padre <-> Hijo).
    }
)
public interface PromocionArticuloMapper extends BeanMapper<
    PromocionArticulo, 
    PromocionArticuloResponseDTO, 
    PromocionArticuloCreateDTO, 
    PromocionArticuloUpdateDTO, 
    PromocionArticuloCadenaSimpleDTO
> {
 
    
    @Override
    @Mapping(source = "articuloId", target = "articulo.id")
    PromocionArticulo toEntity(PromocionArticuloCreateDTO createDto);

    
    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "articuloId", target = "articulo.id")
    void updateFromUpdateDto(PromocionArticuloUpdateDTO updateDto, @MappingTarget PromocionArticulo entity);
}
