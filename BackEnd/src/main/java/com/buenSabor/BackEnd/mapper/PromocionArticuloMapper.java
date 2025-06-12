/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.venta.promocionArticulo.PromocionArticuloDTO;
import com.buenSabor.BackEnd.models.venta.PromocionArticulo;
import org.mapstruct.Mapper;

/**
 *
 * @author oscarloha
 */
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ArticuloMapper.class, PromocionMapper.class}) // Add PromocionMapper
public interface PromocionArticuloMapper {

    // Map entity to DTO
    @Mapping(source = "idArticulo", target = "articulo") 
    @Mapping(source = "id", target = "id")
    PromocionArticuloDTO toDto(PromocionArticulo entity);

    // Map DTO to entity
    // @InheritInverseConfiguration // You can use this, but I'll explicitly map for clarity here
    @Mapping(target = "idArticulo", source = "articulo")
    @Mapping(target = "idPromocion", ignore = true) // <--- ADD THIS LINE
    @Mapping(target = "id", source = "id") // Assuming the ID mapping is correct from Bean
    PromocionArticulo toEntity(PromocionArticuloDTO dto);
}