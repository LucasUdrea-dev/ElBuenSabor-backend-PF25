package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.config.mapperConfig;
import com.buenSabor.BackEnd.dto.venta.promocion.PromocionCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.venta.promocion.PromocionCreateDTO;
import com.buenSabor.BackEnd.dto.venta.promocion.PromocionResponseDTO;
import com.buenSabor.BackEnd.dto.venta.promocion.PromocionUpdateDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.venta.Promocion;
import org.mapstruct.BeanMapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring", 
    config = mapperConfig.class, // Limpia el código de "ignores" y nulos
    uses = { 
        SucursalMapper.class, 
        TipoPromocionMapper.class, 
        PromocionArticuloMapper.class // Convierte la lista de detalles automáticamente
    }
)
public interface PromocionMapper extends BeanMapper<
    Promocion, 
    PromocionResponseDTO, 
    PromocionCreateDTO, 
    PromocionUpdateDTO, 
    PromocionCadenaSimpleDTO // Esto reemplaza a tu antiguo PromocionLiteDTO
> {

    // -----------------------------------------------------------------
    // 1. CREACIÓN (CreateDTO -> Entity)
    // -----------------------------------------------------------------

    @Override
    // Mapeamos las claves foráneas (IDs) hacia los objetos vacíos
    @Mapping(source = "sucursalId", target = "sucursal.id")
   
    Promocion toEntity(PromocionCreateDTO createDto);


    // -----------------------------------------------------------------
    // 2. ACTUALIZACIÓN (UpdateDTO -> Entity)
    // -----------------------------------------------------------------

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "sucursalId", target = "sucursal.id")
    void updateFromUpdateDto(PromocionUpdateDTO updateDto, @MappingTarget Promocion entity);


    // -----------------------------------------------------------------
    // 3. VISTA SIMPLE (Lite)
    // -----------------------------------------------------------------

    @Override
    // Reemplaza a tu antiguo 'toPromocionLiteDto'.
    // Ignoramos la lista de detalles para que sea ligero.
    @Mapping(target = "promocionArticuloList", ignore = true) 
    PromocionCadenaSimpleDTO toSimpleDto(Promocion entity);
}