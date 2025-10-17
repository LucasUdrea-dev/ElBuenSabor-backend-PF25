package com.buenSabor.BackEnd.models.user;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Telefono")
public class Telefono extends Bean {

    @Column(name = "numero")
    private BigInteger numero;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Usuario usuario;

}
