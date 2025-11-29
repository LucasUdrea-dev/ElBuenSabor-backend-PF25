package com.buenSabor.BackEnd.models.producto;

import com.buenSabor.BackEnd.models.bean.Bean;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Historico_Stock_ArticuloInsumo")
public class HistoricoStockArticuloInsumo extends Bean {

    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "fecha_actualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    @JoinColumn(name = "id_stock_articuloInsumo", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private StockArticuloInsumo idstockarticuloInsumo;

}
