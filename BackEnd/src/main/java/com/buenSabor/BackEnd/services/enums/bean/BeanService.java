package com.buenSabor.BackEnd.services.enums.bean;

import com.buenSabor.BackEnd.models.bean.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface BeanService<E extends Bean, ID extends Serializable> {

    public List<E> findAll() throws Exception;
    public E findById(ID id) throws Exception;
    public E save(E entity) throws Exception;
    public E update(ID id, E entity) throws Exception;
    public boolean delete(ID id) throws Exception;

    //Metodo paginacion
    public Page<E>findAll(Pageable pageable) throws Exception;

}
