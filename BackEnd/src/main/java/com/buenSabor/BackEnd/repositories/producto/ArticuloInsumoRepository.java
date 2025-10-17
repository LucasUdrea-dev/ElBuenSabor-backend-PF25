package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloInsumoRepository extends BeanRepository<ArticuloInsumo, Long> {

    List<ArticuloInsumo> findByNombreContainingIgnoreCase(String nombre);

    List<ArticuloInsumo> findBySubcategoria_Id(Long subcategoriaId);

    List<ArticuloInsumo> findByExisteTrue();

}
