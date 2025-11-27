package com.buenSabor.BackEnd.dto.seguridad.registro;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalDTO;
import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationRequestDTO;
import com.buenSabor.BackEnd.dto.seguridad.rol.RolDTO;
import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionDTO;
import com.buenSabor.BackEnd.dto.user.telefono.TelefonoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    @Email(message = "El nombre de usuario debe tener formato de correo")
    private String email;

    private boolean existe;

    private String urlImagen;

    private Double sueldo;

    private List<TelefonoDTO> telefonoList;

    private Long idRol;

    @Valid
    protected UserAuthenticationRequestDTO userAuth;
}
