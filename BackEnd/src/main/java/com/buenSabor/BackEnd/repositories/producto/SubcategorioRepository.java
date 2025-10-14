package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.Subcategoria;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategorioRepository extends BeanRepository<Subcategoria, Long> {

    List<Subcategoria> findByDenominacionContainingIgnoreCase(String nombre);

    @Query("select sc from Subcategoria sc " +
            "Join sc.categoria c " +
            "where lower(c.denominacion) LIKE lower(concat('%',:texto,'%')) ")
    List<Subcategoria> filtrarPorCategoria(@Param("texto") String nombre);

}
