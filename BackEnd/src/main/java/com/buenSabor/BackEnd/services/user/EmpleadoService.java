package com.buenSabor.BackEnd.services.user;

import com.buenSabor.BackEnd.enums.TypeRol;
import com.buenSabor.BackEnd.models.user.Empleado;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.user.EmpleadoRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService extends BeanServiceImpl<Empleado, Long> {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public EmpleadoService(BeanRepository<Empleado, Long> beanRepository) {
        super(beanRepository);
    }

    public List<Usuario> getEmpleados() {
        List<TypeRol> roles = List.of(TypeRol.COCINERO, TypeRol.CAJERO, TypeRol.DELIVERY);
        return empleadoRepository.findByTiposRol(roles);
    }
}
