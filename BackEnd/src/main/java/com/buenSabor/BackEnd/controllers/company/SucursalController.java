/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.company;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.services.company.SucursalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author oscarloha
 */
@RestController
@RequestMapping("api/sucursales")
@Tag(name = "Sucursales", description = "Operaciones relacionadas con entidad Sucursal")
public class SucursalController extends BeanControllerImpl<Sucursal,SucursalService>{
    
}
