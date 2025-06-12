/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.seguridad.rol;

import com.buenSabor.BackEnd.dto.seguridad.tiporol.TipoRolDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import java.util.Date;
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
@AllArgsConstructor
@NoArgsConstructor
public class RolDTO {
    private Long id;
    
    private Date fechaAlta;
    
    private TipoRolDTO tipoRol;

}
