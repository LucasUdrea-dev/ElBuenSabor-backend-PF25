/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.empresa;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.empresa.Empresa;
import com.buenSabor.BackEnd.services.empresa.EmpresaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author oscarloha
 */
@RestController
@RequestMapping("api/empresas")
@Tag(name = "Empresas", description = "Operaciones relacionadas con entidad Empresa")
public class EmpresaController extends BeanControllerImpl<Empresa,EmpresaService> {
    
}
