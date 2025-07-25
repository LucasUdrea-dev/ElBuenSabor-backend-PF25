/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.venta;

import com.buenSabor.BackEnd.models.venta.Promocion;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oscarloha
 */
@Repository
public interface PromocionRepository extends BeanRepository<Promocion, Long> {

    public List<Promocion> findByDenominacionContainingIgnoreCase(String denominacion);

    List<Promocion> findByExisteTrue();

    List<Promocion> findByExisteTrueAndSucursalId(Long sucursalId);
}
