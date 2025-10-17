package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class StockArticuloInsumoService extends BeanServiceImpl<StockArticuloInsumo, Long> {

    public StockArticuloInsumoService(BeanRepository<StockArticuloInsumo, Long> beanRepository) {
        super(beanRepository);
    }

}
