/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.venta;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.models.producto.Articulo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Promocion_Articulo")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PromocionArticulo extends Bean {


    private int cantidad; 
    @JoinColumn(name = "id_articulo", referencedColumnName = "id")
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Articulo articulo;
    @JoinColumn(name = "id_promocion", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Promocion promocion;

    
}
