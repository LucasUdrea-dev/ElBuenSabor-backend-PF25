package com.buenSabor.BackEnd.services.user;

import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import com.buenSabor.BackEnd.mapper.UsuarioMapper;
import com.buenSabor.BackEnd.models.seguridad.Rol;
import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.repositories.seguridad.RolRepository;
import com.buenSabor.BackEnd.repositories.seguridad.UserAuthenticationRepository;
import com.buenSabor.BackEnd.repositories.user.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UserAuthenticationRepository authRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;


    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public List<Usuario> findAllExistente() {
        return usuarioRepository.findAllByExisteTrue();
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO dto) {
        Usuario existente = findById(id);
        if (existente == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Usuario actualizado = usuarioMapper.toEntity(dto);
        actualizado.setId(id);
        return usuarioMapper.toDto(usuarioRepository.save(actualizado));
    }

    @Override
    public void eliminarUsuario(Long id) {
        Usuario usuario = findById(id);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuario.setExiste(false);
        usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioDTO crearUsuario(UsuarioDTO dto) {
         Usuario entity = usuarioMapper.toEntity(dto);

         
        usuarioRepository.save(entity);
        return usuarioMapper.toDto(entity);
    }

    
}
