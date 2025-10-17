package com.buenSabor.BackEnd.models.seguridad;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
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
@Table(name = "Rol")
public class Rol extends Bean {

    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Usuario> usuarioList = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)

    private TipoRol tipoRol;

}
