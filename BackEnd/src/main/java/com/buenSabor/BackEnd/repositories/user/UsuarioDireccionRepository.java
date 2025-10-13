package com.buenSabor.BackEnd.repositories.user;

import com.buenSabor.BackEnd.models.user.UsuarioDireccion;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad UsuarioDireccion.
 * Maneja la relación entre Usuario y Direccion.
 */
@Repository
public interface UsuarioDireccionRepository extends BeanRepository<UsuarioDireccion, Long> {
}
