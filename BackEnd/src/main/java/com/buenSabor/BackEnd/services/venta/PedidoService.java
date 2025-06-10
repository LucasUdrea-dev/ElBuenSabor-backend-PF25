package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.models.venta.Pedido;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PedidoService extends BeanServiceImpl<Pedido, Long> {
    public PedidoService(BeanRepository<Pedido, Long> beanRepository) {
        super(beanRepository);
    }
}
