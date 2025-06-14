package com.buenSabor.BackEnd.services.user;

import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.user.UsuarioRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends BeanServiceImpl<Usuario, Long> {
    public UsuarioService(BeanRepository<Usuario, Long> beanRepository) {
        super(beanRepository);
    }

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public List<Direccion> findDirecciones(Long id) throws Exception{
        try {
            
            Optional<Usuario> usuario = usuarioRepository.findById(id);

            Usuario usuarioEncontrado = usuario.get();

            return usuarioEncontrado.getDireccionList();

        } catch (Exception e) {
            throw new Exception();
        }
    }

}
