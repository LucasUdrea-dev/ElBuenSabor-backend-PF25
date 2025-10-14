package com.buenSabor.BackEnd.dto.user.empleado;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDTO extends UsuarioDTO {

    private Double sueldo;

    private Date fechaAlta;

    private SucursalDTO sucursal;

}
