package com.buenSabor.BackEnd.services.base;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<E extends Bean, ID extends Serializable> implements BaseService<E, ID> {
    protected BeanRepository<E, ID> beanRepository;

    public BaseServiceImpl(BeanRepository<E, ID> beanRepository) {
        this.beanRepository = beanRepository;
    }

    @Override
    @Transactional
    public List<E> findAll() throws Exception {
        try {
            return beanRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Page<E> findAll(Pageable pageable) throws Exception {
        try {
            return beanRepository.findAll(pageable);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E findById(ID id) throws Exception {
        try {
            Optional<E> entityOptional = beanRepository.findById(id);
            return entityOptional.orElseThrow(() -> new Exception("No se encontró la entidad con el id " + id));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E save(E entity) throws Exception {
        try {
            return beanRepository.save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E update(ID id, E entity) throws Exception {
        try {
            if (!beanRepository.existsById(id)) {
                throw new Exception("No se encontró la entidad con el id " + id);
            }
            return beanRepository.save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(ID id) throws Exception {
        try {
            if (beanRepository.existsById(id)) {
                beanRepository.deleteById(id);
                return true;
            } else {
                throw new Exception("No se encontró la entidad con el id " + id);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
