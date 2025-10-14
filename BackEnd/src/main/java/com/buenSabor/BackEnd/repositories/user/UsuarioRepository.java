/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.user;

import com.buenSabor.BackEnd.enums.TypeRol;
import com.buenSabor.BackEnd.models.seguridad.TipoRol;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oscarloha
 */
@Repository
public interface UsuarioRepository extends BeanRepository<Usuario,Long>{

    List<Usuario> findAllByExisteTrue();
    Optional<Usuario> findByIdAndExisteTrue(Long id);
    Usuario findByEmail(String email);
    Optional<Usuario> findById(Long id);

    // Trae todos los usuarios cuyo rol tenga tipoRol = CUSTOMER
    @Query("SELECT u FROM Usuario u WHERE u.rol.tipoRol.rol = :tipoRol")
    List<Usuario> findByTipoRol(@Param("tipoRol") TypeRol tipoRol);
}
