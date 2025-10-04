package com.buenSabor.BackEnd.services.user;

import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import com.buenSabor.BackEnd.mapper.DireccionMapper;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Autowired
    private UserAuthenticationMapper autenticacionMapper;
    @Autowired
    private DireccionMapper direccionMapper;

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
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Mapea el DTO a entidad para obtener valores actualizados
        Usuario actualizado = usuarioMapper.toEntity(dto);

        // Actualiza campos básicos
        existente.setNombre(actualizado.getNombre());
        existente.setApellido(actualizado.getApellido());
        existente.setEmail(actualizado.getEmail());
        existente.setImagenUsuario(actualizado.getImagenUsuario());
        existente.setExiste(actualizado.getExiste());

        // Actualiza Rol si viene con ID
        if (dto.getRol() != null && dto.getRol().getId() != null) {
            Rol rol = rolRepository.findById(dto.getRol().getId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + dto.getRol().getId()));
            existente.setRol(rol);
        }

        // Actualiza UserAuthentication si viene con ID
        if (dto.getUserAuthentication() != null && dto.getUserAuthentication().getId() != null) {
            UserAuthentication auth = authRepository.findById(dto.getUserAuthentication().getId())
                    .orElseThrow(() -> new RuntimeException("UserAuth no encontrado con id: " + dto.getUserAuthentication().getId()));
            existente.setUserAuthentication(auth);
        }

        // Actualiza listas completas (reemplazo simple)
        if (dto.getTelefonoList() != null) {
            existente.setTelefonoList(telefonoMapper.telefonoDtoListToEntityList(dto.getTelefonoList()));
        }

        // Aquí deberías usar el mapper adecuado para Direccion, no UserAuthenticationMapper
        if (dto.getDireccionList() != null) {
            existente.setDireccionList(direccionMapper.direccionDtoListToEntityList(dto.getDireccionList()));
        }

        Usuario guardado = usuarioRepository.save(existente);
        return usuarioMapper.toDto(guardado);
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

    /*@Override
    public UsuarioDTO crearUsuario(UsuarioDTO dto) {
        Usuario entity = usuarioMapper.toEntity(dto);

        UserAuthentication userAuth = entity.getUserAuthentication();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userAuth.setPassword(encoder.encode(userAuth.getPassword()));

        usuarioRepository.save(entity);
        return usuarioMapper.toDto(entity);
    }*/

}
