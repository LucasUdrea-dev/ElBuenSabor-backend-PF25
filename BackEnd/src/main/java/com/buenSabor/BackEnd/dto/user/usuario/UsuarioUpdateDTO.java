/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.user.usuario;

import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationCreateDTO;
import com.buenSabor.BackEnd.dto.seguridad.rol.RolCreateDTO;
import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionCreateDTO;
import com.buenSabor.BackEnd.dto.user.telefono.TelefonoCreateByUserDTO;
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
public class UsuarioUpdateDTO {
    
    protected String nombre;
    protected String apellido;
    protected String email;
    protected Boolean existe;
    protected String imagenUsuario;
    
      protected List<TelefonoCreateByUserDTO>telefonos;
    protected UserAuthenticationCreateDTO autenticacion;
    protected List<RolCreateDTO>roles;
    protected List<DireccionCreateDTO> direcciones;
    
    
}
