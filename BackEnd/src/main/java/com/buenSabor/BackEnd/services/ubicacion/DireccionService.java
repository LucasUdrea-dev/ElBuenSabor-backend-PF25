/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.ubicacion;

import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
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
   
}
