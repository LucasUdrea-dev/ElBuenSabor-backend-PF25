/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.articulo;

import com.buenSabor.BackEnd.dto.producto.insumo.InsumoDTO;
import com.buenSabor.BackEnd.dto.producto.manufacturado.ArticuloManufacturadoDTO;
import com.buenSabor.BackEnd.dto.producto.medida.UnidadMedidaDTO;
import com.buenSabor.BackEnd.dto.producto.subcategoria.SubcategoriaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author oscarloha
 */
//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.NAME,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "tipo"
//)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = InsumoDTO.class, name = "insumo"),
//        @JsonSubTypes.Type(value = ArticuloManufacturadoDTO.class, name = "manufacturado")
//})
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
   
    // Relaciones omitidas: detallePedidoList, historicoPrecioVentaArticuloList, promocionArticuloList
}