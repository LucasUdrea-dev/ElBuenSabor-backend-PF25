/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.user.usuario;

import com.buenSabor.BackEnd.dto.seguridad.rol.RolDTO;
import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionUpdateDTO;
import com.buenSabor.BackEnd.dto.user.telefono.TelefonoUpdateDTO;
import com.buenSabor.BackEnd.models.seguridad.Rol;
import java.util.ArrayList;
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
public class UsuarioUpdateDTO {
    
    protected String nombre;
    
    protected String apellido;
    
    protected String email;
    
    protected Boolean existe;
    
    protected String imagenUsuario;

    protected List<TelefonoUpdateDTO> telefonoList = new ArrayList<>();

    protected RolDTO rol;

    protected List<DireccionUpdateDTO> DireccionList = new ArrayList<>();

   
    
}
