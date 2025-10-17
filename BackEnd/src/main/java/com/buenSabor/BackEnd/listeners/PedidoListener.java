package com.buenSabor.BackEnd.listeners;

import com.buenSabor.BackEnd.models.venta.Pedido;
import com.buenSabor.BackEnd.models.venta.HistoricoEstadoPedido;
import com.buenSabor.BackEnd.repositories.venta.HistoricoEstadoPedidoRepository;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.PostPersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PedidoListener {

    private static HistoricoEstadoPedidoRepository historicoRepository;

    @Autowired
    public void setHistoricoRepository(HistoricoEstadoPedidoRepository repository) {
        PedidoListener.historicoRepository = repository;
    }

    @PostPersist
    public void afterCreate(Pedido pedido) {
        if (pedido.getEstadoPedido() != null) {
            guardarHistoricoEstado(pedido);
        }
    }

    @PreUpdate
    public void beforeUpdate(Pedido pedido) {
        if (pedido.getEstadoPedido() != null && historicoRepository != null) {
            HistoricoEstadoPedido ultimoHistorico = 
                historicoRepository.findTopByPedidoOrderByFechaCambioDesc(pedido);
            
            if (ultimoHistorico == null || 
                !ultimoHistorico.getEstadoPedido().getId().equals(pedido.getEstadoPedido().getId())) {
                guardarHistoricoEstado(pedido);
            }
        }
    }

    private void guardarHistoricoEstado(Pedido pedido) {
        if (historicoRepository != null) {
            HistoricoEstadoPedido historico = new HistoricoEstadoPedido();
            historico.setFechaCambio(new Date());
            historico.setPedido(pedido);
            historico.setEstadoPedido(pedido.getEstadoPedido());
            historicoRepository.save(historico);
        }
    }
}
