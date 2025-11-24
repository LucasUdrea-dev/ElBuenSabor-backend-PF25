package com.buenSabor.BackEnd.dto.producto.articulo;

import com.buenSabor.BackEnd.dto.producto.manufacturadodetalle.ArticuloManufacturadoDetalleCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.producto.medida.UnidadMedidaDTO;
import com.buenSabor.BackEnd.dto.producto.subcategoria.SubcategoriaCadenaSimpleDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloSpecialResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Boolean existe;
    private Boolean esParaElaborar;
    private String imagenArticulo;

    private SubcategoriaCadenaSimpleDTO subcategoria;
    private UnidadMedidaDTO unidadMedida;

    // MANUFACTURADO
    private String tiempoEstimado;
    private List<ArticuloManufacturadoDetalleCadenaSimpleDTO> detalleInsumos;

}
