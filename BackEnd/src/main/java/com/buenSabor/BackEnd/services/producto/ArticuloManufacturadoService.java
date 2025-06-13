/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.dto.producto.manufacturado.ArticuloManufacturadoDTO;
import com.buenSabor.BackEnd.mapper.ArticuloMapper;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.producto.*;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.company.SucursalRepository;
import com.buenSabor.BackEnd.repositories.producto.ArticuloInsumoRepository;
import com.buenSabor.BackEnd.repositories.producto.ArticuloManufacturadoRepository;
import com.buenSabor.BackEnd.repositories.producto.SubcategorioRepository;
import com.buenSabor.BackEnd.repositories.producto.UnidadMedidaRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author oscarloha
 */
@Service
public class ArticuloManufacturadoService extends BeanServiceImpl<ArticuloManufacturado,Long>{

    @Autowired
    ArticuloManufacturadoRepository manufacturadoRepository;
    @Autowired
    SucursalRepository sucursalRepository;
    @Autowired
    SubcategorioRepository subcategorioRepository;
    @Autowired
    UnidadMedidaRepository unidadMedidaRepository;
    @Autowired
    ArticuloInsumoRepository insumoRepository;
    @Autowired
    ArticuloMapper mapper;

    public ArticuloManufacturadoService(BeanRepository<ArticuloManufacturado, Long> beanRepository, ArticuloManufacturadoRepository manufacturadoRepository, SucursalRepository sucursalRepository, SubcategorioRepository subcategorioRepository, UnidadMedidaRepository unidadMedidaRepository, ArticuloInsumoRepository insumoRepository, ArticuloMapper mapper) {
        super(beanRepository);
        this.manufacturadoRepository = manufacturadoRepository;
        this.sucursalRepository = sucursalRepository;
        this.subcategorioRepository = subcategorioRepository;
        this.unidadMedidaRepository = unidadMedidaRepository;
        this.insumoRepository = insumoRepository;
        this.mapper = mapper;
    }
    @Transactional
    public ArticuloManufacturadoDTO crearManufacturado(ArticuloManufacturadoDTO dto) {

        // 1) Mapear DTO → Entidad
        ArticuloManufacturado manufacturado = mapper.toEntity(dto);

        // 2) Sustituir referencias por objetos managed
        Subcategoria subManaged = subcategorioRepository.getById(dto.getSubcategoria().getId());
        manufacturado.setSubcategoria(subManaged);

        UnidadMedida umManaged = unidadMedidaRepository.getById(dto.getUnidadMedida().getId());
        manufacturado.setUnidadMedida(umManaged);

        Sucursal sucManaged = sucursalRepository.getById(dto.getSucursalId());
        manufacturado.setSucursal(sucManaged);

        // 3) Crear detalles de insumo
        List<ArticuloManufacturadoDetalleInsumo> detalles = dto.getInsumos().stream()
                .map(d -> {
                    ArticuloManufacturadoDetalleInsumo det = new ArticuloManufacturadoDetalleInsumo();
                    det.setArticuloManufacturado(manufacturado);
                    det.setArticuloInsumo(
                            insumoRepository.getById(d.getArticuloInsumo().getId())
                    );
                    det.setCantidad(d.getCantidad());
                    return det;
                })
                .collect(Collectors.toList());
        manufacturado.setDetalleInsumos(detalles);

        // 4) Persistir
        ArticuloManufacturado saved = manufacturadoRepository.save(manufacturado);


        // 5) Rellenar el DTO con los IDs generados
        dto.setId(saved.getId());
        dto.setSucursalId(saved.getSucursal().getId());
        dto.setTiempoEstimado(saved.getTiempoEstimado());
        dto.setPreparacion(saved.getPreparacion());

        for (int i = 0; i < dto.getInsumos().size(); i++) {
            Long detalleId = saved.getDetalleInsumos().get(i).getId();
            dto.getInsumos().get(i).setId(detalleId);
        }

        // 6) Devolver el DTO “plano”
        return dto;
    }





}
