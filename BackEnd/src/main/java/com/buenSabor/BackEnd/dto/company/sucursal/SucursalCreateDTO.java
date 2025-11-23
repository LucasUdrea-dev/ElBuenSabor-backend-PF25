
package com.buenSabor.BackEnd.dto.company.sucursal;

import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionCreateDTO;
import jakarta.validation.constraints.NotBlank;
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
public class SucursalCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "La hora de apertura es obligatoria")
    private String horaApertura;
    @NotBlank(message = "La hora de cierre es obligatorie")
    private String horaCierre;
    @NotBlank(message = "La existencia es obligatoria")
    private Boolean existe;
    @NotBlank(message = "La direccion es obligatoria")
    private DireccionCreateDTO direccion;
}
