package com.buenSabor.BackEnd.models.user;

import com.buenSabor.BackEnd.models.bean.Bean;
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

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    protected List<Telefono> telefonoList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "id_rol", referencedColumnName = "id")
    protected Rol rol;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    protected List<UsuarioDireccion> usuarioDireccionList = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_userAuth")
    @JsonIgnore
    protected UserAuthentication userAuthentication;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonIgnore
    protected List<Pedido> pedidoList;

}
