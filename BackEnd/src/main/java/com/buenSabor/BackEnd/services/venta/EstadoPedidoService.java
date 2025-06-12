package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.dto.venta.estado.EstadoPedidoDTO;
import com.buenSabor.BackEnd.mapper.EstadoPedidoMapper;
import com.buenSabor.BackEnd.models.venta.EstadoPedido;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.venta.EstadoPedidoRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.Locked.Read;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoPedidoService extends BeanServiceImpl<EstadoPedido, Long> {

    private final EstadoPedidoRepository estadoPedidoRepository;
    private final EstadoPedidoMapper estadoPedidoMapper; 

    @Autowired
    public EstadoPedidoService(BeanRepository<EstadoPedido, Long> beanRepository, 
            EstadoPedidoRepository estadoPedidoRepository, 
            EstadoPedidoMapper estadoPedidoMapper) {         
        super(beanRepository); 
        this.estadoPedidoRepository = estadoPedidoRepository;
        this.estadoPedidoMapper = estadoPedidoMapper;
    }

   
    @Transactional
    public List<EstadoPedidoDTO> findAllEstadoPedidosDTO() {
     
        List<EstadoPedido> estadoPedidos = estadoPedidoRepository.findAll();
        return estadoPedidoMapper.toDtoList(estadoPedidos);
    }

   
    @Transactional
    public EstadoPedidoDTO findEstadoPedidoDTOById(Long id) {
    
        Optional<EstadoPedido> estadoPedido = estadoPedidoRepository.findById(id);
        return estadoPedido.map(estadoPedidoMapper::toDto).orElse(null);
    }
}
