/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.company;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import com.buenSabor.BackEnd.models.venta.Pedido;
import com.buenSabor.BackEnd.models.venta.Promocion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.buenSabor.BackEnd.models.user.Empleado;
import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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

  
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "hora_apertura")
    @Temporal(TemporalType.TIME)
    private String horaApertura;
    @Column(name = "hora_cierre")
    @Temporal(TemporalType.TIME)
    private String horaCierre;
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
