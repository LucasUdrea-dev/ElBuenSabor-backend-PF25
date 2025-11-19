package com.buenSabor.BackEnd.services.user;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalDTO;
import com.buenSabor.BackEnd.dto.user.empleado.EmpleadoDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import com.buenSabor.BackEnd.enums.TypeRol;
import com.buenSabor.BackEnd.mapper.EmpleadoMapper;
import com.buenSabor.BackEnd.mapper.TelefonoMapper;
import com.buenSabor.BackEnd.models.seguridad.Rol;
import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.models.user.Empleado;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.seguridad.RolRepository;
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

    public EmpleadoDTO updateEmpleado(Long id, @Valid EmpleadoDTO dto) {
        Empleado existente = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado con ID: "+id+" no encontrado"));

        // Mapea el DTO a entidad para obtener valores actualizados
        Empleado actualizado = empleadoMapper.toEntity(dto);

        // Actualiza campos básicos
        existente.setNombre(actualizado.getNombre());
        existente.setApellido(actualizado.getApellido());
        existente.setEmail(actualizado.getEmail());
        existente.setImagenUsuario(actualizado.getImagenUsuario());
        existente.setExiste(actualizado.getExiste());
        existente.setSueldo(actualizado.getSueldo());
        existente.setFechaAlta(actualizado.getFechaAlta());

        // Actualiza Rol si viene con ID
        if (dto.getRol() != null && dto.getRol().getId() != null) {
            Rol rol = rolRepository.findById(dto.getRol().getId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + dto.getRol().getId()));
            existente.setRol(rol);
        }

        // Actualiza UserAuthentication si viene con ID
        if (dto.getUserAuthentication() != null && dto.getUserAuthentication().getId() != null) {
            UserAuthentication auth = authenticationRepository.findById(dto.getUserAuthentication().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "UserAuth no encontrado con id: " + dto.getUserAuthentication().getId()));
            existente.setUserAuthentication(auth);
        }

        // Actualiza listas completas (reemplazo simple)
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
