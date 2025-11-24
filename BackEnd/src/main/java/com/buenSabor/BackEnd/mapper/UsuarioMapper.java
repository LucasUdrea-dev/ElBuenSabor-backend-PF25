package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.user.usuario.UsuarioCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioCreateDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioResponseDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioUpdateDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.user.Usuario;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {
        DireccionMapper.class,
        TelefonoMapper.class,
        RolMapper.class,
        UserAuthenticationMapper.class
})
public interface UsuarioMapper
        extends BeanMapper<Usuario, UsuarioResponseDTO, UsuarioCreateDTO, UsuarioUpdateDTO, UsuarioCadenaSimpleDTO> {

    @Override
    @Mapping(target = "pedidoList", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuarioDireccionList", ignore = true)

    // <--[UsuarioDTO dto]--
    // ==>{Usuario entity, y lo que ignora *pedidoList,id,usuarioDireccionList*}
    Usuario toEntity(UsuarioCreateDTO dto);

    // <--[Usuario entity]--
    // ==>{UsuarioDTO dto, y lo que ignora *direccionList*}
    @Override
    UsuarioResponseDTO toResponseDto(Usuario entity);

    // <--[List<Usuario> list]--
    // ==>{List<UsuarioDTO> list, y lo que ignora *-*}
    @Override
    List<UsuarioResponseDTO> toResponseDtoList(List<Usuario> list);

    // <--[UsuarioDTO dto, Usuario entity]--
    // ==>{void, y lo que ignora *id,pedidoList,usuarioDireccionList*}
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedidoList", ignore = true)
    @Mapping(target = "usuarioDireccionList", ignore = true)
    void updateFromUpdateDto(UsuarioUpdateDTO dto, @MappingTarget Usuario entity);

}
