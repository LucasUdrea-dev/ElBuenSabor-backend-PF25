/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.producto;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ArticuloInsumo")
public class ArticuloInsumo extends Articulo {

    @Column(name = "precio_compra")
    private Double precioCompra;
    
   
    @OneToMany(mappedBy = "idArticuloInsumo", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<StockArticuloInsumo> stockArticuloInsumoList;

    @OneToMany(mappedBy = "idArticuloInsumo", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<HistoricoPrecioCostoArticuloInsumo> historicoPrecioCostoArticuloInsumoList;
    @OneToMany(mappedBy = "articuloInsumo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ArticuloManufacturadoDetalleInsumo> detalleManufacturas = new ArrayList<>();

}
