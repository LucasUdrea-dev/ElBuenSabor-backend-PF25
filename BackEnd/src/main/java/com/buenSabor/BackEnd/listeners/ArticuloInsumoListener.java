package com.buenSabor.BackEnd.listeners;

import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.HistoricoPrecioCostoArticuloInsumo;
import com.buenSabor.BackEnd.repositories.producto.HistoricoPrecioCostoArticuloInsumoRepository;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.PostPersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ArticuloInsumoListener {

    private static HistoricoPrecioCostoArticuloInsumoRepository historicoRepository;

    @Autowired
    public void setHistoricoRepository(HistoricoPrecioCostoArticuloInsumoRepository repository) {
        ArticuloInsumoListener.historicoRepository = repository;
    }

    @PostPersist
    public void afterCreate(ArticuloInsumo articulo) {
        if (articulo.getPrecioCompra() != null) {
            guardarHistoricoPrecio(articulo);
        }
    }

    @PreUpdate
    public void beforeUpdate(ArticuloInsumo articulo) {
        if (articulo.getPrecioCompra() != null && historicoRepository != null) {
            HistoricoPrecioCostoArticuloInsumo ultimoHistorico = 
                historicoRepository.findTopByIdArticuloInsumoOrderByFechaDesc(articulo);
            
            if (ultimoHistorico == null || 
                !ultimoHistorico.getPrecio().equals(articulo.getPrecioCompra())) {
                guardarHistoricoPrecio(articulo);
            }
        }
    }

    private void guardarHistoricoPrecio(ArticuloInsumo articulo) {
        if (historicoRepository != null) {
            HistoricoPrecioCostoArticuloInsumo historico = new HistoricoPrecioCostoArticuloInsumo();
            historico.setFecha(new Date());
            historico.setPrecio(articulo.getPrecioCompra());
            historico.setIdArticuloInsumo(articulo);
            historicoRepository.save(historico);
        }
    }
}
