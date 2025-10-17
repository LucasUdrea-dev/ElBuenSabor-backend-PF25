package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloRepository extends BeanRepository<Articulo, Long> {
    List<Articulo> findByNombreContainingIgnoreCase(String nombre);

    List<Articulo> findBySubcategoria_Id(Long subcategoriaId);

    List<Articulo> findByExisteTrue();

    List<Articulo> findByEsParaElaborarTrue();

    List<Articulo> findBySubcategoria_IdAndExisteTrue(Long subcategoriaId);

    List<Articulo> findByEsParaElaborarTrueAndExisteTrue();

    List<Articulo> findByExisteTrueAndEsParaElaborarFalse();

    List<Articulo> findByEsParaElaborarFalse();
}
