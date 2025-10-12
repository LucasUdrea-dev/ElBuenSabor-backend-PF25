/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.ubicacion;

import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionDTO;
import com.buenSabor.BackEnd.mapper.DireccionMapper;
import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.ubicacion.DireccionRepository;
import com.buenSabor.BackEnd.repositories.user.UsuarioRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author oscarloha
 */
@Service
public class DireccionService extends BeanServiceImpl<Direccion,Long>{
    
      @Autowired
    private UsuarioRepository usuarioRepository; 
    @Autowired
    private DireccionRepository direccionRepository;
    @Autowired
    private DireccionMapper direccionMapper;

    
    public DireccionService(BeanRepository<Direccion, Long> beanRepository) {
        super(beanRepository);
    }
    
       // Método para obtener todas las direcciones de un usuario por su ID
    public List<Direccion> findDireccionesByUserId(Long idUsuario) throws Exception {
        
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndExisteTrue(idUsuario);

        if (usuarioOptional.isEmpty()) {
            throw new Exception("Usuario con ID " + idUsuario + " no encontrado o inactivo.");
        }
        
        Usuario usuario = usuarioOptional.get();
        
        return usuario.getDireccionList(); 
    }
    
    public DireccionDTO crearDireccionParaUsuario(Long idUsuario, DireccionDTO dto) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndExisteTrue(idUsuario);
        if (usuarioOptional.isEmpty()) {
            throw new Exception("Usuario con ID " + idUsuario + " no encontrado o inactivo.");
        }
        Usuario usuario = usuarioOptional.get();
        Direccion nueva = direccionMapper.toEntity(dto);
        Direccion guardada = direccionRepository.save(nueva);
        if (usuario.getDireccionList() == null) {
            usuario.setDireccionList(new java.util.ArrayList<>());
        }
        boolean yaAsociada = usuario.getDireccionList().stream()
                .anyMatch(d -> d.getId() != null && d.getId().equals(guardada.getId()));
        if (!yaAsociada) {
            usuario.getDireccionList().add(guardada);
            usuarioRepository.save(usuario);
        }
        return direccionMapper.toDto(guardada);
    }
    
    public DireccionDTO actualizarDireccionDeUsuario(Long idUsuario, Long idDireccion, DireccionDTO dto) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndExisteTrue(idUsuario);
        if (usuarioOptional.isEmpty()) {
            throw new Exception("Usuario con ID " + idUsuario + " no encontrado o inactivo.");
        }
        Usuario usuario = usuarioOptional.get();
        Direccion direccion = direccionRepository.findById(idDireccion)
                .orElseThrow(() -> new Exception("Direccion con ID " + idDireccion + " no encontrada."));
        boolean pertenece = usuario.getDireccionList() != null && usuario.getDireccionList().stream()
                .anyMatch(d -> d.getId() != null && d.getId().equals(idDireccion));
        if (!pertenece) {
            throw new Exception("La direccion no pertenece al usuario indicado.");
        }
        direccionMapper.updateDireccionFromDto(dto, direccion);
        Direccion guardada = direccionRepository.save(direccion);
        return direccionMapper.toDto(guardada);
    }
    
    public void eliminarDireccionDeUsuario(Long idUsuario, Long idDireccion) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndExisteTrue(idUsuario);
        if (usuarioOptional.isEmpty()) {
            throw new Exception("Usuario con ID " + idUsuario + " no encontrado o inactivo.");
        }
        Usuario usuario = usuarioOptional.get();
        direccionRepository.findById(idDireccion)
                .orElseThrow(() -> new Exception("Direccion con ID " + idDireccion + " no encontrada."));
        if (usuario.getDireccionList() == null) {
            throw new Exception("El usuario no tiene direcciones asociadas.");
        }
        boolean removed = usuario.getDireccionList().removeIf(d -> d.getId() != null && d.getId().equals(idDireccion));
        if (!removed) {
            throw new Exception("La direccion no pertenece al usuario indicado.");
        }
        usuarioRepository.save(usuario);
    }
    
    public DireccionDTO obtenerDireccionDeUsuario(Long idUsuario, Long idDireccion) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndExisteTrue(idUsuario);
        if (usuarioOptional.isEmpty()) {
            throw new Exception("Usuario con ID " + idUsuario + " no encontrado o inactivo.");
        }
        Usuario usuario = usuarioOptional.get();
        if (usuario.getDireccionList() == null) {
            throw new Exception("El usuario no tiene direcciones asociadas.");
        }
        Direccion direccion = usuario.getDireccionList().stream()
                .filter(d -> d.getId() != null && d.getId().equals(idDireccion))
                .findFirst()
                .orElseThrow(() -> new Exception("La direccion no pertenece al usuario indicado."));
        return direccionMapper.toDto(direccion);
    }
   
}
