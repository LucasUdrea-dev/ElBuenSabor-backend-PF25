/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.articulo;

import com.buenSabor.BackEnd.dto.producto.medida.UnidadMedidaDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author oscarloha
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloCreateDTO {
    
    @NotBlank(message = "El nombre del articulo es obligatorio")
    protected String nombre;
   
    @NotBlank(message = "La descripcion del articulo es obligatorio")
    protected String descripcion;
    
    @NotNull(message = "El precio del articulo es obligatorio")
    protected Double precio;
   
    @NotNull(message = "La existencia del articulo es obligatoria")
    protected Boolean existe;
   
    @NotNull(message = "es para elaborar es obligatorio")
    protected Boolean esParaElaborar;
    
    @NotBlank(message = "La imagen del articulo es obligatorio")
    protected String imagenArticulo;

    @NotNull(message = "El id de la subcategoria es obligatorio")
    protected Long subcategoriaId;

    @NotNull(message = "La unidad de medida es obligatoria")
    protected UnidadMedidaDTO unidadMedida;

    
}
