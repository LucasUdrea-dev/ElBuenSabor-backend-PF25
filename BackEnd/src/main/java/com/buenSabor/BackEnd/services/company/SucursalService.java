/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.company;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalDTO;
import com.buenSabor.BackEnd.mapper.SucursalMapper;
import com.buenSabor.BackEnd.models.company.Empresa;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.ubicacion.Ciudad;
import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import com.buenSabor.BackEnd.models.ubicacion.Pais;
import com.buenSabor.BackEnd.models.ubicacion.Provincia;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.company.EmpresaRepository;
import com.buenSabor.BackEnd.repositories.company.SucursalRepository;
import com.buenSabor.BackEnd.repositories.ubicacion.CiudadRepository;
import com.buenSabor.BackEnd.repositories.ubicacion.DireccionRepository;
import com.buenSabor.BackEnd.repositories.ubicacion.PaisRepository;
import com.buenSabor.BackEnd.repositories.ubicacion.ProvinciaRepository;
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
    DireccionRepository direccionRepository;


    private final SucursalRepository sucursalRepository;
    private final EmpresaRepository empresaRepository;
    private final CiudadRepository ciudadRepository;
    private final ProvinciaRepository provinciaRepository;
    private final PaisRepository paisRepository;
    private final SucursalMapper sucursalMapper;

    public SucursalService(
            BeanRepository<Sucursal, Long> beanRepository,
            SucursalRepository sucursalRepository,
            EmpresaRepository empresaRepository,
            CiudadRepository ciudadRepository,
            ProvinciaRepository provinciaRepository,
            PaisRepository paisRepository,
            SucursalMapper sucursalMapper
    ) {
        super(beanRepository);
        this.sucursalRepository = sucursalRepository;
        this.empresaRepository = empresaRepository;
        this.ciudadRepository = ciudadRepository;
        this.provinciaRepository = provinciaRepository;
        this.paisRepository = paisRepository;
        this.sucursalMapper = sucursalMapper;
    }

    @Transactional
    public SucursalDTO crearSucursal(SucursalDTO dto) {
        // Obtener la empresa existente por ID (única entidad que debe existir previamente)
        Empresa empresa = empresaRepository.findById(dto.getEmpresa().getId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        // Mapear el DTO a entidad
        Sucursal sucursal = sucursalMapper.toEntity(dto);
        sucursal.setEmpresa(empresa);

        // Extraer la jerarquía de dirección
        Direccion direccion = sucursal.getDireccion();
        Ciudad ciudad = direccion.getCiudad();
        Provincia provincia = ciudad.getProvincia();
        Pais pais = provincia.getPais();

        // Guardar jerárquicamente las entidades nuevas
        pais = paisRepository.save(pais);
        provincia.setPais(pais);
        provincia = provinciaRepository.save(provincia);
        ciudad.setProvincia(provincia);
        ciudad = ciudadRepository.save(ciudad);
        direccion.setCiudad(ciudad);

        // Asignar la dirección con la ciudad ya persistida
        sucursal.setDireccion(direccion);

        // Guardar la sucursal completa
        Sucursal sucursalGuardada = sucursalRepository.save(sucursal);

        return sucursalMapper.toDto(sucursalGuardada);
    }

    @Transactional
    public SucursalDTO actualizarSucursal(Long id, SucursalDTO dto) {
        // Buscar la sucursal existente
        Sucursal sucursalExistente = sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        // Obtener la empresa asociada (solo por ID, debe existir)
        Empresa empresa = empresaRepository.findById(dto.getEmpresa().getId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        // Mapear DTO a entidad, mantener el ID original
        Sucursal sucursalActualizada = sucursalMapper.toEntity(dto);
        sucursalActualizada.setId(id);
        sucursalActualizada.setEmpresa(empresa);

        // Guardar la jerarquía completa desde cero (País → Provincia → Ciudad → Dirección)
        Pais pais = paisRepository.save(sucursalActualizada.getDireccion().getCiudad().getProvincia().getPais());
        Provincia provincia = sucursalActualizada.getDireccion().getCiudad().getProvincia();
        provincia.setPais(pais);
        provincia = provinciaRepository.save(provincia);

        Ciudad ciudad = sucursalActualizada.getDireccion().getCiudad();
        ciudad.setProvincia(provincia);
        ciudad = ciudadRepository.save(ciudad);

        Direccion direccion = sucursalActualizada.getDireccion();
        direccion.setCiudad(ciudad);
        sucursalActualizada.setDireccion(direccion);

        // Persistir sucursal actualizada
        Sucursal actualizada = sucursalRepository.save(sucursalActualizada);
        return sucursalMapper.toDto(actualizada);
    }


    @Transactional
    public void eliminarSucursal(Long id) {
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        // Soft delete
        sucursal.setExiste(false);
        sucursalRepository.save(sucursal);
    }

}