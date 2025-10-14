package com.buenSabor.BackEnd.dto.producto.articulo;

import com.buenSabor.BackEnd.dto.producto.medida.UnidadMedidaDTO;
import com.buenSabor.BackEnd.dto.producto.subcategoria.SubcategoriaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticuloDTO {

    private Long id;
    protected String nombre;
    protected String descripcion;
    protected Double precio;
    protected Boolean existe;
    protected Boolean esParaElaborar;
    protected String imagenArticulo;

    protected SubcategoriaDTO subcategoria;
    protected UnidadMedidaDTO unidadMedida;

    // Relaciones omitidas: detallePedidoList, historicoPrecioVentaArticuloList,
    // promocionArticuloList
}