/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.seguridad;

import com.buenSabor.BackEnd.models.seguridad.Rol;
import com.buenSabor.BackEnd.models.seguridad.TipoRol;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author oscarloha
 */
@Repository
public interface RolRepository extends BeanRepository<Rol,Long>{

    Optional<Rol> findByTipoRol(TipoRol tipoRolCliente);
}
