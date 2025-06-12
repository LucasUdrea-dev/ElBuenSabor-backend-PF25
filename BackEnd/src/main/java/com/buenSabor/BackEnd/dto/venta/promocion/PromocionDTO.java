/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.promocion;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalDTO;
import com.buenSabor.BackEnd.dto.venta.promocionArticulo.PromocionArticuloDTO;
import com.buenSabor.BackEnd.dto.venta.tipopromo.TipoPromocionDTO;
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
@AllArgsConstructor
@NoArgsConstructor
public class PromocionDTO {

     private Long id;
    private String denominacion;
    private String descripcion;
    private Double precioRebajado;
    private Boolean existe;
    private String imagen;
    

    private SucursalDTO sucursal; 
    private TipoPromocionDTO tipoPromocion; 
    
    // Lista de DTOs para las relaciones 'ToMany'
    private List<PromocionArticuloDTO> articulos; // Asume que tienes un PromocionArticuloDto

}