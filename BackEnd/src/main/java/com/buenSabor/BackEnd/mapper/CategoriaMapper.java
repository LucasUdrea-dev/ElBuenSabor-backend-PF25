package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.producto.categoria.CategoriaDTO;
import com.buenSabor.BackEnd.models.producto.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    // <--[CategoriaDTO dto]--
    // ==>{Categoria entity, y lo que ignora *id,subcategorias*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subcategorias", ignore = true)
    Categoria toEntity(CategoriaDTO dto);

    // <--[Categoria entity]--
    // ==>{CategoriaDTO dto, y lo que ignora *-*}
    CategoriaDTO toDTO(Categoria entity);

    // <--[CategoriaDTO dto, Categoria entity]--
    // ==>{void, y lo que ignora *id,subcategorias*}
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subcategorias", ignore = true)
    void updateFromDto(CategoriaDTO dto, @org.mapstruct.MappingTarget Categoria entity);
    
//    {
//  "id": 0,
//  "tiempoEstimado": "00:45:00",
//  "existe": true,
//  "fecha": "2025-11-28T21:14:49.134Z",
//  "estadoPedido": {
//    "id": 1 
//  },
//  "sucursal": {
//    "id": 1
//  },
//  "usuario": {
//    "id": 23
//  },
//  "tipoEnvio": {
//    "id": 1
//  },
//  "tipoPago": {
//    "id": 1
//  },
//  "detallePedidoList": [
//    {
//      "articulo": {
//        "id": 1
//      },
//      "cantidad": 1
//    },
//    {
//      "articulo": {
//        "id": 2
//      },
//      "cantidad": 2
//    }
//  ],
//  "detallePromocionList": [
//    {
//      "promocion": {
//        "id": 1
//      },
//      "cantidad": 1
//    }
//  ],
//  "direccion": {
//    "direccion": {
//      "nombreCalle": "Calle Falsa",
//      "numeracion": "123",
//      "latitud": -32.889,
//      "longitud": -68.845,
//      "alias": "Casa",
//      "descripcionEntrega": "Portón negro",
//      "ciudad": {
//        "id": 1
//      }
//    }
//  }
//}
}
