package com.buenSabor.BackEnd.models.venta;

import com.buenSabor.BackEnd.models.bean.Bean;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Mercado_Pago")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MercadoPago extends Bean {

    @Column(name = "mpMerchantOrderId")
    private Integer mpMerchantOrderId;
    @Column(name = "mpPreferenceId")
    private String mpPreferenceId;
    @Column(name = "mpPaymentType")
    private String mpPaymentType;

    @ManyToOne(fetch = FetchType.EAGER)
    private TipoPago tipoPago;

}
