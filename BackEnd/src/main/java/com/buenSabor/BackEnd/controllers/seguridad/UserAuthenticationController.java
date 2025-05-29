package com.buenSabor.BackEnd.controllers.seguridad;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import com.buenSabor.BackEnd.services.seguridad.UserAuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/userAuth")
@CrossOrigin(origins = "*")
@Tag(name = "UserAuthentication", description = "Operaciones relacionadas con entidad UserAuthentication")
public class UserAuthenticationController extends BeanControllerImpl<UserAuthentication, UserAuthenticationService> {
}
