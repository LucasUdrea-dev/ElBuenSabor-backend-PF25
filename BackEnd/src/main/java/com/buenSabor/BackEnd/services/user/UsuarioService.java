
package com.buenSabor.BackEnd.services.user;

import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import com.buenSabor.BackEnd.models.user.Usuario;
import java.util.List;

/**
 *
 * @author oscarloha
 */
public interface UsuarioService {
    UsuarioDTO crearUsuario(UsuarioDTO dto);

    List<Usuario> findAll();

    List<Usuario> findAllExistente();

    Usuario findById(Long id);

    UsuarioDTO actualizarUsuario(Long id, UsuarioDTO dto);

    void eliminarUsuario(Long id);

    List<Usuario> getUsuariosCustomer();
}
