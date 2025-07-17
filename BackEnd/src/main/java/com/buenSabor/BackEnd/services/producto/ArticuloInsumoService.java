/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.company.SucursalRepository;
import com.buenSabor.BackEnd.repositories.producto.ArticuloInsumoRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author oscarloha
 */
@Service
public class ArticuloInsumoService extends BeanServiceImpl<ArticuloInsumo,Long>{
    
    @Autowired
    private ArticuloInsumoRepository articuloInsumoRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    public ArticuloInsumoService(BeanRepository<ArticuloInsumo, Long> beanRepository) {
        super(beanRepository);
    }

    @Override
    @Transactional
    public ArticuloInsumo save(ArticuloInsumo articuloInsumo) throws Exception{

        try {
            
            Optional<Sucursal> sucursalBD = sucursalRepository.findById(articuloInsumo.getStockArticuloInsumo().getSucursal().getId());

            Sucursal sucursalManaged = sucursalBD.get();

            articuloInsumo.getStockArticuloInsumo().setSucursal(sucursalManaged);
            articuloInsumo.getStockArticuloInsumo().setArticuloInsumo(articuloInsumo);

            return articuloInsumoRepository.save(articuloInsumo);

        } catch (Exception e) {
            System.err.println("Error al guardar Articulo Manufacturado: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Error al guardar el Articulo Manufacturado: " + e.getMessage(), e);
        }

    }

    @Override
    @Transactional
    public ArticuloInsumo update(Long id, ArticuloInsumo articuloInsumo) throws Exception{

        
        try {
            Optional<ArticuloInsumo> articuloInsumoBD = articuloInsumoRepository.findById(articuloInsumo.getId());
            
            if (!articuloInsumoBD.isPresent()) {
                throw new EntityNotFoundException("El articulo con id " + id + " no existe" );
            }

            Optional<Sucursal> sucursalBD = sucursalRepository.findById(articuloInsumo.getStockArticuloInsumo().getSucursal().getId());

            Sucursal sucursalManaged = sucursalBD.get();

            articuloInsumo.getStockArticuloInsumo().setSucursal(sucursalManaged);
            articuloInsumo.getStockArticuloInsumo().setArticuloInsumo(articuloInsumo);

            return articuloInsumoRepository.save(articuloInsumo);

        } catch (Exception e) {
            System.err.println("Error al guardar Articulo Manufacturado: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Error al guardar el Articulo Manufacturado: " + e.getMessage(), e);
        }

    }
    
    @Transactional
    public List<ArticuloInsumo> obtenerInsumosParaElaborar() throws Exception{

        try {
            return articuloInsumoRepository.findByExisteTrueAndEsParaElaborarTrue();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

}
