package com.buenSabor.BackEnd.models.venta;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "Promocion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Promocion extends Bean {

    @Column(name = "denominacion")
    private String denominacion;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "precio_rebajado")
    private Double precioRebajado;
    @Column(name = "existe")
    private Boolean existe;
    @Column(name = "imagen_promo")
    private String imagen;

    @JoinColumn(name = "sucursal_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sucursal sucursal;

    @JoinColumn(name = "id_tipo_promocion", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoPromocion TipoPromocion;

    @OneToMany(mappedBy = "idPromocion", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromocionArticulo> promocionArticuloList;

    @OneToMany(mappedBy = "promocion", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DetallePromocion> detallePromocionList = new ArrayList<>();

}