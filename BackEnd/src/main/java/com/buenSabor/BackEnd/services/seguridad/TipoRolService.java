package com.buenSabor.BackEnd.services.seguridad;

import com.buenSabor.BackEnd.dto.seguridad.tiporol.TipoRolDTO;
import com.buenSabor.BackEnd.mapper.TipoRolMapper;
import com.buenSabor.BackEnd.models.seguridad.TipoRol;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.seguridad.TipoRolRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoRolService extends BeanServiceImpl<TipoRol, Long> {

    private final TipoRolRepository tipoRolRepository;
    private final TipoRolMapper tipoRolMapper;

    @Autowired
    public TipoRolService(BeanRepository<TipoRol, Long> beanRepository,
            TipoRolRepository tipoRolRepository,
            TipoRolMapper tipoRolMapper) {
        super(beanRepository);
        this.tipoRolRepository = tipoRolRepository;
        this.tipoRolMapper = tipoRolMapper;
    }
    
    @Transactional
    public List<TipoRolDTO> findAllTipoRolesDTO() throws Exception {
        List<TipoRol> tipoRoles = tipoRolRepository.findAll();
        return tipoRolMapper.toDtoList(tipoRoles);
    }

   
    @Transactional
    public TipoRolDTO findTipoRolDTOById(Long id) throws Exception {
        return tipoRolRepository.findById(id)
                .map(tipoRolMapper::toDto)
                .orElse(null);
    }
}
