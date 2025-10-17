package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.models.producto.HistoricoPrecioVentaArticulo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoPrecioVentaArticuloRepository extends
        BeanRepository<HistoricoPrecioVentaArticulo, Long> {

    HistoricoPrecioVentaArticulo findTopByIdArticuloOrderByFechaDesc(Articulo articulo);

    List<HistoricoPrecioVentaArticulo> findByIdArticuloOrderByFechaDesc(Articulo articulo);

    List<HistoricoPrecioVentaArticulo> findByIdArticulo_IdOrderByFechaDesc(Long articuloId);
}
