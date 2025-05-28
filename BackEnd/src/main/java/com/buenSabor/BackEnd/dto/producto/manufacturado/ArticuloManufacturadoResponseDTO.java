/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.manufacturado;

import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloResponseDTO;
import com.buenSabor.BackEnd.dto.producto.insumo.ArticuloInsumoResponseDTO;
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
public class ArticuloManufacturadoResponseDTO extends ArticuloResponseDTO{
    

        private String tiempoEstimado;
        private String preparacion;
        
        private List<ArticuloInsumoResponseDTO>insumos;
}
