/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.services.producto;

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
    private ArticuloInsumoRepository articuloInsumoRepository;
    
    @Autowired
    private SubcategorioRepository subcategorioRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private StockArticuloInsumoRepository stockArticuloInsumoRepository;

    public ArticuloInsumoService(ArticuloInsumoRepository articuloInsumoRepository) {
        super(articuloInsumoRepository);
        this.articuloInsumoRepository = articuloInsumoRepository;
    }

    @Transactional
    public ArticuloInsumo crearInsumo(ArticuloInsumo insumo, Long sucursalId) {
        try {
            // Primero manejamos la unidad de medida
            if (insumo.getUnidadMedida() != null) {
                UnidadMedida unidadMedida;
                if (insumo.getUnidadMedida().getId() != null && insumo.getUnidadMedida().getId() > 0) {
                    // Si tiene ID válido, buscamos la unidad de medida existente
                    unidadMedida = unidadMedidaRepository.findById(insumo.getUnidadMedida().getId())
                            .orElseThrow(() -> new RuntimeException("No se encontró la unidad de medida con id: " + insumo.getUnidadMedida().getId()));
                } else {
                    // Si no tiene ID o es 0, buscamos por la unidad
                    unidadMedida = unidadMedidaRepository.findByUnidad(insumo.getUnidadMedida().getUnidad())
                            .orElseThrow(() -> new RuntimeException("No se encontró la unidad de medida: " + insumo.getUnidadMedida().getUnidad()));
                }
                insumo.setUnidadMedida(unidadMedida);
            }

            // Manejamos la subcategoría y categoría
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
                
                insumo.setSubcategoria(subcategoriaFinal);
            }

            // Manejamos el stock
            if (insumo.getStockArticuloInsumo() != null) {
                StockArticuloInsumo stock = insumo.getStockArticuloInsumo();
                stock.setArticuloInsumo(insumo);
                stock = stockArticuloInsumoRepository.save(stock);
                insumo.setStockArticuloInsumo(stock);
            }

            // Finalmente guardamos el insumo
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