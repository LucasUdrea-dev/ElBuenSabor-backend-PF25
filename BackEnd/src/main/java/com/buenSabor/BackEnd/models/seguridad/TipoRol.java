package com.buenSabor.BackEnd.models.seguridad;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.enums.TypeRol;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "Tipo_Rol")
public class TipoRol extends Bean {

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_rol")
    private TypeRol rol;
    @OneToMany(mappedBy = "tipoRol", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Rol> rolList;

}
