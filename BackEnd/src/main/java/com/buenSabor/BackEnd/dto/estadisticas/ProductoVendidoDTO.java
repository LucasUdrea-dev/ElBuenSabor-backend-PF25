package com.buenSabor.BackEnd.dto.estadisticas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoVendidoDTO {
    private String nombre;
    private List<Integer> ventasDiarias;
    private List<Integer> ventasSemanales;
    private List<Integer> ventasMensuales;
}
