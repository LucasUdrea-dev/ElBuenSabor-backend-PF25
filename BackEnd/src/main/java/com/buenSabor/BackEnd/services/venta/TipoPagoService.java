package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.dto.venta.pago.TipoPagoDTO;
import com.buenSabor.BackEnd.mapper.TipoPagoMapper;
import com.buenSabor.BackEnd.models.venta.TipoPago;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.venta.TipoPagoRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoPagoService extends BeanServiceImpl<TipoPago, Long> {

    
    private final TipoPagoRepository tipoPagoRepository;
    private final TipoPagoMapper tipoPagoMapper;

    @Autowired 
    public TipoPagoService(BeanRepository<TipoPago, Long> beanRepository, 
            TipoPagoRepository tipoPagoRepository, 
            TipoPagoMapper tipoPagoMapper) {          
        super(beanRepository);
        this.tipoPagoRepository = tipoPagoRepository;
        this.tipoPagoMapper = tipoPagoMapper;
    }

    @Transactional 
    public List<TipoPagoDTO> findAllTipoPagosDTO() {
        List<TipoPago> tipoPagos = tipoPagoRepository.findAll();
        return tipoPagoMapper.toDtoList(tipoPagos);
    }

    @Transactional 
    public TipoPagoDTO findTipoPagoDTOById(Long id) {
        Optional<TipoPago> tipoPago = tipoPagoRepository.findById(id);
        return tipoPago.map(tipoPagoMapper::toDto).orElse(null);
    }

   
    
}
