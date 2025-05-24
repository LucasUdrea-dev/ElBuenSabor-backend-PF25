package com.buenSabor.BackEnd.services.bean;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BeanServiceImpl<E extends Bean, ID extends Serializable> implements BeanService<E, ID> {
    protected BeanRepository<E,ID> beanRepository;

    public BeanServiceImpl(BeanRepository<E,ID> beanRepository){
        this.beanRepository = beanRepository;
    }

    @Override
    @Transactional //Indica que son metodos transaccionales
    public List<E> findAll() throws Exception {
        try {
            List<E> entities = beanRepository.findAll();
            return entities;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional //Indica que son metodos transaccionales
    public E findById(ID id) throws Exception {
        try {
            //No sabemos si en bd se encuentra un id , se usa Optional
            Optional<E> entityOptional = beanRepository.findById(id);
            //Obtiene una entidad si es que la encuentra
            return entityOptional.get();
        }catch (Exception e){
            throw  new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional //Indica que son metodos transaccionales
    public E save(E entity) throws Exception {
        try {
            entity = beanRepository.save(entity);
            return entity;
        }catch (Exception e){
            throw  new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional //Indica que son metodos transaccionales
    public E update(ID id, E entity) throws Exception {
        try {
            //No sabemos si en bd se encuentra un id , se usa Optional
            Optional<E> entityOptional = beanRepository.findById(id);
            E ent = entityOptional.get();
            ent =beanRepository.save(entity);
            return ent;
        }catch (Exception e){
            throw  new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional //Indica que son metodos transaccionales
    public boolean delete(ID id) throws Exception {
        try {
            if(beanRepository.existsById(id)){
                beanRepository.deleteById(id);
                return true;
            }else {
                throw  new Exception();
            }
        }catch (Exception e){
            throw  new Exception(e.getMessage());
        }
    }

    //Metodo paginacion
    @Override
    @Transactional
    public Page<E> findAll(Pageable pageable) throws Exception{
        try {
            Page<E> entities = beanRepository.findAll(pageable);
            return entities;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
