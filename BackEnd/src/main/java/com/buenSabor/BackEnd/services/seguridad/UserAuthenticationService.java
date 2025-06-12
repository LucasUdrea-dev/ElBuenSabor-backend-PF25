package com.buenSabor.BackEnd.services.seguridad;

import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.repositories.seguridad.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService {
   
    @Autowired
    private UserAuthenticationRepository repository;

    public UserAuthentication create(UserAuthentication entity) {
        entity.setId(null); 
        return repository.save(entity);
    }

    public UserAuthentication update(Long id, UserAuthentication newData) {
        UserAuthentication existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserAuthentication no encontrado con id: " + id));

        existing.setUsername(newData.getUsername());
        existing.setPassword(newData.getPassword());
        return repository.save(existing);
    }
}