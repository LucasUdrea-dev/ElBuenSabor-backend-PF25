/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.venta;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.enums.TypePromotion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


@Entity
@Table(name = "Tipo_Promocion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoPromocion extends Bean {

 

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TypePromotion tipoPromocion;
    @OneToMany(mappedBy = "TipoPromocion", fetch = FetchType.EAGER)
        @JsonIgnore
    private List<Promocion> promocionList;

    
}
