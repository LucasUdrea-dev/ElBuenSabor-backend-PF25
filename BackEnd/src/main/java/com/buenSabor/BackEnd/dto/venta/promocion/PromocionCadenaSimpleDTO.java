/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.promocion;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.venta.promocionArticulo.PromocionArticuloCadenaSimpleDTO;
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
public class PromocionCadenaSimpleDTO {
    
    private Long id;
    
    private String denominacion;
   
    private String descripcion;
 
    private Double precioRebajado;
    
    private Boolean existe;
   
    private String imagen;

    private SucursalCadenaSimpleDTO sucursal;

    private TipoPromocionDTO TipoPromocion;

    private List<PromocionArticuloCadenaSimpleDTO> promocionArticuloList;

   

    
}
