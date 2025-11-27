package com.buenSabor.BackEnd.dto.user.empleado;

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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmpleadoUpdateDTO {
    private String nombre;

    private String apellido;

    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    @Email(message = "El nombre de usuario debe tener formato de correo")
    private String email;

    private List<TelefonoDTO> telefonoList;

    private Long idRol;

    protected UserAuthenticationRequestDTO userAuthentication;
}
