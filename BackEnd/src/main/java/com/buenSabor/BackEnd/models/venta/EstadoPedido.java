/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.venta;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.enums.TypeState;
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
@Table(name = "Estado_Pedido")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoPedido extends Bean {


    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_estado")
    private TypeState nombre_estado;
    @OneToMany(mappedBy = "estadoPedido", fetch = FetchType.EAGER)
    private List<Pedido> pedidoList;

    
}
