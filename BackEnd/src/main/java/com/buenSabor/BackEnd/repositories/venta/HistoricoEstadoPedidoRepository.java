package com.buenSabor.BackEnd.repositories.venta;

import com.buenSabor.BackEnd.models.venta.HistoricoEstadoPedido;
import com.buenSabor.BackEnd.models.venta.Pedido;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoEstadoPedidoRepository extends BeanRepository<HistoricoEstadoPedido, Long> {
    
    HistoricoEstadoPedido findTopByPedidoOrderByFechaCambioDesc(Pedido pedido);
    
    List<HistoricoEstadoPedido> findByPedidoOrderByFechaCambioDesc(Pedido pedido);
    
    List<HistoricoEstadoPedido> findByPedido_IdOrderByFechaCambioDesc(Long pedidoId);
}
