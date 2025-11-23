
package com.buenSabor.BackEnd.dto.ubicacion.direccion;

import com.buenSabor.BackEnd.dto.ubicacion.ciudad.CiudadCadenaSimpleDTO;
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
public class DireccionCadenaSimpleDTO {
    
   
    private Long id;
    
    private String nombreCalle;
    
    private String numeracion;
   
    private Double latitud;
   
    private Double longitud;
    
    private String alias;

    private String descripcionEntrega;

    private CiudadCadenaSimpleDTO ciudad;

    private Long idUsuario;

    // manejar en el servicio
    private boolean isSucursal;
    
}
