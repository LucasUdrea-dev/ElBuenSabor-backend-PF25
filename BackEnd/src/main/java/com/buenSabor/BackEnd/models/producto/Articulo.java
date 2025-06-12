/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.producto;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.models.venta.DetallePedido;
import com.buenSabor.BackEnd.models.venta.Pedido;
import com.buenSabor.BackEnd.models.venta.PromocionArticulo;
import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Articulo")
public class Articulo extends Bean {

   

    @Column(name = "nombre")
    protected String nombre;
    @Column(name = "descripcion")
    protected String descripcion;
    @Column(name = "precio")
    protected Double precio;
    @Column(name = "existe")
    protected Boolean existe;
    @Column(name = "es_para_elaborar")
    protected Boolean esParaElaborar;
    protected String imagenArticulo;
    
    
    @OneToMany(mappedBy = "articulo", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<DetallePedido> detallePedidoList = new ArrayList<>();

    @OneToMany(mappedBy = "idArticulo", fetch = FetchType.EAGER)
    @JsonIgnore
    protected List<HistoricoPrecioVentaArticulo> historicoPrecioVentaArticuloList;

    @ManyToOne(fetch = FetchType.EAGER)
    protected Subcategoria subcategoria;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    protected UnidadMedida unidadMedida;
    @OneToMany(mappedBy = "idArticulo", fetch = FetchType.EAGER)
    @JsonIgnore
    protected List<PromocionArticulo> promocionArticuloList;


   
    
}
