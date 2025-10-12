package com.buenSabor.BackEnd.services.user;

import com.buenSabor.BackEnd.dto.user.telefono.TelefonoDTO;
import com.buenSabor.BackEnd.mapper.TelefonoMapper;
import com.buenSabor.BackEnd.models.user.Telefono;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.user.TelefonoRepository;
import com.buenSabor.BackEnd.repositories.user.UsuarioRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelefonoService extends BeanServiceImpl<Telefono,Long> {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TelefonoRepository telefonoRepository;
    @Autowired
    private TelefonoMapper telefonoMapper;

    public TelefonoService(BeanRepository<Telefono, Long> beanRepository) {
        super(beanRepository);
    }

    public List<TelefonoDTO> findTelefonosByUserId(Long idUsuario) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndExisteTrue(idUsuario);
        if (usuarioOptional.isEmpty()) {
            throw new Exception("Usuario con ID " + idUsuario + " no encontrado o inactivo.");
        }
        Usuario usuario = usuarioOptional.get();
        List<Telefono> telefonos = usuario.getTelefonoList();
        return telefonos == null ? java.util.Collections.emptyList() : telefonos.stream()
                .map(telefonoMapper::toDto)
                .collect(Collectors.toList());
    }

    public TelefonoDTO crearTelefonoParaUsuario(Long idUsuario, TelefonoDTO dto) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndExisteTrue(idUsuario);
        if (usuarioOptional.isEmpty()) {
            throw new Exception("Usuario con ID " + idUsuario + " no encontrado o inactivo.");
        }
        Usuario usuario = usuarioOptional.get();
        Telefono nuevo = telefonoMapper.toEntity(dto);
        nuevo.setUsuario(usuario);
        Telefono guardado = telefonoRepository.save(nuevo);
        if (usuario.getTelefonoList() == null) {
            usuario.setTelefonoList(new java.util.ArrayList<>());
        }
        usuario.getTelefonoList().add(guardado);
        usuarioRepository.save(usuario);
        return telefonoMapper.toDto(guardado);
    }

    public TelefonoDTO obtenerTelefonoDeUsuario(Long idUsuario, Long idTelefono) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndExisteTrue(idUsuario);
        if (usuarioOptional.isEmpty()) {
            throw new Exception("Usuario con ID " + idUsuario + " no encontrado o inactivo.");
        }
        Usuario usuario = usuarioOptional.get();
        if (usuario.getTelefonoList() == null) {
            throw new Exception("El usuario no tiene telefonos asociados.");
        }
        Telefono telefono = usuario.getTelefonoList().stream()
                .filter(t -> t.getId() != null && t.getId().equals(idTelefono))
                .findFirst()
                .orElseThrow(() -> new Exception("El telefono no pertenece al usuario indicado."));
        return telefonoMapper.toDto(telefono);
    }

    public TelefonoDTO actualizarTelefonoDeUsuario(Long idUsuario, Long idTelefono, TelefonoDTO dto) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndExisteTrue(idUsuario);
        if (usuarioOptional.isEmpty()) {
            throw new Exception("Usuario con ID " + idUsuario + " no encontrado o inactivo.");
        }
        Usuario usuario = usuarioOptional.get();
        Telefono telefono = telefonoRepository.findById(idTelefono)
                .orElseThrow(() -> new Exception("Telefono con ID " + idTelefono + " no encontrado."));
        boolean pertenece = usuario.getTelefonoList() != null && usuario.getTelefonoList().stream()
                .anyMatch(t -> t.getId() != null && t.getId().equals(idTelefono));
        if (!pertenece) {
            throw new Exception("El telefono no pertenece al usuario indicado.");
        }
        telefonoMapper.updateFromDto(dto, telefono);
        Telefono guardado = telefonoRepository.save(telefono);
        return telefonoMapper.toDto(guardado);
    }

    public void eliminarTelefonoDeUsuario(Long idUsuario, Long idTelefono) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndExisteTrue(idUsuario);
        if (usuarioOptional.isEmpty()) {
            throw new Exception("Usuario con ID " + idUsuario + " no encontrado o inactivo.");
        }
        Usuario usuario = usuarioOptional.get();
        telefonoRepository.findById(idTelefono)
                .orElseThrow(() -> new Exception("Telefono con ID " + idTelefono + " no encontrado."));
        if (usuario.getTelefonoList() == null) {
            throw new Exception("El usuario no tiene telefonos asociados.");
        }
        boolean removed = usuario.getTelefonoList().removeIf(t -> t.getId() != null && t.getId().equals(idTelefono));
        if (!removed) {
            throw new Exception("El telefono no pertenece al usuario indicado.");
        }
        usuarioRepository.save(usuario);
        telefonoRepository.deleteById(idTelefono);
    }
}
