/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.venta;

import com.buenSabor.BackEnd.listeners.PedidoListener;
import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.user.Usuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Pedido")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(PedidoListener.class)
public class Pedido extends Bean {

    @Column(name = "tiempo_estimado")
    private String tiempoEstimado;

    @Column(name = "existe")
    private Boolean existe;

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DetallePedido> detallePedidoList = new ArrayList<>();

    @JoinColumn(name = "id_estado_pedido", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private EstadoPedido estadoPedido;

    @JoinColumn(name = "id_sucursal", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Sucursal sucursal;

    @JoinColumn(name = "id_tipo_envio", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoEnvio tipoEnvio;

    @JoinColumn(name = "id_tipo_pago", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoPago tipoPago;

    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetallePromocion> detallePromocionList = new ArrayList<>();

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private DireccionPedido direccionPedido;

}
