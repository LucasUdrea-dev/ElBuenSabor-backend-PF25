package com.buenSabor.BackEnd.repositories.venta;

import com.buenSabor.BackEnd.models.venta.Pedido;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends BeanRepository<Pedido, Long> {

    public List<Pedido> findAllByExisteTrue();

    public Object findByIdAndExisteTrue(Long id);

    List<Pedido> findByFechaBetween(Date fechaInicio, Date fechaFin);

    List<Pedido> findBySucursal_IdAndFechaBetween(Long sucursalId, Date fechaInicio, Date fechaFin);

    List<Pedido> findByFecha(Date fecha);

    List<Pedido> findBySucursal_IdAndFecha(Long sucursalId, Date fecha);
}
