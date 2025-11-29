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
import jakarta.persistence.OneToOne;
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

    @OneToOne(mappedBy = "articuloInsumo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private StockArticuloInsumo stockArticuloInsumo;

    @OneToMany(mappedBy = "idArticuloInsumo", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<HistoricoPrecioCostoArticuloInsumo> historicoPrecioCostoArticuloInsumoList;

    @OneToMany(mappedBy = "articuloInsumo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ArticuloManufacturadoDetalleInsumo> detalleManufacturas = new ArrayList<>();

    
    public void setStockAndLink(StockArticuloInsumo stock) {
        this.stockArticuloInsumo = stock;
        if (stock != null) {
            stock.setArticuloInsumo(this); 
        }
    }
    
    
    @OneToMany(mappedBy = "idArticuloInsumo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<HistoricoPrecioCostoArticuloInsumo> historicoPrecioCosto = new ArrayList<>();
}
