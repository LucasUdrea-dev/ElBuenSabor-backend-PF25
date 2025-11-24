/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.user.usuario;

import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationRequestDTO;
import com.buenSabor.BackEnd.dto.seguridad.rol.RolDTO;
import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionCreateDTO;
import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionDTO;
import com.buenSabor.BackEnd.dto.user.telefono.TelefonoCreateDTO;
import com.buenSabor.BackEnd.dto.user.telefono.TelefonoDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author oscarloha
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCreateDTO {
    
    @NotBlank(message = "El nombre es obligatorio")
    protected String nombre;
    
    @NotBlank(message = "El apellido es obligatorio")
    protected String apellido;

    @NotBlank(message = "El email es obligatorio")
    protected String email;
    
    @NotNull(message = "La existencia es obligatoria")
    protected Boolean existe;

    protected String imagenUsuario;
    
    @NotNull(message = "un telefono es obligatorio")
    protected List<TelefonoCreateDTO> telefonoList;

    @NotNull(message = "El rol es obligatorio")
    protected RolDTO rol;

    @NotNull(message = "la direccion es obligatoria")
    protected List<DireccionCreateDTO> direccionList;

    @NotNull(message = "la auth es obligatoria")
    protected UserAuthenticationRequestDTO userAuthentication;
    
}
