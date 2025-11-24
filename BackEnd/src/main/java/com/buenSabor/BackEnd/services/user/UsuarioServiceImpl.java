package com.buenSabor.BackEnd.services.user;

import com.buenSabor.BackEnd.dto.user.usuario.UsuarioCreateDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioCreateDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioResponseDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioUpdateDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioResponseDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioUpdateDTO;
import com.buenSabor.BackEnd.enums.TypeRol;
import com.buenSabor.BackEnd.mapper.TelefonoMapper;
import com.buenSabor.BackEnd.mapper.UserAuthenticationMapper;
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
    @Autowired
    private TelefonoMapper telefonoMapper;
    @SuppressWarnings("unused")
    @Autowired
    private UserAuthenticationMapper autenticacionMapper;

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

    public List<Usuario> getUsuariosCustomer() {
        return usuarioRepository.findByTipoRol(TypeRol.CUSTOMER);
    }

    @Override
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioMapper.updateFromUpdateDto(dto, usuario);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDto(usuario);
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
    public UsuarioResponseDTO crearUsuario(UsuarioCreateDTO dto) {
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDto(usuario);
    }
}
