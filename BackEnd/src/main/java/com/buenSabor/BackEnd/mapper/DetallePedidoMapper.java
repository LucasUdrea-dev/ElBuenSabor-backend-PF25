package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.config.mapperConfig;
import com.buenSabor.BackEnd.dto.venta.detallepedido.DetallePedidoCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.venta.detallepedido.DetallePedidoCreateDTO;
import com.buenSabor.BackEnd.dto.venta.detallepedido.DetallePedidoResponseDTO;
import com.buenSabor.BackEnd.dto.venta.detallepedido.DetallePedidoUpdateDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.venta.DetallePedido;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring", 
    config = mapperConfig.class, 
    uses = { ArticuloMapper.class } 
)
public interface DetallePedidoMapper extends BeanMapper<
    DetallePedido, 
    DetallePedidoResponseDTO, 
    DetallePedidoCreateDTO, 
    DetallePedidoUpdateDTO, 
    DetallePedidoCadenaSimpleDTO
> {
 
}
