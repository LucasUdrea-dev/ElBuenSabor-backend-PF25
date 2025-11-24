package com.buenSabor.BackEnd.dto.seguridad.registro;


import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationRequestDTO;
import com.buenSabor.BackEnd.dto.user.telefono.TelefonoCreateDTO;
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

    // este create no fue probado
    
    protected List<TelefonoCreateDTO> telefonoList;

    protected UserAuthenticationRequestDTO userAuth;
}
