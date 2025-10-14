package com.buenSabor.BackEnd.models.venta;

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
@Table(name = "Historico_Estado_Pedido")
public class HistoricoEstadoPedido extends Bean {

    @Column(name = "fecha_cambio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCambio;

    @JoinColumn(name = "id_pedido", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Pedido pedido;

    @JoinColumn(name = "id_estado_pedido", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private EstadoPedido estadoPedido;

    @Column(name = "observaciones", length = 500)
    private String observaciones;
}
