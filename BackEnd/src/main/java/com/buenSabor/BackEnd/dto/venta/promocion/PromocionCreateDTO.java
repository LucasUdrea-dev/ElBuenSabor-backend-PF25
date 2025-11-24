/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.promocion;

import com.buenSabor.BackEnd.dto.venta.promocionArticulo.PromocionArticuloCreateDTO;
import com.buenSabor.BackEnd.dto.venta.tipopromo.TipoPromocionDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
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
public class PromocionCreateDTO {
  
    @NotBlank(message = "La denominacion de la promocion es obligatorio")
    private String denominacion;
   
    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;
 
    @NotNull(message = "El precio rebajado es obligatorio")
    private Double precioRebajado;
    
    @NotNull(message = "La existencia es obligatoria")
    private Boolean existe;
   
    @NotNull(message = "La imagen es obligatoria")
    private String imagen;

    @NotNull(message = "El id de la sucursa les obligatorio")
    private Long sucursalId;

    @NotNull(message = "El tipo promocion es obligatorio")
    private TipoPromocionDTO TipoPromocion;
    
    @NotNull(message = "Una promocion-articulo detalle es obligatoria")
    private List<PromocionArticuloCreateDTO> promocionArticuloList = new ArrayList<>();


}
