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
import jakarta.persistence.Version;
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

    @Column(name = "min_stock")
    private Integer minStock;

    @Column(name = "cantidad")
    private Integer cantidad;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_articulo_insumo", referencedColumnName = "id")
    @JsonIgnore
    private ArticuloInsumo articuloInsumo;

    @ManyToOne
    @JoinColumn(name = "sucursal_id") 
    private Sucursal sucursal;

    @OneToMany(mappedBy = "idstockarticuloInsumo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<HistoricoStockArticuloInsumo> historicoStockArticuloInsumoList;

    @Version
    @Column(name = "version")
    private Long version;

    public synchronized boolean actualizarStock(int cantidad) {
        if (this.cantidad + cantidad < 0) {
            return false; // No hay suficiente stock
        }
        this.cantidad += cantidad;
        return true;
    }

    public boolean tieneSuficienteStock(int cantidadRequerida) {
        return this.cantidad >= cantidadRequerida;
    }
}
