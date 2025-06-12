package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.dto.venta.envio.TipoEnvioDTO;
import com.buenSabor.BackEnd.mapper.TipoEnvioMapper;
import com.buenSabor.BackEnd.models.venta.TipoEnvio;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.venta.TipoEnvioRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoEnvioService extends BeanServiceImpl<TipoEnvio, Long> {

    private final TipoEnvioRepository tipoEnvioRepository;
    private final TipoEnvioMapper tipoEnvioMapper;

    @Autowired
    public TipoEnvioService(BeanRepository<TipoEnvio, Long> beanRepository, 
            TipoEnvioRepository tipoEnvioRepository, 
            TipoEnvioMapper tipoEnvioMapper) {           
        super(beanRepository);
        this.tipoEnvioRepository = tipoEnvioRepository;
        this.tipoEnvioMapper = tipoEnvioMapper;
    }

  
    @Transactional
    public List<TipoEnvioDTO> findAllTipoEnviosDTO() {
      
        List<TipoEnvio> tipoEnvios = tipoEnvioRepository.findAll();
        return tipoEnvioMapper.toDtoList(tipoEnvios);
    }

   
    @Transactional
    public TipoEnvioDTO findTipoEnvioDTOById(Long id) {
      
        Optional<TipoEnvio> tipoEnvio = tipoEnvioRepository.findById(id);
        return tipoEnvio.map(tipoEnvioMapper::toDto).orElse(null);
    }
}
