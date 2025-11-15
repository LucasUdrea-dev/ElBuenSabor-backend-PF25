package com.buenSabor.BackEnd.dto.seguridad.registro;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalDTO;
import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationRequestDTO;
import com.buenSabor.BackEnd.dto.seguridad.rol.RolDTO;
import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionDTO;
import com.buenSabor.BackEnd.dto.user.telefono.TelefonoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoRegistroDTO {
    private String nombre;

    private String apellido;

    private String email;

    private String urlImagen;

    private Double sueldo;

    private List<TelefonoDTO> telefonoList;

    private Long idRol;

    protected UserAuthenticationRequestDTO userAuth;
}
