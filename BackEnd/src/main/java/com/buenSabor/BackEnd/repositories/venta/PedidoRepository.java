package com.buenSabor.BackEnd.repositories.venta;

import com.buenSabor.BackEnd.models.venta.Pedido;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends BeanRepository<Pedido, Long> {

    public List<Pedido> findAllByExisteTrue();

    public Object findByIdAndExisteTrue(Long id);

    List<Pedido> findByFechaBetween(Date fechaInicio, Date fechaFin);

    List<Pedido> findBySucursal_IdAndFechaBetween(Long sucursalId, Date fechaInicio, Date fechaFin);

    List<Pedido> findByFecha(Date fecha);

    List<Pedido> findBySucursal_IdAndFecha(Long sucursalId, Date fecha);

    List<Pedido> findByUsuarioId(Long userId);


    // En tu interface PedidoRepository

    @Query("SELECT DISTINCT p FROM Pedido p " +
            "LEFT JOIN FETCH p.detallePedidoList dp " + // Traemos los detalles YA
            "LEFT JOIN FETCH dp.articulo a " + // Traemos los artículos YA
            "LEFT JOIN FETCH p.detallePromocionList dprom " + // Traemos promociones YA
            "LEFT JOIN FETCH dprom.promocion prom " +
            "LEFT JOIN FETCH prom.promocionArticuloList pa " +
            "LEFT JOIN FETCH pa.idArticulo " + // Artículos de la promo
            "WHERE p.fecha BETWEEN :fechaInicio AND :fechaFin " +
            "AND (:sucursalId IS NULL OR p.sucursal.id = :sucursalId)")
    List<Pedido> findPedidosWithDetails(
            @Param("sucursalId") Long sucursalId,
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin);
}
