/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.producto;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "Stock_ArticuloInsumo")
public class StockArticuloInsumo extends Bean {

 

    @Column(name = "max_stock")
    private Integer maxStock;

    @Column(name = "cantidad")
    private Integer cantidad;

      
       
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_articulo_insumo", referencedColumnName = "id")
     @JsonIgnore
    private ArticuloInsumo articuloInsumo; 
        

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Sucursal Sucursal;

    @OneToMany(mappedBy = "idstockarticuloInsumo", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<HistoricoStockArticuloInsumo> historicoStockArticuloInsumoList;

    
}
