/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.venta;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.enums.TypeDelivery;
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
@Table(name = "Tipo_Envio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoEnvio extends Bean {



    @Column(name = "nombre_envio")
    @Enumerated(EnumType.STRING)
    private TypeDelivery tipoDelivery;
    @OneToMany(mappedBy = "tipoEnvio", fetch = FetchType.EAGER)
        @JsonIgnore
    private List<Pedido> pedidoList;

    
}
