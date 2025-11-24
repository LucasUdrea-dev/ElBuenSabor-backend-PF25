/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.categoria;

import com.buenSabor.BackEnd.dto.producto.subcategoria.SubcategoriaCreateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
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
public class CategoriaCreateDTO {
    
    @NotBlank(message = "La denominacion de la categoria es obligatorio")
    private String denominacion;
    
    @NotBlank(message = "La imagen de la categoria es obligatorio")
    private String imagen;
    
    private List<SubcategoriaCreateDTO> subcategorias;

    @NotNull(message = "es para elaborar es obligatorio")
    private boolean esParaElaborar;
    
}
