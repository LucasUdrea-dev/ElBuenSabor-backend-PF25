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
    private com.buenSabor.BackEnd.repositories.company.SucursalRepository sucursalRepository;

    public ArticuloInsumoService(ArticuloInsumoRepository articuloInsumoRepository) {
        super(articuloInsumoRepository);
        this.articuloInsumoRepository = articuloInsumoRepository;
    }

    @Transactional
    public ArticuloInsumo crearInsumo(InsumoDTO dto, Long sucursalId) {
        try {
            // Validar que el stock venga en el DTO
            if (dto.getStockArticuloInsumo() == null) {
                throw new IllegalArgumentException("El stock del insumo es requerido");
            }
            if (sucursalId == null) {
                throw new IllegalArgumentException("El ID de la sucursal es requerido");
            }

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

            // Manejo del Stock
            StockArticuloInsumo stock = insumo.getStockArticuloInsumo();
            if (stock == null) {
                stock = new StockArticuloInsumo();
            }

            // Validar que la cantidad no sea negativa
            if (dto.getStockArticuloInsumo().getCantidad() < 0) {
                throw new IllegalArgumentException("El stock no puede ser negativo");
            }

            // Asignar valores del stock desde el DTO
            stock.setCantidad(dto.getStockArticuloInsumo().getCantidad());
            stock.setMinStock(dto.getStockArticuloInsumo().getMinStock());

            Sucursal sucManaged = sucursalRepository.findById(sucursalId)
                    .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada"));
            stock.setSucursal(sucManaged);

            // Establecer relación bidireccional
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

            // Actualiza campos básicos usando el mapper
            articuloMapper.updateFromDto(dto, insumo);

            if (dto.getSubcategoria() != null && dto.getSubcategoria().getId() != null) {
                Subcategoria subManaged = subcategorioRepository.findById(dto.getSubcategoria().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Subcategoria no encontrada"));
                insumo.setSubcategoria(subManaged);
            }

            if (dto.getUnidadMedida() != null && dto.getUnidadMedida().getId() != null) {
                UnidadMedida umManaged = unidadMedidaRepository.findById(dto.getUnidadMedida().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Unidad de medida no encontrada"));
                insumo.setUnidadMedida(umManaged);
            }

            // actualizo o creo stock
            if (dto.getStockArticuloInsumo() != null) {
                if (dto.getStockArticuloInsumo().getCantidad() < 0) {
                    throw new IllegalArgumentException("El stock no puede ser negativo");
                }

                StockArticuloInsumo stock = insumo.getStockArticuloInsumo();
                if (stock == null) {
                    stock = new StockArticuloInsumo();
                    stock.setArticuloInsumo(insumo);
                    insumo.setStockArticuloInsumo(stock);
                }

                stock.setMinStock(dto.getStockArticuloInsumo().getMinStock());
                stock.setCantidad(dto.getStockArticuloInsumo().getCantidad());

                Long sucursalId = dto.getStockArticuloInsumo().getSucursalId();
                if (sucursalId != null) {
                    Sucursal sucManaged = sucursalRepository.findById(sucursalId)
                            .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada"));
                    stock.setSucursal(sucManaged);
                }
            }

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