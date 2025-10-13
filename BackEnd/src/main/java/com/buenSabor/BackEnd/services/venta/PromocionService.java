package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.dto.venta.promocion.PromocionDTO;
import com.buenSabor.BackEnd.dto.venta.promocion.PromocionLiteDTO;
import com.buenSabor.BackEnd.dto.venta.promocionArticulo.PromocionArticuloDTO;
import com.buenSabor.BackEnd.mapper.PromocionMapper;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.models.venta.Promocion;
import com.buenSabor.BackEnd.models.venta.PromocionArticulo;
import com.buenSabor.BackEnd.models.venta.TipoPromocion;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.company.SucursalRepository;
import com.buenSabor.BackEnd.repositories.producto.ArticuloRepository;
import com.buenSabor.BackEnd.repositories.venta.PromocionRepository;
import com.buenSabor.BackEnd.repositories.venta.TipoPromocionRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.buenSabor.BackEnd.repositories.venta.DetallePromocionArticuloRepository;

@Service
public class PromocionService extends BeanServiceImpl<Promocion, Long> {

    private static final Logger logger = LoggerFactory.getLogger(PromocionService.class);

    private final PromocionRepository promocionRepository;
    private final ArticuloRepository articuloRepository;
    @SuppressWarnings("unused")
    private final DetallePromocionArticuloRepository promocionArticuloRepository;
    private final SucursalRepository sucursalRepository;
    private final TipoPromocionRepository tipoPromocionRepository;
    private final PromocionMapper promocionMapper;

    @Autowired
    public PromocionService(
            BeanRepository<Promocion, Long> beanRepository,
            PromocionRepository promocionRepository,
            ArticuloRepository articuloRepository,
            DetallePromocionArticuloRepository promocionArticuloRepository,
            SucursalRepository sucursalRepository,
            TipoPromocionRepository tipoPromocionRepository,
            PromocionMapper promocionMapper) {
        super(beanRepository);
        this.promocionRepository = promocionRepository;
        this.articuloRepository = articuloRepository;
        this.promocionArticuloRepository = promocionArticuloRepository;
        this.sucursalRepository = sucursalRepository;
        this.tipoPromocionRepository = tipoPromocionRepository;
        this.promocionMapper = promocionMapper;
    }

    @Transactional
    public PromocionDTO findPromocionDTOById(Long id) throws Exception {
        Promocion promocion = findById(id);
        if (promocion == null) {
            return null;
        }
        return promocionMapper.toDto(promocion);
    }

    @Transactional
    public List<PromocionDTO> findAllPromocionesDTO() throws Exception {
        List<Promocion> promociones = findAll(); // Using the BeanServiceImpl's findAll
        return promocionMapper.toDtoList(promociones);
    }

    @Transactional
    public Page<PromocionDTO> findAllPromocionesDTO(Pageable pageable) throws Exception {
        Page<Promocion> promocionesPage = findAll(pageable); // Using the BeanServiceImpl's findAll
        return promocionesPage.map(promocionMapper::toDto);
    }

