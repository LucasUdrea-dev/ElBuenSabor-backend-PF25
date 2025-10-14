/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.seguridad;

import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author oscarloha
 */
@Repository
public interface UserAuthenticationRepository extends BeanRepository<UserAuthentication,Long>{
    Optional<UserAuthentication> findByUsername(String username);
    Optional<UserAuthentication> findById(Long id);
}
