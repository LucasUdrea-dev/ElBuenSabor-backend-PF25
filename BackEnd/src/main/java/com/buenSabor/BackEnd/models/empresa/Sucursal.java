/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.empresa;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import com.buenSabor.BackEnd.models.venta.Pedido;
import com.buenSabor.BackEnd.models.venta.Promocion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.buenSabor.BackEnd.models.user.Empleado;
import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author oscarloha
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Sucursal")
public class Sucursal extends Bean {

    /*private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;*/
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "hora_apertura")
    @Temporal(TemporalType.TIME)
    private Date horaApertura;
    @Column(name = "hora_cierre")
    @Temporal(TemporalType.TIME)
    private Date horaCierre;
    @Column(name = "existe")
    private Boolean existe;
    
    
    //----
    @OneToMany(mappedBy = "idSucursal", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Empleado> empleadoList;

    @OneToMany(mappedBy = "idSucursal", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<StockArticuloInsumo> stockArticuloInsumoList;

    @OneToMany(mappedBy = "sucursal", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Pedido> pedidoList;

    @OneToOne(fetch = FetchType.EAGER)
    private Direccion direccion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Empresa empresa;

    @OneToMany(mappedBy = "sucursalId", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Promocion> promocionList;

}
