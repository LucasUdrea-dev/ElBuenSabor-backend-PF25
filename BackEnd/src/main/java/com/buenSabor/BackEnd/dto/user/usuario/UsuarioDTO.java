package com.buenSabor.BackEnd.dto.user.usuario;

import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationRequestDTO;
import com.buenSabor.BackEnd.dto.seguridad.rol.RolDTO;
import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionDTO;
import com.buenSabor.BackEnd.dto.user.telefono.TelefonoDTO;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    protected Long id;

    protected String nombre;

    protected String apellido;

    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    @Email(message = "El nombre de usuario debe tener formato de correo")
    protected String email;

    protected Boolean existe;

    protected String imagenUsuario;

    protected List<TelefonoDTO> telefonoList;

    protected RolDTO rol;

    protected List<DireccionDTO> direccionList;

    protected UserAuthenticationRequestDTO userAuthentication;

}
