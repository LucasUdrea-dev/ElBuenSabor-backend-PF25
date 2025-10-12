/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.dto.producto.insumo.InsumoDTO;
import com.buenSabor.BackEnd.dto.producto.stock.StockDTO;
import com.buenSabor.BackEnd.mapper.ArticuloMapper;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.Subcategoria;
import com.buenSabor.BackEnd.models.producto.UnidadMedida;
import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import com.buenSabor.BackEnd.repositories.producto.ArticuloInsumoRepository;
import com.buenSabor.BackEnd.repositories.producto.SubcategorioRepository;
import com.buenSabor.BackEnd.repositories.producto.UnidadMedidaRepository;
import com.buenSabor.BackEnd.repositories.producto.StockArticuloInsumoRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author oscarloha
 */
@Service
@Transactional
public class ArticuloInsumoService extends BeanServiceImpl<ArticuloInsumo, Long> {
    @Autowired
    private ArticuloMapper articuloMapper;

    @Autowired
    private ArticuloInsumoRepository articuloInsumoRepository;

    @Autowired
    private SubcategorioRepository subcategorioRepository;

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private StockArticuloInsumoRepository stockArticuloInsumoRepository;
    @Autowired
    private com.buenSabor.BackEnd.repositories.company.SucursalRepository sucursalRepository;

    public ArticuloInsumoService(ArticuloInsumoRepository articuloInsumoRepository) {
        super(articuloInsumoRepository);
        this.articuloInsumoRepository = articuloInsumoRepository;
    }

    @Transactional
    public ArticuloInsumo crearInsumo(InsumoDTO dto, Long sucursalId) {
        try {
            // Mapea DTO → entidad
            ArticuloInsumo insumo = articuloMapper.toEntity(dto);

            Long subcategoriaId = dto.getSubcategoria().getId();
            Subcategoria subManaged = subcategorioRepository.findById(subcategoriaId)
                    .orElseThrow(() -> new EntityNotFoundException("Subcategoria no encontrada"));
            insumo.setSubcategoria(subManaged);

            Long unidadMedidaId = dto.getUnidadMedida().getId();
            UnidadMedida umManaged = unidadMedidaRepository.findById(unidadMedidaId)
                    .orElseThrow(() -> new EntityNotFoundException("Unidad de medida no encontrada"));
            insumo.setUnidadMedida(umManaged);

            insumo.setPrecioCompra(dto.getPrecioCompra());

            StockArticuloInsumo stock = insumo.getStockArticuloInsumo();
            Long sucId = dto.getStockArticuloInsumo().getSucursalId();
            Sucursal sucManaged = sucursalRepository.findById(sucId)
                    .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada"));
            stock.setSucursal(sucManaged);
            stock.setArticuloInsumo(insumo);
            insumo.setStockArticuloInsumo(stock);

            return articuloInsumoRepository.save(insumo);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el insumo: " + e.getMessage(), e);
        }
    }

    @Transactional
    public ArticuloInsumo actualizar(Long id, InsumoDTO dto) {
        try {
            // Recupera el insumo existente
            ArticuloInsumo insumo = articuloInsumoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Insumo con id " + id + " no existe"));

            // Actualiza campos básicos
            insumo.setNombre(dto.getNombre());
            insumo.setDescripcion(dto.getDescripcion());
            insumo.setPrecio(dto.getPrecio());
            insumo.setExiste(dto.getExiste());
            insumo.setEsParaElaborar(dto.getEsParaElaborar());
            insumo.setImagenArticulo(dto.getImagenArticulo());
            insumo.setPrecioCompra(dto.getPrecioCompra());

            // Reemplaza subcategoría y unidadMedida
            Subcategoria subManaged = subcategorioRepository.findById(dto.getSubcategoria().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Subcategoria no encontrada"));
            insumo.setSubcategoria(subManaged);

            UnidadMedida umManaged = unidadMedidaRepository.findById(dto.getUnidadMedida().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Unidad de medida no encontrada"));
            insumo.setUnidadMedida(umManaged);

            // actualizo o creo stock
            StockDTO stockDto = dto.getStockArticuloInsumo();
            StockArticuloInsumo stock;
            if (stockDto.getId() != null && stockDto.getId() > 0) {

                stock = stockArticuloInsumoRepository.findById(stockDto.getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Stock con id " + stockDto.getId() + " no existe"));
            } else {
                stock = new StockArticuloInsumo();
            }
            stock.setMaxStock(dto.getStockArticuloInsumo().getMaxStock());
            stock.setCantidad(dto.getStockArticuloInsumo().getCantidad());
            Sucursal sucManaged = sucursalRepository.findById(dto.getStockArticuloInsumo().getSucursalId())
                    .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada"));
            stock.setSucursal(sucManaged);
            insumo.setStockArticuloInsumo(stock);

            // Guarda cambios
            return articuloInsumoRepository.save(insumo);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el insumo: " + e.getMessage(), e);
        }
    }

    @Transactional
    public ArticuloInsumo eliminarLogico(Long id) {
        // Recupero articulo a eliminar
        ArticuloInsumo insumo = articuloInsumoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Insumo con id " + id + " no existe"));

        // verifico que el articulo existe(atributo)
        if (!insumo.getExiste()) {
            throw new IllegalStateException(
                    "El insumo con id " + id + " ya no existe");
        }

        // Seteo valor y guardo
        insumo.setExiste(false);
        return articuloInsumoRepository.save(insumo);
    }

    public List<ArticuloInsumo> buscarPorNombre(String nombre) throws Exception {
        try {
            return articuloInsumoRepository.findByNombreContainingIgnoreCase(nombre);
        } catch (Exception e) {
            throw new Exception("Error al buscar por nombre: " + e.getMessage());
        }
    }

    public List<ArticuloInsumo> buscarPorSubcategoria(Long subCategoriaId) throws Exception {
        try {
            return articuloInsumoRepository.findBySubcategoria_Id(subCategoriaId);
        } catch (Exception e) {
            throw new Exception("Error al buscar por subcategoría: " + e.getMessage());
        }
    }

    public List<ArticuloInsumo> findByExisteTrue() throws Exception {
        try {
            return articuloInsumoRepository.findByExisteTrue();
        } catch (Exception e) {
            throw new Exception("Error al buscar insumos disponibles: " + e.getMessage());
        }
    }

}