package com.buenSabor.BackEnd.mapper.bean;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

/**
 * @param <E> Entity (Ej: Empresa)
 * @param <R> ResponseDTO (Ej: EmpresaResponseDTO - vista normal)
 * @param <C> CreateDTO (Ej: EmpresaCreateDTO - para crear)
 * @param <U> UpdateDTO (Ej: EmpresaUpdateDTO - para actualizar)
 * @param <CS> CadenaSimpleDTO (Ej: EmpresaCadenaSimpleDTO - vista reducida para listas/combos)
 */
public interface BeanMapper<E, R, C, U, CS> {

    // --- De Entidad a Response ---
    R toResponseDto(E entity);
    List<R> toResponseDtoList(List<E> entityList);

    // --- De Entidad a CadenaSimple ---
    CS toSimpleDto(E entity);
    List<CS> toSimpleDtoList(List<E> entityList);

    // --- De CreateDTO a Entidad  ---
    
//    @Mapping(target = "id", ignore = true) 
    E toEntity(C createDto);

    // --- De UpdateDTO a Entidad (Actualización) ---
   
    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "id", ignore = true) 
    void updateFromUpdateDto(U updateDto, @MappingTarget E entity);
    
    // Métodos auxiliares
    List<E> toEntityList(List<C> createDtoList);
}