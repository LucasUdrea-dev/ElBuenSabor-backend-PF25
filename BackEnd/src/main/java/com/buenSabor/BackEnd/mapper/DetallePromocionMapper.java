package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.config.mapperConfig;
import com.buenSabor.BackEnd.dto.venta.detallepromocion.DetallePromocionCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.venta.detallepromocion.DetallePromocionCreateDTO;
import com.buenSabor.BackEnd.dto.venta.detallepromocion.DetallePromocionResponseDTO;
import com.buenSabor.BackEnd.dto.venta.detallepromocion.DetallePromocionUpdateDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.venta.DetallePromocion;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring", 
    config = mapperConfig.class, // Reemplaza la configuración manual de nulos
    uses = { ArticuloMapper.class } // Necesario para convertir el Articulo dentro del detalle
)
public interface DetallePromocionMapper extends BeanMapper<
    DetallePromocion, 
    DetallePromocionResponseDTO, 
    DetallePromocionCreateDTO, 
    DetallePromocionUpdateDTO, 
    DetallePromocionCadenaSimpleDTO
> {
}