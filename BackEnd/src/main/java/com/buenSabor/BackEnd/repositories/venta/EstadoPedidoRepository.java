package com.buenSabor.BackEnd.repositories.venta;

import com.buenSabor.BackEnd.enums.TypeState;
import com.buenSabor.BackEnd.models.venta.EstadoPedido;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoPedidoRepository extends BeanRepository<EstadoPedido, Long> {
    boolean existsByNombreEstado(TypeState nombreEstado);

    public Object findById(int i);
}
