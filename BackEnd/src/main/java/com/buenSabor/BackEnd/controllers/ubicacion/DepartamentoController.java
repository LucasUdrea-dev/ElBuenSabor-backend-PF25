/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.ubicacion;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.ubicacion.Departamento;
import com.buenSabor.BackEnd.services.ubicacion.DepartamentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author oscarloha
 */
@RestController
@RequestMapping("api/Departamento")
@Tag(name = "Departamento", description = "Operaciones relacionadas con entidad Departamento")
public class DepartamentoController extends BeanControllerImpl<Departamento,DepartamentoService>{
    
}
