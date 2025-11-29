package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.dto.producto.articulo.TopProductoDTO;
import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    
    @Query(value = """
        SELECT 
            a.nombre AS nombreArticulo,
            SUM(ventas.cantidad) AS cantidadVendida,
            SUM(ventas.cantidad * a.precio) AS totalRecaudado
        FROM (
            -- 1. Ventas Directas (Artículos sueltos)
            SELECT 
                dp.id_articulo AS id_articulo, 
                dp.cantidad AS cantidad
            FROM detalle_pedido dp
            JOIN pedido p ON dp.pedido_id = p.id
            WHERE p.id_sucursal = :sucursalId
            AND p.existe = true -- Solo pedidos válidos

            UNION ALL

            -- 2. Ventas por Promoción (Desglose de artículos dentro de la promo)
            SELECT 
                pa.id_articulo AS id_articulo, 
                (dprom.cantidad * pa.cantidad) AS cantidad
            FROM detalle_promocion dprom
            JOIN pedido p ON dprom.pedido_id = p.id
            JOIN promocion_articulo pa ON dprom.promocion_id = pa.id_promocion
            WHERE p.id_sucursal = :sucursalId
            AND p.existe = true
        ) AS ventas
        JOIN articulo a ON ventas.id_articulo = a.id
        GROUP BY a.id, a.nombre, a.precio
        ORDER BY cantidadVendida DESC
        """, nativeQuery = true)
    List<TopProductoDTO> obtenerTopProductosPorSucursal(
            @Param("sucursalId") Long sucursalId, 
            Pageable pageable);
}

