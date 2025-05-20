/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.venta;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.models.producto.Articulo;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 * @author oscarloha
 */
@Entity
@Table(name = "Promocion_Articulo")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PromocionArticulo extends Bean {

    /*private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;*/

    @JoinColumn(name = "id_articulo", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Articulo idArticulo;
    @JoinColumn(name = "id_promocion", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Promocion idPromocion;

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
        if (!(object instanceof PromocionArticulo)) {
            return false;
        }
        PromocionArticulo other = (PromocionArticulo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.buenSabor.BackEnd.models.direccion.PromocionArticulo[ id=" + id + " ]";
    }*/
    
}
