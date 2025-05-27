/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.producto;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.models.empresa.Sucursal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @JoinColumn(name = "id_articulo_insumo", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private ArticuloInsumo idArticuloInsumo;

    @JoinColumn(name = "id_sucursal", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Sucursal idSucursal;

    @OneToMany(mappedBy = "idstockarticuloInsumo", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<HistoricoStockArticuloInsumo> historicoStockArticuloInsumoList;

    
}
