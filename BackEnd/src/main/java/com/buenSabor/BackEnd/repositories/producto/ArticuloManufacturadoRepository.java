package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.ArticuloManufacturado;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloManufacturadoRepository extends BeanRepository<ArticuloManufacturado, Long> {
    List<ArticuloManufacturado> findByNombreContainingIgnoreCase(String nombre);

    List<ArticuloManufacturado> findBySubcategoria_Id(Long subcategoriaId);

    List<ArticuloManufacturado> findByExisteTrue();

}
