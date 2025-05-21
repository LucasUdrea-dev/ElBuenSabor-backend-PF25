/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.models.venta;

import com.buenSabor.BackEnd.models.bean.Bean;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 * @author oscarloha
 */
@Entity
@Table(name = "Mercado_Pago")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MercadoPago  extends Bean{

   

    @Column(name = "mpMerchantOrderId")
    private Integer mpMerchantOrderId;
    @Column(name = "mpPreferenceId")
    private String mpPreferenceId;
    @Column(name = "mpPaymentType")
    private String mpPaymentType;
//    @JoinColumns({
//        @JoinColumn(name = "payment_type_id", referencedColumnName = "id"),
//        @JoinColumn(name = "payment_type_id", referencedColumnName = "id")})
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoPago tipoPago;

    
    
}
