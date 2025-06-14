package com.buenSabor.BackEnd.controllers.user;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.services.user.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuarios")
@Tag(name = "Usuario", description = "Operaciones relacionadas con entidad Usuario")
public class UsuarioController extends BeanControllerImpl<Usuario, UsuarioService> {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/direcciones/{id}")
    public ResponseEntity<?> obtenerDirecciones(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findDirecciones(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al encontrar las direcciones: " + e.getMessage());
        }
    }

}
