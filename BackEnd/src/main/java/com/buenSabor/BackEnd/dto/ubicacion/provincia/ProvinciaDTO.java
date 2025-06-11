/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.ubicacion.provincia;

import com.buenSabor.BackEnd.dto.ubicacion.pais.PaisDTO;
import lombok.Data;

/**
 *
 * @author oscarloha
 */
@Data
public class ProvinciaDTO {
    private Long id;
    private String nombre;
    private PaisDTO pais;
}