    @Transactional
    public PromocionDTO crearPromocion(PromocionDTO dto) {
        logger.info("Creando nueva promoción: {}", dto.getDenominacion());
        
        // Asegurar que es una nueva promoción
        dto.setId(null);

        // Validar y obtener Sucursal existente
        if (dto.getSucursal() == null || dto.getSucursal().getId() == null) {
            throw new RuntimeException("Debe especificar una sucursal válida.");
        }
        Sucursal sucursal = sucursalRepository.findById(dto.getSucursal().getId())
                .orElseThrow(
                        () -> new RuntimeException("Sucursal con ID " + dto.getSucursal().getId() + " no encontrada."));

        // Validar y obtener TipoPromocion existente (NO crear uno nuevo)
        if (dto.getTipoPromocion() == null || dto.getTipoPromocion().getId() == null) {
            throw new RuntimeException("Debe especificar un tipo de promoción válido.");
        }
        TipoPromocion tipoPromocion = tipoPromocionRepository.findById(dto.getTipoPromocion().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Tipo de Promoción con ID " + dto.getTipoPromocion().getId() + " no encontrado."));
        
        logger.debug("TipoPromocion encontrado: {} (ID: {})", tipoPromocion.getTipoPromocion(), tipoPromocion.getId());

        // Mapear DTO a entidad (sin relaciones complejas)
        Promocion promocion = new Promocion();
        promocion.setDenominacion(dto.getDenominacion());
        promocion.setDescripcion(dto.getDescripcion());
        promocion.setPrecioRebajado(dto.getPrecioRebajado());
        promocion.setExiste(dto.getExiste() != null ? dto.getExiste() : true);
        promocion.setImagen(dto.getImagen());
        promocion.setSucursal(sucursal);
        promocion.setTipoPromocion(tipoPromocion);

        // Procesar artículos de la promoción (evitar duplicados)
        List<PromocionArticulo> promocionArticulos = new ArrayList<>();

        if (dto.getArticulos() != null && !dto.getArticulos().isEmpty()) {
            for (PromocionArticuloDTO paDto : dto.getArticulos()) {
                if (paDto.getArticulo() == null || paDto.getArticulo().getId() == null) {
                    throw new RuntimeException("Cada artículo debe tener un ID válido.");
                }
                
                // Verificar que el artículo existe
                Articulo articulo = articuloRepository.findById(paDto.getArticulo().getId())
                        .orElseThrow(() -> new RuntimeException(
                                "Artículo con ID " + paDto.getArticulo().getId() + " no encontrado."));

                // Verificar que no se duplique el artículo en la misma promoción
                boolean articuloDuplicado = promocionArticulos.stream()
                        .anyMatch(pa -> pa.getIdArticulo().getId().equals(articulo.getId()));
                
                if (articuloDuplicado) {
                    logger.warn("Artículo con ID {} ya está en la promoción, se omite duplicado.", articulo.getId());
                    continue;
                }

                PromocionArticulo promocionArticulo = new PromocionArticulo();
                promocionArticulo.setId(null); // Asegurar que es nuevo
                promocionArticulo.setCantidad(paDto.getCantidad());
                promocionArticulo.setIdArticulo(articulo);
                promocionArticulo.setIdPromocion(promocion); // Establecer relación bidireccional

                promocionArticulos.add(promocionArticulo);
            }
        }

        promocion.setPromocionArticuloList(promocionArticulos);

        // Guardar la promoción (cascade guardará los PromocionArticulo)
        Promocion savedPromocion = promocionRepository.save(promocion);
        logger.info("Promoción creada exitosamente con ID: {}", savedPromocion.getId());
        
        return promocionMapper.toDto(savedPromocion);
    }

    @Transactional
    public PromocionDTO actualizarPromocion(Long id, PromocionDTO dto) {
        logger.info("Actualizando promoción con ID: {}", id);
        
        // 1. Buscar la promoción existente
        Promocion existingPromocion = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoción con ID " + id + " no encontrada."));

        // 2. Validar y obtener entidades relacionadas (Sucursal, TipoPromocion)
        if (dto.getSucursal() == null || dto.getSucursal().getId() == null) {
            throw new RuntimeException("Debe especificar una sucursal válida.");
        }
        Sucursal sucursal = sucursalRepository.findById(dto.getSucursal().getId())
                .orElseThrow(
                        () -> new RuntimeException("Sucursal con ID " + dto.getSucursal().getId() + " no encontrada."));

        if (dto.getTipoPromocion() == null || dto.getTipoPromocion().getId() == null) {
            throw new RuntimeException("Debe especificar un tipo de promoción válido.");
        }
        TipoPromocion tipoPromocion = tipoPromocionRepository.findById(dto.getTipoPromocion().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Tipo de Promoción con ID " + dto.getTipoPromocion().getId() + " no encontrado."));
        
        logger.debug("TipoPromocion encontrado: {} (ID: {})", tipoPromocion.getTipoPromocion(), tipoPromocion.getId());

        // 3. Actualizar campos básicos
        if (dto.getDenominacion() != null) {
            existingPromocion.setDenominacion(dto.getDenominacion());
        }
        if (dto.getDescripcion() != null) {
            existingPromocion.setDescripcion(dto.getDescripcion());
        }
        if (dto.getPrecioRebajado() != null) {
            existingPromocion.setPrecioRebajado(dto.getPrecioRebajado());
        }
        if (dto.getExiste() != null) {
            existingPromocion.setExiste(dto.getExiste());
        }
        if (dto.getImagen() != null) {
            existingPromocion.setImagen(dto.getImagen());
        }
        existingPromocion.setSucursal(sucursal);
        existingPromocion.setTipoPromocion(tipoPromocion);

        // 4. Manejar artículos de la promoción (evitar duplicados)
        // Limpiar la lista existente (orphanRemoval se encargará de eliminar los huérfanos)
        existingPromocion.getPromocionArticuloList().clear();
        
        if (dto.getArticulos() != null && !dto.getArticulos().isEmpty()) {
            List<Long> articulosYaProcesados = new ArrayList<>();
            
            for (PromocionArticuloDTO paDto : dto.getArticulos()) {
                if (paDto.getArticulo() == null || paDto.getArticulo().getId() == null) {
                    throw new RuntimeException("Cada artículo debe tener un ID válido.");
                }
                
                Long articuloId = paDto.getArticulo().getId();
                
                // Verificar duplicados en el mismo request
                if (articulosYaProcesados.contains(articuloId)) {
                    logger.warn("Artículo con ID {} duplicado en el request, se omite.", articuloId);
                    continue;
                }
                
                Articulo articulo = articuloRepository.findById(articuloId)
                        .orElseThrow(() -> new RuntimeException(
                                "Artículo con ID " + articuloId + " no encontrado."));

                PromocionArticulo promocionArticulo = new PromocionArticulo();
                promocionArticulo.setId(null); // Crear nuevo registro
                promocionArticulo.setCantidad(paDto.getCantidad());
                promocionArticulo.setIdArticulo(articulo);
                promocionArticulo.setIdPromocion(existingPromocion);
                
                existingPromocion.getPromocionArticuloList().add(promocionArticulo);
                articulosYaProcesados.add(articuloId);
            }
        }

        // 5. Guardar la promoción actualizada
        Promocion updatedPromocion = promocionRepository.save(existingPromocion);
        logger.info("Promoción con ID {} actualizada exitosamente.", id);

        // 6. Convertir a DTO para la respuesta
        return promocionMapper.toDto(updatedPromocion);
    }

    @Transactional
    public void eliminarPromocion(Long id) {
        Promocion promocion = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoción con ID " + id + " no encontrada para eliminar."));

        try {
            promocion.setExiste(Boolean.FALSE);
            promocionRepository.save(promocion);
            logger.info("Promoción con ID {} eliminada con éxito.", id);
        } catch (Exception e) {
            logger.error("Error al intentar eliminar la promoción con ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error al eliminar la promoción con ID " + id + ": " + e.getMessage(), e);
        }
    }

    // @Transactional(readOnly = true)
    // public List<PromocionDTO> findPromocionesByHabilitada(boolean habilitada) {
    // List<Promocion> promociones =
    // promocionRepository.findByHabilitada(habilitada);
    // return promocionMapper.toDtoList(promociones);
    // }
    //
    // @Transactional(readOnly = true)
    // public List<PromocionDTO> findPromocionesByDenominacion(String denominacion)
    // {
    // List<Promocion> promociones =
    // promocionRepository.findByDenominacionContainingIgnoreCase(denominacion);
    // return promocionMapper.toDtoList(promociones);
    // }
    @Transactional
    public List<PromocionDTO> findPromocionesByDenominacion(String denominacion) {
        List<Promocion> promociones = promocionRepository.findByDenominacionContainingIgnoreCase(denominacion);
        return promocionMapper.toDtoList(promociones);
    }

    @Transactional
    public PromocionLiteDTO findPromocionLiteById(Long id) {
        return promocionRepository.findById(id)
                .map(promocion -> promocionMapper.toPromocionLiteDto(promocion))
                .orElse(null);
    }

    @Transactional
    public List<PromocionLiteDTO> findAllPromocionesLite() {
        List<Promocion> promociones = promocionRepository.findAll();
        return promocionMapper.toPromocionLiteDtoList(promociones);
    }

    @Transactional
    public List<PromocionLiteDTO> findAllPromocionesExistentesLite() {
        List<Promocion> promociones = promocionRepository.findByExisteTrue();
        return promocionMapper.toPromocionLiteDtoList(promociones);
    }

    @Transactional
    public List<PromocionDTO> findAllPromocionesExistentesDTO() {
        List<Promocion> promociones = promocionRepository.findByExisteTrue();
        return promocionMapper.toDtoList(promociones);
    }

    @Transactional
    public List<PromocionLiteDTO> findPromocionesExistentesLiteBySucursal(Long sucursalId) {
        List<Promocion> promociones = promocionRepository.findByExisteTrueAndSucursalId(sucursalId);
        return promocionMapper.toPromocionLiteDtoList(promociones);
    }

    @Transactional
    public List<PromocionDTO> findPromocionesExistentesDTOBySucursal(Long sucursalId) {
        List<Promocion> promociones = promocionRepository.findByExisteTrueAndSucursalId(sucursalId);
        return promocionMapper.toDtoList(promociones);
    }
}
