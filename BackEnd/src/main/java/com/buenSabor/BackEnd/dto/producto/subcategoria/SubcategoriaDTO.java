package com.buenSabor.BackEnd.dto.producto.subcategoria;

import com.buenSabor.BackEnd.dto.producto.categoria.CategoriaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubcategoriaDTO {

    private Long id;
    private String denominacion;
    private CategoriaDTO categoria;

}
