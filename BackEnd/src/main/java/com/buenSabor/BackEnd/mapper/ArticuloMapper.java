package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloDTO;
import com.buenSabor.BackEnd.dto.producto.insumo.InsumoDTO;
import com.buenSabor.BackEnd.dto.producto.manufacturado.ArticuloManufacturadoDTO;
import com.buenSabor.BackEnd.dto.producto.manufacturadodetalle.ArticuloManufacturadoDetalleInsumoDTO;
import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturado;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturadoDetalleInsumo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = { SubcategoriaMapper.class, UnidadMedidaMapper.class,
        StockArticuloInsumoMapper.class })
public interface ArticuloMapper {

    ArticuloMapper mapper = Mappers.getMapper(ArticuloMapper.class);

    // <--[Articulo articulo]--
    // ==>{ArticuloDTO dto, y lo que ignora *-*}
    @SubclassMapping(source = ArticuloInsumo.class, target = InsumoDTO.class)
    @SubclassMapping(source = ArticuloManufacturado.class, target = ArticuloManufacturadoDTO.class)
    @Mapping(target = "subcategoria", source = "subcategoria")
    @Mapping(target = "unidadMedida", source = "unidadMedida")
    ArticuloDTO toArticuloDTO(Articulo articulo);

    // <--[ArticuloInsumo entity]--
    // ==>{InsumoDTO dto, y lo que ignora *-*}
    @Mapping(target = "subcategoria", source = "subcategoria")
    @Mapping(target = "unidadMedida", source = "unidadMedida")
    @Mapping(target = "stockArticuloInsumo", source = "stockArticuloInsumo")
    InsumoDTO toInsumoDTO(ArticuloInsumo entity);

    // <--[ArticuloManufacturado entity]--
    // ==>{ArticuloManufacturadoDTO dto, y lo que ignora *-*}
    @Mapping(target = "subcategoria", source = "subcategoria")
    @Mapping(target = "unidadMedida", source = "unidadMedida")
    @Mapping(target = "insumos", ignore = true) // Mapeo manual para evitar ciclo
    @Mapping(target = "sucursalId", source = "sucursal.id")
    ArticuloManufacturadoDTO toArticuloManufacturadoDTO(ArticuloManufacturado entity);

    @AfterMapping
    default void mapInsumos(ArticuloManufacturado entity, @MappingTarget ArticuloManufacturadoDTO dto) {
        if (entity.getDetalleInsumos() != null && !entity.getDetalleInsumos().isEmpty()) {
            List<ArticuloManufacturadoDetalleInsumoDTO> insumosDTO = new ArrayList<>();
            for (ArticuloManufacturadoDetalleInsumo detalle : entity.getDetalleInsumos()) {
                ArticuloManufacturadoDetalleInsumoDTO detalleDTO = new ArticuloManufacturadoDetalleInsumoDTO();
                detalleDTO.setId(detalle.getId());
                detalleDTO.setCantidad(detalle.getCantidad());

                // Mapear el insumo manualmente
                if (detalle.getArticuloInsumo() != null) {
                    detalleDTO.setArticuloInsumo(toInsumoDTO(detalle.getArticuloInsumo()));
                }
                insumosDTO.add(detalleDTO);
            }
            dto.setInsumos(insumosDTO);
        }
    }

    // <--[ArticuloDTO dto]--
    // ==>{Articulo entity, y lo que ignora
    // *promocionArticuloList,historicoPrecioVentaArticuloList,detallePedidoList,id*}
    @SubclassMapping(source = InsumoDTO.class, target = ArticuloInsumo.class)
    @SubclassMapping(source = ArticuloManufacturadoDTO.class, target = ArticuloManufacturado.class)
    @Mapping(target = "promocionArticuloList", ignore = true)
    @Mapping(target = "historicoPrecioVentaArticuloList", ignore = true)
    @Mapping(target = "detallePedidoList", ignore = true)
    @Mapping(target = "subcategoria", source = "subcategoria")
    @Mapping(target = "unidadMedida", source = "unidadMedida")
    @Mapping(target = "id", ignore = true)
    Articulo toArticulo(ArticuloDTO dto);

