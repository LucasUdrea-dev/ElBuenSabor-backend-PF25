/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.ubicacion.direccion;

import com.buenSabor.BackEnd.dto.ubicacion.ciudad.CiudadCreateDTO;
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
public class DireccionCreateDTO {

    @NotBlank(message = "El nombre de la calle es obligatorio")
    private String nombreCalle;

    @NotBlank(message = "La numeración es obligatoria")
    private String numeracion;

    @NotNull(message = "La latitud es obligatoria")
    private Double latitud;

    @NotNull(message = "La longitud es obligatoria")
    private Double longitud;

    private String alias;

    private String descripcionEntrega;

    @NotNull(message = "La ciudad es obligatoria")
    private CiudadCreateDTO ciudad;
}
