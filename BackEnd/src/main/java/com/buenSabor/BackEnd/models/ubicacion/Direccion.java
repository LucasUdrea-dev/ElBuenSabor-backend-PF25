/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.ubicacion;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.models.empresa.Sucursal;
import com.buenSabor.BackEnd.models.user.Usuario;
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
import jakarta.persistence.OneToOne;
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
@Table(name = "Direccion")
public class Direccion extends Bean {

    /*private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;*/

    @Column(name = "existe")
    protected Boolean existe;
    @Column(name = "nombre_calle")
    protected String nombreCalle;
    @Column(name = "numeracion")
    protected String numeracion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitud")
    protected Double latitud;
    @Column(name = "longitud")
    protected Double longitud;
    
    @ManyToOne
    @JoinColumn(name = "id_ciudad")
    protected Ciudad ciudad;

    
    @ManyToMany(mappedBy = "direccionList", fetch = FetchType.EAGER)
    @JsonIgnore
    protected List<Usuario> usuarioList;

  
    @OneToOne(mappedBy = "direccion", fetch = FetchType.EAGER)
    @JsonIgnore
    protected Sucursal sucursal;

   
}
