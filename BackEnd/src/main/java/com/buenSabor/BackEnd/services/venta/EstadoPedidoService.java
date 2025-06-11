package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.models.venta.EstadoPedido;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EstadoPedidoService extends BeanServiceImpl<EstadoPedido, Long> {
    public EstadoPedidoService(BeanRepository<EstadoPedido, Long> beanRepository) {
        super(beanRepository);
    }
}
