package com.buenSabor.BackEnd.dto.producto.categoria;

import com.buenSabor.BackEnd.models.producto.Subcategoria;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaConSubcategoriasDTO {

    private Long id;
    private String denominacion;
    private String imagen;
    private Boolean esParaElaborar;
    private List<Subcategoria> subcategorias;

}