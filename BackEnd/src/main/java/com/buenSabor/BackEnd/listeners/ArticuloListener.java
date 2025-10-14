package com.buenSabor.BackEnd.listeners;

import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.models.producto.HistoricoPrecioVentaArticulo;
import com.buenSabor.BackEnd.repositories.producto.HistoricoPrecioVentaArticuloRepository;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.PostPersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ArticuloListener {

    private static HistoricoPrecioVentaArticuloRepository historicoRepository;

    @Autowired
    public void setHistoricoRepository(HistoricoPrecioVentaArticuloRepository repository) {
        ArticuloListener.historicoRepository = repository;
    }

    @PostPersist
    public void afterCreate(Articulo articulo) {
        if (articulo.getPrecio() != null) {
            guardarHistoricoPrecio(articulo);
        }
    }

    @PreUpdate
    public void beforeUpdate(Articulo articulo) {
        if (articulo.getPrecio() != null && historicoRepository != null) {
            HistoricoPrecioVentaArticulo ultimoHistorico = 
                historicoRepository.findTopByIdArticuloOrderByFechaDesc(articulo);
            
            if (ultimoHistorico == null || 
                !ultimoHistorico.getPrecio().equals(articulo.getPrecio())) {
                guardarHistoricoPrecio(articulo);
            }
        }
    }

    private void guardarHistoricoPrecio(Articulo articulo) {
        if (historicoRepository != null) {
            HistoricoPrecioVentaArticulo historico = new HistoricoPrecioVentaArticulo();
            historico.setFecha(new Date());
            historico.setPrecio(articulo.getPrecio());
            historico.setIdArticulo(articulo);
            historicoRepository.save(historico);
        }
    }
}
