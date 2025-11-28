package com.buenSabor.BackEnd.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockCheckResponse {
    private boolean hayStockSuficiente;
    private List<ProductoFaltante> productosFaltantes = new ArrayList<>();
    private String mensaje;

    public StockCheckResponse(boolean hayStockSuficiente, String mensaje) {
        this.hayStockSuficiente = hayStockSuficiente;
        this.mensaje = mensaje;
    }

    public void agregarProductoFaltante(Long id, String nombre, int cantidadFaltante) {
        this.productosFaltantes.add(new ProductoFaltante(id, nombre, cantidadFaltante));
    }

    @Getter
    @Setter
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductoFaltante {
        private Long id;
        private String nombre;
        private int cantidadFaltante;
    }
}
