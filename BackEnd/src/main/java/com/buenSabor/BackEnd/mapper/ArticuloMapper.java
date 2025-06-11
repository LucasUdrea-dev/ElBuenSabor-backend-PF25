/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloDTO;
import com.buenSabor.BackEnd.dto.producto.insumo.InsumoDTO;
import com.buenSabor.BackEnd.dto.producto.manufacturado.ArticuloManufacturadoDTO;
import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author oscarloha
 */
@Mapper(componentModel = "spring", uses = {DireccionMapper.class, SubcategoriaMapper.class, UnidadMedidaMapper.class, ArticuloManufacturadoDetalleInsumoMapper.class, StockArticuloInsumoMapper.class})
public interface ArticuloMapper {

    ArticuloMapper mapper = Mappers.getMapper(ArticuloMapper.class);

    @Mapping(target = "subcategoria", source = "subcategoria")
    @Mapping(target = "unidadMedida", source = "unidadMedida")
    ArticuloDTO toArticuloDTO(Articulo articulo);

    @SubclassMapping(source = InsumoDTO.class, target = ArticuloInsumo.class)
    @SubclassMapping(source = ArticuloManufacturadoDTO.class, target = ArticuloManufacturado.class)
    @Mapping(target = "promocionArticuloList", ignore = true)
    @Mapping(target = "historicoPrecioVentaArticuloList", ignore = true)
    @Mapping(target = "detallePedidoList", ignore = true)
    @Mapping(target = "subcategoria.id", source = "subcategoria.id")
    @Mapping(target = "subcategoria.categoria.id", source = "subcategoria.categoria.id")
    @Mapping(target = "unidadMedida.id", source = "unidadMedida.id")
    Articulo toArticulo(ArticuloDTO dto);

    @Mapping(target = "detallePedidoList", ignore = true)
    @Mapping(target = "historicoPrecioVentaArticuloList", ignore = true)
    @Mapping(target = "promocionArticuloList", ignore = true)
    @Mapping(target = "historicoPrecioCostoArticuloInsumoList", ignore = true)
    @Mapping(target = "detalleManufacturas", ignore = true)
    @Mapping(target = "subcategoria.id", source = "subcategoria.id")
    @Mapping(target = "subcategoria.categoria.id", source = "subcategoria.categoria.id")
    @Mapping(target = "unidadMedida.id", source = "unidadMedida.id")
    ArticuloInsumo toEntity(InsumoDTO dto);

    @Mapping(target = "detallePedidoList", ignore = true)
    @Mapping(target = "historicoPrecioVentaArticuloList", ignore = true)
    @Mapping(target = "promocionArticuloList", ignore = true)
    @Mapping(target = "detalleInsumos", ignore = true)
    @Mapping(target = "sucursal", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subcategoria", source = "subcategoria")
    @Mapping(target = "unidadMedida", source = "unidadMedida")
    ArticuloManufacturado toEntity(ArticuloManufacturadoDTO dto);

}
