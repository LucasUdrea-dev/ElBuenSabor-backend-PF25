/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.user.usuario;

import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionResponseDTO;
import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationResponseDTO;
import com.buenSabor.BackEnd.dto.seguridad.rol.RolResponseDTO;
import com.buenSabor.BackEnd.dto.user.telefono.TelefonoResponseDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author oscarloha
 */

@Getter  
@Setter  
@NoArgsConstructor  
@AllArgsConstructor  
public class UsuarioResponseDTO {
    
    protected String nombre;
    protected String apellido;
    protected String email;
    protected Boolean existe;
    protected String imagenUsuario;
    
    protected List<TelefonoResponseDTO>telefonos;
    protected UserAuthenticationResponseDTO autenticacion;
    protected List<RolResponseDTO>roles;
    protected List<DireccionResponseDTO> direcciones;
    
}
