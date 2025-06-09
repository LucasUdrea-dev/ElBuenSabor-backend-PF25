/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.dto.seguridad.autenticacion.UserAuthenticationResponseDTO;
import com.buenSabor.BackEnd.dto.seguridad.rol.RolResponseDTO;
import com.buenSabor.BackEnd.dto.user.telefono.TelefonoResponseDTO;
import com.buenSabor.BackEnd.dto.user.usuario.UsuarioResponseDTO;
import com.buenSabor.BackEnd.models.seguridad.Rol;
import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import com.buenSabor.BackEnd.models.user.Telefono;
import com.buenSabor.BackEnd.models.user.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author oscarloha
 */
@Mapper (componentModel = "spring")
public interface UsuarioMapper {
    
    UsuarioMapper mapper = Mappers.getMapper(UsuarioMapper.class);
//    
//    Usuario usuarioResponseDtoToUsuario(UsuarioResponseDTO usuarioDto);
//
//    UsuarioResponseDTO usuarioToUsuarioResponseDTO(Usuario usuario);
//
//    // MapStruct mapea automáticamente los atributos con el mismo nombre,
//    // pero para atributos anidados, necesitas declarar los mappers correspondientes:
//    Telefono telefonoResponseDtoToTelefono(TelefonoResponseDTO dto);
//    TelefonoResponseDTO telefonoToTelefonoResponseDTO(Telefono telefono);
//
//    Direccion direccionResponseDtoToDireccion(DireccionResponseDTO dto);
//    DireccionResponseDTO direccionToDireccionResponseDTO(Direccion direccion);
//
//    Rol rolResponseDtoToRol(RolResponseDTO dto);
//    RolResponseDTO rolToRolResponseDTO(Rol rol);
//
//    UserAuthentication userAuthDtoToUserAuth(UserAuthenticationResponseDTO dto);
//    UserAuthenticationResponseDTO userAuthToUserAuthDto(UserAuthentication auth);
    
    
    
    
}
