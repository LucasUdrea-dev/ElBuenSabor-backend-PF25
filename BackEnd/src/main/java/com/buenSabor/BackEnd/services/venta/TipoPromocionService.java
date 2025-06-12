package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.dto.venta.tipopromo.TipoPromocionDTO;
import com.buenSabor.BackEnd.mapper.TipoPromocionMapper;
import com.buenSabor.BackEnd.models.venta.TipoPromocion;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.venta.TipoPromocionRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoPromocionService extends BeanServiceImpl<TipoPromocion, Long> {

    private final TipoPromocionRepository tipoPromocionRepository;
    private final TipoPromocionMapper tipoPromocionMapper;

    @Autowired
    public TipoPromocionService(BeanRepository<TipoPromocion, Long> beanRepository,
            TipoPromocionRepository tipoPromocionRepository,
            TipoPromocionMapper tipoPromocionMapper) {
        super(beanRepository); // Pass the base repository to the superclass constructor
        this.tipoPromocionRepository = tipoPromocionRepository;
        this.tipoPromocionMapper = tipoPromocionMapper;
    }

    @Transactional
    public List<TipoPromocionDTO> findAllTipoPromocionesDTO() {
        // Use the specific repository or super.findAll() if exposed
        List<TipoPromocion> tipoPromociones = tipoPromocionRepository.findAll();
        return tipoPromocionMapper.toDtoList(tipoPromociones);
    }

    @Transactional
    public TipoPromocionDTO findTipoPromocionDTOById(Long id) {
        // Use the specific repository or super.findById() if exposed
        Optional<TipoPromocion> tipoPromocion = tipoPromocionRepository.findById(id);
        return tipoPromocion.map(tipoPromocionMapper::toDto).orElse(null); // Returns null if not found
    }

 
}
