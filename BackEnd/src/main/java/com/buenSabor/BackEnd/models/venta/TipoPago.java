/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.venta;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.enums.TypePay;
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
@Table(name = "Tipo_Pago")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoPago extends Bean {



    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_pago")
    private TypePay tipoPago;
    @OneToMany(mappedBy = "tipoPago", fetch = FetchType.EAGER)
    private List<Pedido> pedidoList;
    @OneToMany(mappedBy = "tipoPago", fetch = FetchType.EAGER)
    private List<MercadoPago> mercadoPagoList;


    
}
