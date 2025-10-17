package com.buenSabor.BackEnd.models.producto;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "categoria")
public class Categoria extends Bean {

    @Column(name = "denominacion")
    private String denominacion;
    private String imagen;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Subcategoria> subcategorias;

    private boolean esParaElaborar;

}
