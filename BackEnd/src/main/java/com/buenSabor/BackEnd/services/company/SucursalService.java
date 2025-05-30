/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.company;

import com.buenSabor.BackEnd.models.company.Empresa;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.company.EmpresaRepository;
import com.buenSabor.BackEnd.repositories.company.SucursalRepository;
import com.buenSabor.BackEnd.repositories.ubicacion.DireccionRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author oscarloha
 */
@Service
public class SucursalService extends BeanServiceImpl<Sucursal, Long> {

    @Autowired
    EmpresaRepository empresaRepository;
    @Autowired
    SucursalRepository sucursalRepository;
    @Autowired
    DireccionRepository direccionRepository;

    public SucursalService(BeanRepository<Sucursal, Long> beanRepository) {
        super(beanRepository);
    }

    public Sucursal crearSucursal(Sucursal sucursal, Long id) {

        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> {
                    return new RuntimeException("Empresa no encontrada con id: " + id);
                });
        Direccion direccion = direccionRepository.save(sucursal.getDireccion());
        sucursal.setDireccion(direccion);
        sucursalRepository.save(sucursal);

        sucursal.setEmpresa(empresa);

        return sucursalRepository.save(sucursal);
    }

}
