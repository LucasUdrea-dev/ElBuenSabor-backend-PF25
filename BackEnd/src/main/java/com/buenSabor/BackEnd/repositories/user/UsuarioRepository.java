package com.buenSabor.BackEnd.repositories.user;

import com.buenSabor.BackEnd.enums.TypeRol;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends BeanRepository<Usuario, Long> {

    List<Usuario> findAllByExisteTrue();

    Optional<Usuario> findByIdAndExisteTrue(Long id);

    Usuario findByEmail(String email);

    Optional<Usuario> findById(Long id);

    @Query("SELECT u FROM Usuario u WHERE u.rol.tipoRol.rol = :tipoRol")
    List<Usuario> findByTipoRol(@Param("tipoRol") TypeRol tipoRol);
}
