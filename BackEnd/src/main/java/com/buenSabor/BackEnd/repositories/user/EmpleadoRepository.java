/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.repositories.user;

import com.buenSabor.BackEnd.enums.TypeRol;
import com.buenSabor.BackEnd.models.user.Empleado;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author oscarloha
 */
@Repository
public interface EmpleadoRepository extends BeanRepository<Empleado,Long>{
    // Trae todos los usuarios cuyo rol esté en la lista de tiposRol
    @Query("SELECT u FROM Usuario u WHERE u.rol.tipoRol.rol IN :tiposRol")
    List<Usuario> findByTiposRol(@Param("tiposRol") List<TypeRol> tiposRol);
    
}
