package com.buenSabor.BackEnd.controllers.bean;

import com.buenSabor.BackEnd.models.bean.Bean;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

//@Tag(name = "Controlador Base", description = "Operaciones CRUD genéricas para entidades")
public abstract class BeanControllerImpl<E extends Bean, S extends BeanServiceImpl<E,Long>> implements BeanController<E, Long> {

    @Autowired
    protected S service;

    @Operation(summary = "Obtener todos los registros")
    @GetMapping("/full")
    @Override
    public ResponseEntity<?> getAll(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Intente más tarde.\"}");
        }
    }

    @Operation(summary = "Obtener un registro por ID")
    @GetMapping("/full/{id}")
    @Override
    public ResponseEntity<?> getOne(@Parameter(description = "ID del registro") @PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Intente más tarde.\"}");
        }
    }
//
//    @Operation(summary = "Guardar un nuevo registro")
//    @PostMapping("/full/save")
//    public ResponseEntity<?> save(@RequestBody E entity){
//        try {
//            return ResponseEntity.status(HttpStatus.OK).body(service.save(entity));
//        } catch (Exception e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente más tarde.\"}");
//        }
//    }
//
//    @Operation(summary = "Actualizar un registro existente")
//    @PutMapping("/full/update/{id}")
//    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody E entity){
//        try {
//            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, entity));
//        } catch (Exception e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente más tarde.\"}");
//        }
//    }
//
//    @Operation(summary = "Eliminar un registro por ID")
//    @DeleteMapping("/full/drop/{id}")
//    public ResponseEntity<?> delete(@PathVariable Long id){
//        try {
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
//        } catch (Exception e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente más tarde.\"}");
//        }
//    }

//    @Operation(summary = "Obtener todos los registros paginados")
//    @GetMapping("/full/pagedd")
//    public ResponseEntity<?> getAll(Pageable pageable) {
//        try {
//            return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));
//        } catch (Exception e){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Intente más tarde.\"}");
//        }
//    }
}
