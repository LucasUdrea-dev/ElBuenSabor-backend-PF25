package com.buenSabor.BackEnd.controllers.user;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.user.Telefono;
import com.buenSabor.BackEnd.services.user.TelefonoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/telefonos")
@CrossOrigin(origins = "*")
@Tag(name = "Telefono", description = "Operaciones relacionadas con entidad Telefono")
public class TelefonoController extends BeanControllerImpl<Telefono, TelefonoService> {
}
