package com.buenSabor.BackEnd.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockCheckRequest {
    private List<ProductoCheck> productos;
    private Long sucursalId;
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductoCheck {
        private Long id;
        private int cantidad;
        private String tipo; // "INSUMO" o "MANUFACTURADO"
    }
}
