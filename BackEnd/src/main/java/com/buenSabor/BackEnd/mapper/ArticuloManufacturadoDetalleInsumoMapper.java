package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.config.mapperConfig;
import com.buenSabor.BackEnd.dto.producto.manufacturadodetalle.ArticuloManufacturadoDetalleCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.producto.manufacturadodetalle.ArticuloManufacturadoDetalleCreateDTO;
import com.buenSabor.BackEnd.dto.producto.manufacturadodetalle.ArticuloManufacturadoDetalleResponseDTO;
import com.buenSabor.BackEnd.dto.producto.manufacturadodetalle.ArticuloManufacturadoDetalleUpdateDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturadoDetalleInsumo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper for the entity {@link ArticuloManufacturadoDetalleInsumo} and its
 * DTOs.
 */
@Mapper(config = mapperConfig.class, componentModel = "spring", uses = { SubcategoriaMapper.class,
        UnidadMedidaMapper.class, StockArticuloInsumoMapper.class })
public interface ArticuloManufacturadoDetalleInsumoMapper extends
        BeanMapper<ArticuloManufacturadoDetalleInsumo, ArticuloManufacturadoDetalleResponseDTO, ArticuloManufacturadoDetalleCreateDTO, ArticuloManufacturadoDetalleUpdateDTO, ArticuloManufacturadoDetalleCadenaSimpleDTO> {

    @Override
    @Mapping(target = "articuloManufacturado", ignore = true) 
    @Mapping(target = "articuloInsumo", ignore = true)        
    ArticuloManufacturadoDetalleInsumo toEntity(ArticuloManufacturadoDetalleCreateDTO createDto);


    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "articuloManufacturado", ignore = true)
    @Mapping(target = "articuloInsumo", ignore = true)
    void updateFromUpdateDto(ArticuloManufacturadoDetalleUpdateDTO updateDto, @MappingTarget ArticuloManufacturadoDetalleInsumo entity);

    @Override
    @Mapping(target = "articuloInsumo.stockArticuloInsumo.articuloInsumo", ignore = true)
    ArticuloManufacturadoDetalleCadenaSimpleDTO toSimpleDto(ArticuloManufacturadoDetalleInsumo entity);
}
