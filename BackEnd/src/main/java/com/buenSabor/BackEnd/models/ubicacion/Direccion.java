/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.ubicacion;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.models.venta.DireccionPedido;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
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
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Direccion")
public class Direccion extends Bean {

    @Column(name = "nombre_calle")
    private String nombreCalle;
    @Column(name = "numeracion")
    private String numeracion;
    @Column(name = "latitud")
    private Double latitud;
    @Column(name = "longitud")
    private Double longitud;
    @Column(name = "alias")
    private String alias;

    @Column(name = "descripcion_entrega", columnDefinition = "TEXT")
    private String descripcionEntrega;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ciudad")
    private Ciudad ciudad;

    @ManyToMany(mappedBy = "direccionList", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Usuario> usuarioList;

    @OneToOne(mappedBy = "direccion", fetch = FetchType.EAGER)
    @JsonIgnore
    private Sucursal sucursal;

    @OneToMany(mappedBy = "direccion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DireccionPedido> direccionPedidos = new ArrayList<>();

}
