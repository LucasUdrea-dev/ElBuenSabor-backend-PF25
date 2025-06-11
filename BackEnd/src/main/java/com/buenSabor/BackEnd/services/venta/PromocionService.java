package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.dto.venta.promocion.PromocionDTO;
import com.buenSabor.BackEnd.dto.venta.promocion.PromocionLiteDTO;
import com.buenSabor.BackEnd.dto.venta.promocionArticulo.PromocionArticuloDTO;
import com.buenSabor.BackEnd.mapper.PromocionArticuloMapper;
import com.buenSabor.BackEnd.mapper.PromocionMapper;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.models.venta.Promocion;
import com.buenSabor.BackEnd.models.venta.PromocionArticulo;
import com.buenSabor.BackEnd.models.venta.TipoPromocion;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.company.SucursalRepository;
import com.buenSabor.BackEnd.repositories.producto.ArticuloRepository;
import com.buenSabor.BackEnd.repositories.venta.PromocionArticuloRepository;
import com.buenSabor.BackEnd.repositories.venta.PromocionRepository;
import com.buenSabor.BackEnd.repositories.venta.TipoPromocionRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PromocionService extends BeanServiceImpl<Promocion, Long> {

    private static final Logger logger = LoggerFactory.getLogger(PromocionService.class);

    private final PromocionRepository promocionRepository;
    private final ArticuloRepository articuloRepository;
    private final PromocionArticuloRepository promocionArticuloRepository;
    private final SucursalRepository sucursalRepository;
    private final TipoPromocionRepository tipoPromocionRepository;
    private final PromocionMapper promocionMapper;
    private final PromocionArticuloMapper promocionArticuloMapper;

    @Autowired
    public PromocionService(
            BeanRepository<Promocion, Long> beanRepository,
            PromocionRepository promocionRepository,
            ArticuloRepository articuloRepository,
            PromocionArticuloRepository promocionArticuloRepository,
            SucursalRepository sucursalRepository,
            TipoPromocionRepository tipoPromocionRepository,
            PromocionMapper promocionMapper,
            PromocionArticuloMapper promocionArticuloMapper
    ) {
        super(beanRepository);
        this.promocionRepository = promocionRepository;
        this.articuloRepository = articuloRepository;
        this.promocionArticuloRepository = promocionArticuloRepository;
        this.sucursalRepository = sucursalRepository;
        this.tipoPromocionRepository = tipoPromocionRepository;
        this.promocionMapper = promocionMapper;
        this.promocionArticuloMapper = promocionArticuloMapper;
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
        // 1. Fetch related entities (Sucursal, TipoPromocion) that must exist
        Sucursal sucursal = sucursalRepository.findById(dto.getSucursal().getId())
                .orElseThrow(() -> new RuntimeException("Sucursal con ID " + dto.getSucursal().getId() + " no encontrada."));

        TipoPromocion tipoPromocion = tipoPromocionRepository.findById(dto.getTipoPromocion().getId())
                .orElseThrow(() -> new RuntimeException("Tipo de Promoción con ID " + dto.getTipoPromocion().getId() + " no encontrado."));

        // 2. Map the DTO to the main Promocion entity
        Promocion promocion = promocionMapper.toEntity(dto);
        promocion.setSucursal(sucursal);
        promocion.setTipoPromocion(tipoPromocion);

        // 3. Handle nested PromocionArticulo entities
        Set<PromocionArticulo> promocionArticulos = new HashSet<>();
        if (dto.getArticulos() != null) {
            for (PromocionArticuloDTO paDto : dto.getArticulos()) {
                Articulo articulo = articuloRepository.findById(paDto.getArticulo().getId())
                        .orElseThrow(() -> new RuntimeException("Artículo con ID " + paDto.getArticulo().getId() + " no encontrado."));

                PromocionArticulo promocionArticulo = promocionArticuloMapper.toEntity(paDto);
                promocionArticulo.setIdArticulo(articulo);
                promocionArticulo.setIdPromocion(promocion); // Associate with the current promotion
                promocionArticulos.add(promocionArticulo);
            }
        }
        promocion.setPromocionArticuloList(new ArrayList<>(promocionArticulos));

        // 4. Save the main Promocion entity (Cascading will save PromocionArticulo entities)
        Promocion savedPromocion = promocionRepository.save(promocion);

        // 5. Convert the saved entity back to DTO for response
        return promocionMapper.toDto(savedPromocion);
    }

    @Transactional
    public PromocionDTO actualizarPromocion(Long id, PromocionDTO dto) {
        // 1. Find the existing Promocion
        Promocion existingPromocion = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoción con ID " + id + " no encontrada.")); // Or custom exception

        // 2. Fetch related entities (Sucursal, TipoPromocion)
        Sucursal sucursal = sucursalRepository.findById(dto.getSucursal().getId())
                .orElseThrow(() -> new RuntimeException("Sucursal con ID " + dto.getSucursal().getId() + " no encontrada."));

        TipoPromocion tipoPromocion = tipoPromocionRepository.findById(dto.getTipoPromocion().getId())
                .orElseThrow(() -> new RuntimeException("Tipo de Promoción con ID " + dto.getTipoPromocion().getId() + " no encontrado."));

        // 3. Update basic fields of the existing Promocion using MapStruct update method
        promocionMapper.updatePromocionFromDto(dto, existingPromocion); // Update only non-null fields
        existingPromocion.setSucursal(sucursal);
        existingPromocion.setTipoPromocion(tipoPromocion);

        // 4. Handle nested PromocionArticulo entities (requires careful handling for updates)
        existingPromocion.getPromocionArticuloList().clear();
        if (dto.getArticulos() != null) {
            for (PromocionArticuloDTO paDto : dto.getArticulos()) {
                Articulo articulo = articuloRepository.findById(paDto.getArticulo().getId())
                        .orElseThrow(() -> new RuntimeException("Artículo con ID " + paDto.getArticulo().getId() + " no encontrado."));

                PromocionArticulo promocionArticulo = promocionArticuloMapper.toEntity(paDto);
                promocionArticulo.setIdArticulo(articulo);
                promocionArticulo.setIdPromocion(existingPromocion);
                existingPromocion.getPromocionArticuloList().add(promocionArticulo);
            }
        }

        // 5. Save the updated Promocion
        Promocion updatedPromocion = promocionRepository.save(existingPromocion);

        // 6. Convert the updated entity back to DTO for response
        return promocionMapper.toDto(updatedPromocion);
    }

    @Transactional
    public void eliminarPromocion(Long id) {
        Promocion promocion = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoción con ID " + id + " no encontrada para eliminar."));

        try {
            promocionRepository.delete(promocion);
            logger.info("Promoción con ID {} eliminada con éxito.", id);
        } catch (Exception e) {
            logger.error("Error al intentar eliminar la promoción con ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error al eliminar la promoción con ID " + id + ": " + e.getMessage(), e);
        }
    }

//    @Transactional(readOnly = true)
//    public List<PromocionDTO> findPromocionesByHabilitada(boolean habilitada) {
//        List<Promocion> promociones = promocionRepository.findByHabilitada(habilitada);
//        return promocionMapper.toDtoList(promociones);
//    }
//
//    @Transactional(readOnly = true)
//    public List<PromocionDTO> findPromocionesByDenominacion(String denominacion) {
//        List<Promocion> promociones = promocionRepository.findByDenominacionContainingIgnoreCase(denominacion);
//        return promocionMapper.toDtoList(promociones);
//    }
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
