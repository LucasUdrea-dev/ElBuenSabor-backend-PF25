/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.promocion;

import com.buenSabor.BackEnd.dto.venta.promodetalle.PromoArticuloCreateDTO;
import com.buenSabor.BackEnd.dto.venta.tipopromo.TipoPromoCreateDTO;
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
public class PromoCreateDTO {
    
    
    private String denominacion;
    private String fechaDesde;
    private String fechaHasta;
    private String horaDesde;
    private String horaHasta;
    private String descripcion;
    private Double precioRebajado;
    private Boolean existe;
    
    private Long sucursalId;
    private TipoPromoCreateDTO tipoPromo;
    private List<PromoArticuloCreateDTO>detallePromo;
    
    
    
}
