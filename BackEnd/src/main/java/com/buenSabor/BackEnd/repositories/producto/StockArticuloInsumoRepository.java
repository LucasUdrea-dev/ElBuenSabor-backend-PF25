package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockArticuloInsumoRepository extends BeanRepository<StockArticuloInsumo, Long> {

    Optional<StockArticuloInsumo> findByArticuloInsumoId(Long articuloInsumoId);

    List<StockArticuloInsumo> findBySucursalId(Long sucursalId);

    @Query("SELECT s FROM StockArticuloInsumo s WHERE s.articuloInsumo.nombre LIKE %:nombre%")
    Page<StockArticuloInsumo> findByArticuloNombreContaining(
            @Param("nombre") String nombre,
            Pageable pageable);

    @Query("SELECT s FROM StockArticuloInsumo s WHERE s.sucursal.id = :sucursalId AND s.articuloInsumo.nombre LIKE %:nombre%")
    Page<StockArticuloInsumo> findBySucursalAndArticuloNombreContaining(
            @Param("sucursalId") Long sucursalId,
            @Param("nombre") String nombre,
            Pageable pageable);
}
