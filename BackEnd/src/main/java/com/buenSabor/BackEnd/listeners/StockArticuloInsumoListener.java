package com.buenSabor.BackEnd.listeners;

import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.HistoricoStockArticuloInsumo;
import com.buenSabor.BackEnd.repositories.producto.HistoricoStockArticuloInsumoRepository;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.PostPersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StockArticuloInsumoListener {

    private static HistoricoStockArticuloInsumoRepository historicoRepository;

    @Autowired
    public void setHistoricoRepository(HistoricoStockArticuloInsumoRepository repository) {
        StockArticuloInsumoListener.historicoRepository = repository;
    }

    @PostPersist
    public void afterCreate(StockArticuloInsumo stock) {
        guardarHistoricoStock(stock);
    }

    @PreUpdate
    public void beforeUpdate(StockArticuloInsumo stock) {
        if (historicoRepository != null) {
            HistoricoStockArticuloInsumo ultimoHistorico = 
                historicoRepository.findTopByIdstockarticuloInsumoOrderByFechaActualizacionDesc(stock);
            
            if (ultimoHistorico == null || 
                !ultimoHistorico.getCantidad().equals(stock.getCantidad())) {
                guardarHistoricoStock(stock);
            }
        }
    }

    private void guardarHistoricoStock(StockArticuloInsumo stock) {
        if (historicoRepository != null) {
            HistoricoStockArticuloInsumo historico = new HistoricoStockArticuloInsumo();
            historico.setCantidad(stock.getCantidad());
            historico.setFechaActualizacion(new Date());
            historico.setIdstockarticuloInsumo(stock);
            historicoRepository.save(historico);
        }
    }
}
