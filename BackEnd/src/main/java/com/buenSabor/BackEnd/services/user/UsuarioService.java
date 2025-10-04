/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buenSabor.BackEnd.services.user;

import com.buenSabor.BackEnd.dto.user.usuario.UsuarioDTO;
import com.buenSabor.BackEnd.models.user.Usuario;
import java.util.List;

/**
 *
 * @author oscarloha
 */
public interface UsuarioService {
    //UsuarioDTO crearUsuario(UsuarioDTO dto);
    List<Usuario> findAll();
    List<Usuario> findAllExistente();
    Usuario findById(Long id);
    UsuarioDTO actualizarUsuario(Long id, UsuarioDTO dto);
    void eliminarUsuario(Long id);
}
