/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.promocion;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalResponseDTO;
import com.buenSabor.BackEnd.dto.venta.promocionArticulo.PromocionArticuloResponseDTO;
import com.buenSabor.BackEnd.dto.venta.tipopromo.TipoPromocionDTO;
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
public class PromocionResponseDTO {
    
    private Long id;
    
    private String denominacion;
   
    private String descripcion;
 
    private Double precioRebajado;
    
    private Boolean existe;
   
    private String imagen;

    private SucursalResponseDTO sucursal;

    private TipoPromocionDTO TipoPromocion;

    private List<PromocionArticuloResponseDTO> promocionArticuloList;


    
}
