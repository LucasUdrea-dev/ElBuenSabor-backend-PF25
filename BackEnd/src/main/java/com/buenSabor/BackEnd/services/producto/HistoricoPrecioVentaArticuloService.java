package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.models.producto.HistoricoPrecioVentaArticulo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HistoricoPrecioVentaArticuloService extends
        BeanServiceImpl<HistoricoPrecioVentaArticulo, Long> {

    public HistoricoPrecioVentaArticuloService(BeanRepository<HistoricoPrecioVentaArticulo, Long> beanRepository) {
        super(beanRepository);
    }

}
