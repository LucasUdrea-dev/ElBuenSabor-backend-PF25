package com.buenSabor.BackEnd.dto.seguridad.registro;


import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationRequestDTO;
import com.buenSabor.BackEnd.dto.user.telefono.TelefonoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    @Email(message = "El nombre de usuario debe tener formato de correo")
    protected String email;

    protected String urlImagen;

    protected List<TelefonoDTO> telefonoList;

    @Valid
    protected UserAuthenticationRequestDTO userAuth;
}
