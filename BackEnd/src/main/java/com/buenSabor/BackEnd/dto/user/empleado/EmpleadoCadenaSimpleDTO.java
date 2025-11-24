
package com.buenSabor.BackEnd.dto.user.empleado;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioCadenaSimpleDTO;
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
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoCadenaSimpleDTO extends UsuarioCadenaSimpleDTO {
    
    private Double sueldo;

    private Date fechaAlta;

    private SucursalCadenaSimpleDTO sucursal;
}
