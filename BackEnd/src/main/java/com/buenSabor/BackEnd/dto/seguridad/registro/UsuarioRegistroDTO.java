package com.buenSabor.BackEnd.dto.seguridad.registro;


import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationRequestDTO;
import com.buenSabor.BackEnd.dto.seguridad.rol.RolDTO;
import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionDTO;
import com.buenSabor.BackEnd.dto.user.telefono.TelefonoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRegistroDTO {
    protected String nombre;

    protected String apellido;

    protected String email;

    protected String urlImagen;

    protected List<TelefonoDTO> telefonoList;

    protected UserAuthenticationRequestDTO userAuth;
}
