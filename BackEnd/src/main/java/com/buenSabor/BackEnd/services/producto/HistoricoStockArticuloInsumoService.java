package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.models.producto.HistoricoStockArticuloInsumo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HistoricoStockArticuloInsumoService extends
        BeanServiceImpl<HistoricoStockArticuloInsumo, Long> {

    public HistoricoStockArticuloInsumoService(BeanRepository<HistoricoStockArticuloInsumo, Long> beanRepository) {
        super(beanRepository);
    }

}
