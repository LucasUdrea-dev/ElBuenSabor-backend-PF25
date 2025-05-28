/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.user.empleado;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalSimplDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioResponseDTO;
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
public class EmpleadoResponseDTO extends UsuarioResponseDTO{
    
    private Long id;
    private Double sueldo;
    private Date fechaAlta;
    private SucursalSimplDTO sucursal;
    
}
