package com.buenSabor.BackEnd.models.producto;

import com.buenSabor.BackEnd.listeners.ArticuloInsumoListener;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@EntityListeners(ArticuloInsumoListener.class)
public class ArticuloInsumo extends Articulo {

    @Column(name = "precio_compra")
    private Double precioCompra;

    @OneToOne(mappedBy = "articuloInsumo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private StockArticuloInsumo stockArticuloInsumo;

    @OneToMany(mappedBy = "idArticuloInsumo", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<HistoricoPrecioCostoArticuloInsumo> historicoPrecioCostoArticuloInsumoList;

    @OneToMany(mappedBy = "articuloInsumo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ArticuloManufacturadoDetalleInsumo> detalleManufacturas = new ArrayList<>();

}
