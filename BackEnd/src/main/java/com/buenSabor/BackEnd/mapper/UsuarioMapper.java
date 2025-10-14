package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import com.buenSabor.BackEnd.models.user.Usuario;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        DireccionMapper.class,
        TelefonoMapper.class,
        RolMapper.class,
        UserAuthenticationMapper.class
})
public interface UsuarioMapper {

    @Mapping(target = "pedidoList", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuarioDireccionList", ignore = true)

    // <--[UsuarioDTO dto]--
    // ==>{Usuario entity, y lo que ignora *pedidoList,id,usuarioDireccionList*}
    Usuario toEntity(UsuarioDTO dto);

    // <--[Usuario entity]--
    // ==>{UsuarioDTO dto, y lo que ignora *direccionList*}
    @Mapping(target = "direccionList", ignore = true)
    UsuarioDTO toDto(Usuario entity);

    // <--[List<Usuario> list]--
    // ==>{List<UsuarioDTO> list, y lo que ignora *-*}
    List<UsuarioDTO> toDtoList(List<Usuario> list);

    // <--[UsuarioDTO dto, Usuario entity]--
    // ==>{void, y lo que ignora *id,pedidoList,usuarioDireccionList*}
    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "pedidoList", ignore = true)
    @org.mapstruct.Mapping(target = "usuarioDireccionList", ignore = true)
    void updateFromDto(UsuarioDTO dto, @org.mapstruct.MappingTarget Usuario entity);
}
