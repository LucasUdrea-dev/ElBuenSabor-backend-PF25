/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.dto.producto.insumo.InsumoDTO;
import com.buenSabor.BackEnd.mapper.ArticuloMapper;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.Subcategoria;
import com.buenSabor.BackEnd.models.producto.Categoria;
import com.buenSabor.BackEnd.models.producto.UnidadMedida;
import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import com.buenSabor.BackEnd.repositories.producto.ArticuloInsumoRepository;
import com.buenSabor.BackEnd.repositories.producto.SubcategorioRepository;
import com.buenSabor.BackEnd.repositories.producto.CategoriaRepository;
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
    private CategoriaRepository categoriaRepository;

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
            // 1) Mapea DTO → entidad
            ArticuloInsumo insumo = articuloMapper.toEntity(dto);

            Long subcategoriaId = dto.getSubcategoria().getId();
            Subcategoria subManaged = subcategorioRepository.getById(subcategoriaId);
            insumo.setSubcategoria(subManaged);

            Long unidadMedidaId = dto.getUnidadMedida().getId();
            UnidadMedida umManaged = unidadMedidaRepository.getById(unidadMedidaId);
            insumo.setUnidadMedida(umManaged);

            insumo.setPrecioCompra(dto.getPrecioCompra());

            StockArticuloInsumo stock = insumo.getStockArticuloInsumo();
            Long sucId = dto.getStockArticuloInsumo().getSucursalId();
            Sucursal sucManaged = sucursalRepository.getById(sucId);
            stock.setSucursal(sucManaged);
            insumo.setStockArticuloInsumo(stock);

            // 6) Guarda ArticuloInsumo + Stock (cascade ALL en la relación 1–1)
            return articuloInsumoRepository.save(insumo);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el insumo: " + e.getMessage(), e);
        }
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
    
    @Transactional
    public ArticuloInsumo actualizar(Long id, ArticuloInsumo insumo) {
        try {
            // Verificar si el insumo existe
            ArticuloInsumo insumoExistente = articuloInsumoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No se encontró el insumo con id: " + id));

            // Actualizar campos básicos
            insumoExistente.setNombre(insumo.getNombre());
            insumoExistente.setPrecio(insumo.getPrecio());
            insumoExistente.setImagenArticulo(insumo.getImagenArticulo());
            insumoExistente.setDescripcion(insumo.getDescripcion());
            insumoExistente.setExiste(insumo.getExiste());
            insumoExistente.setEsParaElaborar(insumo.getEsParaElaborar());
            insumoExistente.setPrecioCompra(insumo.getPrecioCompra());

            // Manejar unidad de medida
            if (insumo.getUnidadMedida() != null && insumo.getUnidadMedida().getId() != null) {
                UnidadMedida unidadMedida = unidadMedidaRepository.findById(insumo.getUnidadMedida().getId())
                        .orElseThrow(() -> new RuntimeException("No se encontró la unidad de medida con id: " + insumo.getUnidadMedida().getId()));
                insumoExistente.setUnidadMedida(unidadMedida);
            }

            // Manejar subcategoría y categoría
            if (insumo.getSubcategoria() != null) {
                final Subcategoria subcategoriaOriginal = insumo.getSubcategoria();
                Subcategoria subcategoriaFinal;
                
                // Si la subcategoría tiene ID, la buscamos
                if (subcategoriaOriginal.getId() != null && subcategoriaOriginal.getId() > 0) {
                    subcategoriaFinal = subcategorioRepository.findById(subcategoriaOriginal.getId())
                            .orElseThrow(() -> new RuntimeException("No se encontró la subcategoría con id: " + subcategoriaOriginal.getId()));
                } else {
                    // Si la subcategoría no tiene ID, manejamos su categoría
                    if (subcategoriaOriginal.getCategoria() != null) {
                        final Categoria categoriaOriginal = subcategoriaOriginal.getCategoria();
                        Categoria categoriaFinal;
                        
                        // Si la categoría tiene ID, la buscamos
                        if (categoriaOriginal.getId() != null && categoriaOriginal.getId() > 0) {
                            categoriaFinal = categoriaRepository.findById(categoriaOriginal.getId())
                                    .orElseThrow(() -> new RuntimeException("No se encontró la categoría con id: " + categoriaOriginal.getId()));
                        } else {
                            // Si la categoría no tiene ID, la guardamos
                            categoriaFinal = categoriaRepository.save(categoriaOriginal);
                        }
                        subcategoriaOriginal.setCategoria(categoriaFinal);
                    }
                    
                    // Guardamos la subcategoría
                    subcategoriaFinal = subcategorioRepository.save(subcategoriaOriginal);
                }
                
                insumoExistente.setSubcategoria(subcategoriaFinal);
            }

            // Manejar el stock
            if (insumo.getStockArticuloInsumo() != null) {
                final StockArticuloInsumo stockNuevo = insumo.getStockArticuloInsumo();
                
                // Buscar el stock existente por el ID del insumo
                StockArticuloInsumo stockExistente = stockArticuloInsumoRepository.findByArticuloInsumoId(id)
                        .orElse(null);
                
                if (stockExistente != null) {
                    // Actualizar el stock existente
                    stockExistente.setCantidad(stockNuevo.getCantidad());
                    stockExistente.setMaxStock(stockNuevo.getMaxStock());
                    stockExistente = stockArticuloInsumoRepository.save(stockExistente);
                    insumoExistente.setStockArticuloInsumo(stockExistente);
                } else {
                    // Si no existe, crear uno nuevo
                    stockNuevo.setArticuloInsumo(insumoExistente);
                    StockArticuloInsumo stockGuardado = stockArticuloInsumoRepository.save(stockNuevo);
                    insumoExistente.setStockArticuloInsumo(stockGuardado);
                }
            }

            return articuloInsumoRepository.save(insumoExistente);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el insumo: " + e.getMessage(), e);
        }
    }
}