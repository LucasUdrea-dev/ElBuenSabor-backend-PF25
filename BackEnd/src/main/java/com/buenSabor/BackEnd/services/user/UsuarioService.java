package com.buenSabor.BackEnd.services.user;

import com.buenSabor.BackEnd.dto.user.usuario.UsuarioCreateDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioResponseDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioUpdateDTO;
import com.buenSabor.BackEnd.models.user.Usuario;
import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO crearUsuario(UsuarioCreateDTO dto);

    List<Usuario> findAll();

    List<Usuario> findAllExistente();

    Usuario findById(Long id);

    UsuarioResponseDTO actualizarUsuario(Long id, UsuarioUpdateDTO dto);

    void eliminarUsuario(Long id);

    List<Usuario> getUsuariosCustomer();
}
