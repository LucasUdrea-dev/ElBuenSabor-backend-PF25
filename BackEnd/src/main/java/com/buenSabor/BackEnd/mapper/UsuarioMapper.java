/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import com.buenSabor.BackEnd.models.user.Usuario;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author oscarloha
 */
@Mapper(componentModel = "spring", uses = {
    DireccionMapper.class,
    TelefonoMapper.class,
    RolMapper.class,
    UserAuthenticationMapper.class
})
public interface UsuarioMapper {

      @Mapping(target = "pedidoList", ignore = true) 
      @Mapping(target = "id", ignore = true) 

    Usuario toEntity(UsuarioDTO dto);

    UsuarioDTO toDto(Usuario entity);

    List<UsuarioDTO> toDtoList(List<Usuario> list);
}
