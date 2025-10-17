package com.buenSabor.BackEnd.dto.user.telefono;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TelefonoDTO {

    private Long id;
    private BigInteger numero;
}
