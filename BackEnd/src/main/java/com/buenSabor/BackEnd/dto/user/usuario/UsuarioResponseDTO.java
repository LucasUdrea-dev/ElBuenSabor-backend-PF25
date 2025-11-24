/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.user.usuario;

import com.buenSabor.BackEnd.dto.seguridad.rol.RolDTO;
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
public class UsuarioResponseDTO {
    
    protected Long id;
    
    protected String nombre;
    
    protected String apellido;
    
    protected String email;
    
    protected Boolean existe;
    
    protected String imagenUsuario;
    
    protected RolDTO rol;
    
}
