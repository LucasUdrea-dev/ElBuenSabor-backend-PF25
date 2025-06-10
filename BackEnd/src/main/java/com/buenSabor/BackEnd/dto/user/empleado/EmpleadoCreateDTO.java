/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.user.empleado;

import com.buenSabor.BackEnd.dto.user.usuario.UsuarioCreateDTO;
import java.util.Date;
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
public class EmpleadoCreateDTO extends UsuarioCreateDTO{

    private Double sueldo;
    private Date fechaAlta;
    private Long idSucursal;
    
}
