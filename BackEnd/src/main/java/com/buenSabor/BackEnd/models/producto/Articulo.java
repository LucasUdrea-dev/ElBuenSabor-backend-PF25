/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.producto;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.models.venta.Pedido;
import com.buenSabor.BackEnd.models.venta.PromocionArticulo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
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
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Articulo")
public class Articulo extends Bean {

    /*private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;*/

    @Column(name = "nombre")
    protected String nombre;
    @Column(name = "descripcion")
    protected String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    protected Double precio;
    @Column(name = "existe")
    protected Boolean existe;
    @Column(name = "es_para_elaborar")
    protected Boolean esParaElaborar;
    protected String imagenArticulo;
    
    
    @ManyToMany(mappedBy = "articuloList", fetch = FetchType.EAGER)
    @JsonIgnore
    protected List<Pedido> pedidoList;
    //
//    @OneToMany(mappedBy = "articulo", fetch = FetchType.EAGER)
//    private List<ArticuloManufacturado> articuloManufacturadoList;
    //
    @OneToMany(mappedBy = "idArticulo", fetch = FetchType.EAGER)
    @JsonIgnore
    protected List<HistoricoPrecioVentaArticulo> historicoPrecioVentaArticuloList;
    //
//    @OneToMany(mappedBy = "idArticulo", fetch = FetchType.EAGER)
//    private List<ArticuloInsumo> articuloInsumoList;

    @ManyToOne(fetch = FetchType.EAGER)
    protected Subcategoria subCategoria;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    protected UnidadMedida unidadMedida;
    @OneToMany(mappedBy = "idArticulo", fetch = FetchType.EAGER)
    @JsonIgnore
    protected List<PromocionArticulo> promocionArticuloList;


   
    
}