    // <--[InsumoDTO dto]--
    // ==>{ArticuloInsumo entity, y lo que ignora
    // *detallePedidoList,historicoPrecioVentaArticuloList,promocionArticuloList,historicoPrecioCostoArticuloInsumoList,detalleManufacturas,id*}
    @Mapping(target = "detallePedidoList", ignore = true)
    @Mapping(target = "historicoPrecioVentaArticuloList", ignore = true)
    @Mapping(target = "promocionArticuloList", ignore = true)
    @Mapping(target = "historicoPrecioCostoArticuloInsumoList", ignore = true)
    @Mapping(target = "detalleManufacturas", ignore = true)
    @Mapping(target = "subcategoria", source = "subcategoria")
    @Mapping(target = "unidadMedida", source = "unidadMedida")
    @Mapping(target = "id", ignore = true)
    ArticuloInsumo toEntity(InsumoDTO dto);

    // <--[ArticuloManufacturadoDTO dto]--
    // ==>{ArticuloManufacturado entity, y lo que ignora
    // *detallePedidoList,historicoPrecioVentaArticuloList,promocionArticuloList,detalleInsumos,sucursal,id*}
    @Mapping(target = "detallePedidoList", ignore = true)
    @Mapping(target = "historicoPrecioVentaArticuloList", ignore = true)
    @Mapping(target = "promocionArticuloList", ignore = true)
    @Mapping(target = "detalleInsumos", ignore = true)
    @Mapping(target = "sucursal", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subcategoria", source = "subcategoria")
    @Mapping(target = "unidadMedida", source = "unidadMedida")
    ArticuloManufacturado toEntity(ArticuloManufacturadoDTO dto);

    // <--[ArticuloDTO dto, Articulo entity]--
    // ==>{void, y lo que ignora
    // *id,promocionArticuloList,historicoPrecioVentaArticuloList,detallePedidoList*}
    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "promocionArticuloList", ignore = true)
    @org.mapstruct.Mapping(target = "historicoPrecioVentaArticuloList", ignore = true)
    @org.mapstruct.Mapping(target = "detallePedidoList", ignore = true)
    @org.mapstruct.Mapping(target = "subcategoria", source = "subcategoria")
    @org.mapstruct.Mapping(target = "unidadMedida", source = "unidadMedida")
    void updateFromDto(ArticuloDTO dto, @org.mapstruct.MappingTarget Articulo entity);

    // <--[InsumoDTO dto, ArticuloInsumo entity]--
    // ==>{void, y lo que ignora
    // *id,detallePedidoList,historicoPrecioVentaArticuloList,promocionArticuloList,historicoPrecioCostoArticuloInsumoList,detalleManufacturas*}
    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "detallePedidoList", ignore = true)
    @org.mapstruct.Mapping(target = "historicoPrecioVentaArticuloList", ignore = true)
    @org.mapstruct.Mapping(target = "promocionArticuloList", ignore = true)
    @org.mapstruct.Mapping(target = "historicoPrecioCostoArticuloInsumoList", ignore = true)
    @org.mapstruct.Mapping(target = "detalleManufacturas", ignore = true)
    @org.mapstruct.Mapping(target = "subcategoria", source = "subcategoria")
    @org.mapstruct.Mapping(target = "unidadMedida", source = "unidadMedida")
    @org.mapstruct.Mapping(target = "stockArticuloInsumo", ignore = true)
    void updateFromDto(InsumoDTO dto, @org.mapstruct.MappingTarget ArticuloInsumo entity);

    // <--[ArticuloManufacturadoDTO dto, ArticuloManufacturado entity]--
    // ==>{void, y lo que ignora
    // *id,detallePedidoList,historicoPrecioVentaArticuloList,promocionArticuloList,detalleInsumos,sucursal*}
    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "detallePedidoList", ignore = true)
    @org.mapstruct.Mapping(target = "historicoPrecioVentaArticuloList", ignore = true)
    @org.mapstruct.Mapping(target = "promocionArticuloList", ignore = true)
    @org.mapstruct.Mapping(target = "detalleInsumos", ignore = true)
    @org.mapstruct.Mapping(target = "sucursal", ignore = true)
    @org.mapstruct.Mapping(target = "subcategoria", source = "subcategoria")
    @org.mapstruct.Mapping(target = "unidadMedida", source = "unidadMedida")
    void updateFromDto(ArticuloManufacturadoDTO dto, @org.mapstruct.MappingTarget ArticuloManufacturado entity);
}
