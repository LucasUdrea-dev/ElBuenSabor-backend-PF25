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
import jakarta.transaction.Transactional;
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
@Transactional
    public Sucursal crearSucursal(Sucursal sucursal, Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con id: " + id));

        if (sucursal.getNombre() == null || sucursal.getHoraApertura() == null || sucursal.getHoraCierre() == null) {
            throw new RuntimeException("Datos incompletos para la sucursal.");
        }

        if (sucursal.getDireccion() != null) {
            Direccion direccion = direccionRepository.save(sucursal.getDireccion());
            sucursal.setDireccion(direccion);
        }

        sucursal.setEmpresa(empresa);

        return sucursalRepository.save(sucursal);
    }
}