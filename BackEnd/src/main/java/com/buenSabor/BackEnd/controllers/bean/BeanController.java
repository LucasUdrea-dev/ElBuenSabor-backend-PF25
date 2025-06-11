package com.buenSabor.BackEnd.controllers.bean;

import com.buenSabor.BackEnd.models.bean.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

public interface BeanController <E extends Bean, ID extends Serializable> {
    public ResponseEntity<?> getAll();
    public ResponseEntity<?> getOne(@PathVariable ID id);
    public ResponseEntity<?> save(@RequestBody E entity);
    public ResponseEntity<?> update(@PathVariable ID id ,@RequestBody E entity);
    public ResponseEntity<?> delete(@PathVariable ID id);

    //Paginacion (
//    public ResponseEntity<?> getAll(Pageable pageable);

}
