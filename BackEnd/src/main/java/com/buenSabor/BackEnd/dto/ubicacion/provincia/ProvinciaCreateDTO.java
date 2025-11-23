/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.ubicacion.provincia;

import com.buenSabor.BackEnd.dto.ubicacion.pais.PaisCreateDTO;
import jakarta.validation.constraints.NotBlank;
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
public class ProvinciaCreateDTO {
    
      @NotBlank(message = "El nombre de la provincia es obligatorio")
      private String nombre;
      
      private PaisCreateDTO pais;
}
