package com.buenSabor.BackEnd.services.user;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalDTO;
import com.buenSabor.BackEnd.dto.user.empleado.EmpleadoDTO;
import com.buenSabor.BackEnd.dto.user.empleado.EmpleadoUpdateDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import com.buenSabor.BackEnd.enums.TypeRol;
import com.buenSabor.BackEnd.mapper.EmpleadoMapper;
import com.buenSabor.BackEnd.mapper.TelefonoMapper;
import com.buenSabor.BackEnd.models.seguridad.Rol;
import com.buenSabor.BackEnd.models.seguridad.TipoRol;
import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.models.user.Empleado;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.seguridad.RolRepository;
import com.buenSabor.BackEnd.repositories.seguridad.TipoRolRepository;
import com.buenSabor.BackEnd.repositories.seguridad.UserAuthenticationRepository;
import com.buenSabor.BackEnd.repositories.user.EmpleadoRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmpleadoService extends BeanServiceImpl<Empleado, Long> {

    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private EmpleadoMapper empleadoMapper;
    @Autowired
    private TelefonoMapper telefonoMapper;
    @Autowired
    private TipoRolRepository tipoRolRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UserAuthenticationRepository authenticationRepository;


    public EmpleadoService(BeanRepository<Empleado, Long> beanRepository) {
        super(beanRepository);
    }

    public List<Usuario> getEmpleados() {
        List<TypeRol> roles = List.of(TypeRol.COCINERO, TypeRol.CAJERO, TypeRol.DELIVERY);
        return empleadoRepository.findByTiposRol(roles);
    }

    public EmpleadoDTO updateEmpleado(Long id, @Valid EmpleadoUpdateDTO dto) {
        Empleado existente = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado con ID: "+id+" no encontrado"));

        existente.setNombre(dto.getNombre());
        existente.setApellido(dto.getApellido());

        // Asignar tipo rol
        TipoRol tipoRol = tipoRolRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new RuntimeException("No existe rol con id: " + dto.getIdRol()));

        Rol nuevoRol = new Rol();
        nuevoRol.setTipoRol(tipoRol);
        nuevoRol.setFechaAlta(new Date());
        rolRepository.save(nuevoRol);

        existente.setRol(nuevoRol);

        // Actualiza UserAuthentication
        existente.setEmail(dto.getEmail());

        if (existente.getUserAuthentication() != null && existente.getUserAuthentication().getId() != null) {
            UserAuthentication auth = authenticationRepository.findById(existente.getUserAuthentication().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "UserAuth no encontrado con id: " + existente.getUserAuthentication().getId()));

            auth.setUsername(dto.getEmail());
            authenticationRepository.save(auth);

            existente.setUserAuthentication(auth);

        }

        // Actualiza listas completas
        if (dto.getTelefonoList() != null) {
            existente.setTelefonoList(telefonoMapper.telefonoDtoListToEntityList(dto.getTelefonoList()));
        }

        // Las direcciones se manejan a través de DireccionService

        Empleado guardado = empleadoRepository.save(existente);

        return empleadoMapper.toDto(guardado);
    }


    public void eliminarEmpleado(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado con ID: "+id+" no encontrado"));

        empleado.setExiste(false);
        empleadoRepository.save(empleado);
    }
}
