/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.user.empleado;

import com.buenSabor.BackEnd.dto.user.usuario.UsuarioCreateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
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
public class EmpleadoCreateDTO extends UsuarioCreateDTO{
    
    @NotNull(message = "El sueldo es obligatorio (aca no se trabaja en negro, excepto el back)")
    private Double sueldo;
    
    @NotNull(message = "La fecha de alta es obligatoria")
    private Date fechaAlta;

    @NotNull(message = "El id de la sucursal el obligatoria")
    private Long sucursalId;
}
