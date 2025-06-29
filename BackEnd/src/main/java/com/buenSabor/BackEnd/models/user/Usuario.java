/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.user;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import com.buenSabor.BackEnd.models.venta.Pedido;
import com.buenSabor.BackEnd.models.seguridad.Rol;
import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@Table(name = "Usuario")
public class Usuario extends Bean {

   

    @Column(name = "nombre")
    protected String nombre;
    @Column(name = "apellido")
    protected String apellido;
    @Column(name = "email")
    protected String email;
    @Column(name = "existe")
    protected Boolean existe;
    protected String imagenUsuario;
    
    @OneToMany(mappedBy = "usuario",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    protected List<Telefono> telefonoList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_rol", referencedColumnName = "id")
    protected Rol rol;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Usuario_Direccion",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_direccion"))
    protected List<Direccion> direccionList;


    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_userAuth")
    protected UserAuthentication userAuthentication;


    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
        @JsonIgnore
    protected List<Pedido> pedidoList;
    
}
