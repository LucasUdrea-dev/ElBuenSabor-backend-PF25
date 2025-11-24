package com.buenSabor.BackEnd.mapper;


import com.buenSabor.BackEnd.config.mapperConfig;
import com.buenSabor.BackEnd.dto.venta.pedido.PedidoCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.venta.pedido.PedidoCreateDTO;
import com.buenSabor.BackEnd.dto.venta.pedido.PedidoResponseDTO;
import com.buenSabor.BackEnd.dto.venta.pedido.PedidoUpdateDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.venta.Pedido;
import org.mapstruct.Mapper;


@Mapper(
    componentModel = "spring", 
    config = mapperConfig.class, 
    uses = {
        EstadoPedidoMapper.class,
        SucursalMapper.class,
        TipoEnvioMapper.class,
        TipoPagoMapper.class,
        UsuarioMapper.class, 
        DetallePedidoMapper.class,
        // DetallePromocionMapper.class 
    }
)
public interface PedidoMapper extends BeanMapper<
    Pedido, 
    PedidoResponseDTO, 
    PedidoCreateDTO, 
    PedidoUpdateDTO, 
    PedidoCadenaSimpleDTO
> {
   
}