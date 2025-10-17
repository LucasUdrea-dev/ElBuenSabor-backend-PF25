package com.buenSabor.BackEnd.dto.seguridad.rol;

import com.buenSabor.BackEnd.dto.seguridad.tiporol.TipoRolDTO;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolDTO {

    private Long id;
    private Date fechaAlta;
    private TipoRolDTO tipoRol;

}
