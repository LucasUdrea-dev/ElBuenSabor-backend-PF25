/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.venta;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.models.empresa.Sucursal;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.models.producto.Articulo;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author oscarloha
 */
@Entity
@Table(name = "Pedido")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido extends Bean {

    /*private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;*/

    @Column(name = "tiempo_estimado")
    @Temporal(TemporalType.TIME)
    private Date tiempoEstimado;

    @Column(name = "existe")
    private Boolean existe;

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @JoinTable(name = "Detalle_Pedido",
    joinColumns = @JoinColumn(name = "id_pedido", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "id_articulo", referencedColumnName = "id"))
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Articulo> articuloList;

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
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

/*
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.buenSabor.BackEnd.models.direccion.Pedido[ id=" + id + " ]";
    }
    */

}
