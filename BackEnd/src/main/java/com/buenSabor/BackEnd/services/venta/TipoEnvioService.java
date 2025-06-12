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

    private final TipoEnvioRepository tipoEnvioRepository; // Still needed for specific queries or if BeanServiceImpl's find methods aren't exposed
    private final TipoEnvioMapper tipoEnvioMapper;

    @Autowired
    public TipoEnvioService(BeanRepository<TipoEnvio, Long> beanRepository, // For BeanServiceImpl
            TipoEnvioRepository tipoEnvioRepository, // For direct repository access
            TipoEnvioMapper tipoEnvioMapper) {             // For DTO conversions
        super(beanRepository); // Crucially call the super constructor
        this.tipoEnvioRepository = tipoEnvioRepository;
        this.tipoEnvioMapper = tipoEnvioMapper;
    }

  
    @Transactional
    public List<TipoEnvioDTO> findAllTipoEnviosDTO() {
        // You can use 'super.findAll()' if BeanServiceImpl exposes it, or 'tipoEnvioRepository.findAll()'
        List<TipoEnvio> tipoEnvios = tipoEnvioRepository.findAll();
        return tipoEnvioMapper.toDtoList(tipoEnvios);
    }

   
    @Transactional
    public TipoEnvioDTO findTipoEnvioDTOById(Long id) {
        // You can use 'super.findById(id)' if BeanServiceImpl exposes it, or 'tipoEnvioRepository.findById(id)'
        Optional<TipoEnvio> tipoEnvio = tipoEnvioRepository.findById(id);
        return tipoEnvio.map(tipoEnvioMapper::toDto).orElse(null);
    }
}
