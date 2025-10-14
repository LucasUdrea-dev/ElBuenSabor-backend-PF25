package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.models.producto.HistoricoPrecioCostoArticuloInsumo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HistoricoPrecioConstoArticuloInsumoService extends
        BeanServiceImpl<HistoricoPrecioCostoArticuloInsumo, Long> {

    public HistoricoPrecioConstoArticuloInsumoService(
            BeanRepository<HistoricoPrecioCostoArticuloInsumo, Long> beanRepository) {
        super(beanRepository);
    }

}
