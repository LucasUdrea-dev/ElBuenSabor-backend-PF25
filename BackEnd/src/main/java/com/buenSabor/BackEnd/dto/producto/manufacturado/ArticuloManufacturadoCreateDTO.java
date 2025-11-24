/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.manufacturado;

import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloCreateDTO;
import com.buenSabor.BackEnd.dto.producto.manufacturadodetalle.ArticuloManufacturadoDetalleCreateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author oscarloha
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloManufacturadoCreateDTO extends ArticuloCreateDTO{
    
    @NotBlank(message = "El tiempo estimado del articulo es obligatorio")
    private String tiempoEstimado;
    
    @NotBlank(message = "La preparacion del articulo es obligatorio")
    private String preparacion;
    
    @NotNull(message = "El detalle del articulo es obligatorio")
    private List<ArticuloManufacturadoDetalleCreateDTO> detalleInsumos = new ArrayList<>();
    
    @NotNull(message = "El id sucursal del articulo es obligatorio")
    private Long sucursalId;

    
}
