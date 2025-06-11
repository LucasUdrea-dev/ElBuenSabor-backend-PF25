package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.dto.venta.detallepedido.DetallePedidoDTO;
import com.buenSabor.BackEnd.dto.venta.detallepromocion.DetallePromocionDTO;
import com.buenSabor.BackEnd.dto.venta.pedido.PedidoDTO;
import com.buenSabor.BackEnd.mapper.DetallePedidoMapper;
import com.buenSabor.BackEnd.mapper.DetallePromocionMapper;
import com.buenSabor.BackEnd.mapper.DireccionMapper;
import com.buenSabor.BackEnd.mapper.PedidoMapper;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.models.venta.DetallePedido;
import com.buenSabor.BackEnd.models.venta.DetallePromocion;
import com.buenSabor.BackEnd.models.venta.DireccionPedido;
import com.buenSabor.BackEnd.models.venta.EstadoPedido;
import com.buenSabor.BackEnd.models.venta.Pedido;
import com.buenSabor.BackEnd.models.venta.Promocion;
import com.buenSabor.BackEnd.models.venta.TipoEnvio;
import com.buenSabor.BackEnd.models.venta.TipoPago;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.company.SucursalRepository;
import com.buenSabor.BackEnd.repositories.producto.ArticuloRepository;
import com.buenSabor.BackEnd.repositories.ubicacion.DireccionRepository;
import com.buenSabor.BackEnd.repositories.user.UsuarioRepository;
import com.buenSabor.BackEnd.repositories.venta.DetallePedidoRepository;
import com.buenSabor.BackEnd.repositories.venta.DetallePromocionRepository;
import com.buenSabor.BackEnd.repositories.venta.EstadoPedidoRepository;
import com.buenSabor.BackEnd.repositories.venta.PedidoRepository;
import com.buenSabor.BackEnd.repositories.venta.PromocionRepository;
import com.buenSabor.BackEnd.repositories.venta.TipoEnvioRepository;
import com.buenSabor.BackEnd.repositories.venta.TipoPagoRepository;
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

@Service
public class PedidoService extends BeanServiceImpl<Pedido, Long> {

    private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);

    private final PedidoRepository pedidoRepository;
    private final EstadoPedidoRepository estadoPedidoRepository;
    private final SucursalRepository sucursalRepository;
    private final TipoEnvioRepository tipoEnvioRepository;
    private final TipoPagoRepository tipoPagoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ArticuloRepository articuloRepository; 
    private final PromocionRepository promocionRepository; 
    private final DetallePedidoRepository detallePedidoRepository; 
    private final DetallePromocionRepository detallePromocionRepository; 
    private final DireccionRepository direccionPedidoRepository; 

    private final PedidoMapper pedidoMapper;
    private final DetallePedidoMapper detallePedidoMapper;
    private final DetallePromocionMapper detallePromocionMapper;
    private final DireccionMapper direccionPedidoMapper; 

    @Autowired
    public PedidoService(
            BeanRepository<Pedido, Long> beanRepository,
            PedidoRepository pedidoRepository,
            EstadoPedidoRepository estadoPedidoRepository,
            SucursalRepository sucursalRepository,
            TipoEnvioRepository tipoEnvioRepository,
            TipoPagoRepository tipoPagoRepository,
            UsuarioRepository usuarioRepository,
            ArticuloRepository articuloRepository,
            PromocionRepository promocionRepository,
            DetallePedidoRepository detallePedidoRepository,
            DetallePromocionRepository detallePromocionRepository,
            DireccionRepository direccionPedidoRepository,
            PedidoMapper pedidoMapper,
            DetallePedidoMapper detallePedidoMapper,
            DetallePromocionMapper detallePromocionMapper,
            DireccionMapper direccionPedidoMapper
    ) {
        super(beanRepository);
        this.pedidoRepository = pedidoRepository;
        this.estadoPedidoRepository = estadoPedidoRepository;
        this.sucursalRepository = sucursalRepository;
        this.tipoEnvioRepository = tipoEnvioRepository;
        this.tipoPagoRepository = tipoPagoRepository;
        this.usuarioRepository = usuarioRepository;
        this.articuloRepository = articuloRepository;
        this.promocionRepository = promocionRepository;
        this.detallePedidoRepository = detallePedidoRepository;
        this.detallePromocionRepository = detallePromocionRepository;
        this.direccionPedidoRepository = direccionPedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.detallePedidoMapper = detallePedidoMapper;
        this.detallePromocionMapper = detallePromocionMapper;
        this.direccionPedidoMapper = direccionPedidoMapper;
    }

    
    @Transactional
    public PedidoDTO findPedidoDTOById(Long id) throws Exception {
        return pedidoRepository.findById(id)
                .map(pedidoMapper::toDto)
                .orElse(null);
    }

    @Transactional
    public List<PedidoDTO> findAllPedidosDTO() throws Exception {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidoMapper.toDtoList(pedidos);
    }

    @Transactional
    public Page<PedidoDTO> findAllPedidosDTO(Pageable pageable) throws Exception {
        Page<Pedido> pedidosPage = pedidoRepository.findAll(pageable);
        return pedidosPage.map(pedidoMapper::toDto);
    }

    // --- Create Operation ---

//    @Transactional
//    public PedidoDTO crearPedido(PedidoDTO dto) throws Exception {
//        // 1. Fetch and set ManyToOne relationships (existing entities)
//        EstadoPedido estadoPedido = estadoPedidoRepository.findById(dto.getEstadoPedido().getId())
//                .orElseThrow(() -> new RuntimeException("Estado de Pedido con ID " + dto.getEstadoPedido().getId() + " no encontrado."));
//        Sucursal sucursal = sucursalRepository.findById(dto.getSucursal().getId())
//                .orElseThrow(() -> new RuntimeException("Sucursal con ID " + dto.getSucursal().getId() + " no encontrada."));
//        TipoEnvio tipoEnvio = tipoEnvioRepository.findById(dto.getTipoEnvio().getId())
//                .orElseThrow(() -> new RuntimeException("Tipo de Envío con ID " + dto.getTipoEnvio().getId() + " no encontrado."));
//        TipoPago tipoPago = tipoPagoRepository.findById(dto.getTipoPago().getId())
//                .orElseThrow(() -> new RuntimeException("Tipo de Pago con ID " + dto.getTipoPago().getId() + " no encontrado."));
//        Usuario usuario = usuarioRepository.findById(dto.getUsuario().getId())
//                .orElseThrow(() -> new RuntimeException("Usuario con ID " + dto.getUsuario().getId() + " no encontrado."));
//
//        // 2. Map the main PedidoDTO to Pedido entity (basic fields only)
//        // Note: Collections (detallePedidoList, detallePromocionList) and DireccionPedido
//        // are ignored by the mapper and will be handled manually here.
//        Pedido pedido = pedidoMapper.toEntity(dto);
//        pedido.setEstadoPedido(estadoPedido);
//        pedido.setSucursal(sucursal);
//        pedido.setTipoEnvio(tipoEnvio);
//        pedido.setTipoPago(tipoPago);
//        pedido.setUsuario(usuario);
//
//        // Initialize lists to avoid NullPointerExceptions later
//        pedido.setDetallePedidoList(new ArrayList<>());
//        pedido.setDetallePromocionList(new ArrayList<>());
//
//        // 3. Handle DireccionPedido (OneToOne)
//        if (dto.getDireccionPedido() != null) {
//            DireccionPedido direccionPedido = direccionPedidoMapper.toEntity(dto.getDireccionPedido());
//            direccionPedido.setPedido(pedido); // Set bidirectional relationship
//            pedido.setDireccionPedido(direccionPedido);
//            // DireccionPedido will be saved cascade if configured correctly in Pedido entity
//            // Or explicitly save: direccionPedidoRepository.save(direccionPedido);
//        }
//
//        // 4. Save the Pedido entity first to get its ID
//        Pedido savedPedido = pedidoRepository.save(pedido);
//
//        // 5. Handle DetallePedido (OneToMany)
//        if (dto.getDetallePedidoList() != null && !dto.getDetallePedidoList().isEmpty()) {
//            for (DetallePedidoDTO detalleDto : dto.getDetallePedidoList()) {
//                Articulo articulo = articuloRepository.findById(detalleDto.getArticulo().getId())
//                        .orElseThrow(() -> new RuntimeException("Artículo con ID " + detalleDto.getArticulo().getId() + " no encontrado para DetallePedido."));
//
//                DetallePedido detallePedido = detallePedidoMapper.toEntity(detalleDto);
//                detallePedido.setPedido(savedPedido); // Associate with the saved parent Pedido
//                detallePedido.setArticulo(articulo);
//                savedPedido.getDetallePedidoList().add(detallePedido); // Add to the collection
//            }
//            // If CascadeType.ALL is on Pedido.detallePedidoList, they will be saved when Pedido is saved/updated.
//            // Otherwise, save them explicitly: detallePedidoRepository.saveAll(savedPedido.getDetallePedidoList());
//        }
//
//        // 6. Handle DetallePromocion (OneToMany)
//        if (dto.getDetallePromocionList() != null && !dto.getDetallePromocionList().isEmpty()) {
//            for (DetallePromocionDTO detalleDto : dto.getDetallePromocionList()) {
//                Promocion promocion = promocionRepository.findById(detalleDto.getPromocion().getId())
//                        .orElseThrow(() -> new RuntimeException("Promoción con ID " + detalleDto.getPromocion().getId() + " no encontrada para DetallePromocion."));
//
//                DetallePromocion detallePromocion = detallePromocionMapper.toEntity(detalleDto);
//                detallePromocion.setPedido(savedPedido); // Associate with the saved parent Pedido
//                detallePromocion.setPromocion(promocion);
//                savedPedido.getDetallePromocionList().add(detallePromocion); // Add to the collection
//            }
//            // If CascadeType.ALL is on Pedido.detallePromocionList, they will be saved.
//            // Otherwise, save them explicitly: detallePromocionRepository.saveAll(savedPedido.getDetallePromocionList());
//        }
//
//        // Re-save the parent to ensure cascades or any updates based on children are flushed
//        // This might be redundant if cascades are correctly configured, but provides safety.
//        return pedidoMapper.toDto(pedidoRepository.save(savedPedido));
//    }
//
//
//    // --- Update Operation ---
//
//    @Transactional
//    public PedidoDTO actualizarPedido(Long id, PedidoDTO dto) throws Exception {
//        Pedido existingPedido = pedidoRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Pedido con ID " + id + " no encontrado para actualizar."));
//
//        // 1. Fetch and set ManyToOne relationships (similar to create)
//        EstadoPedido estadoPedido = estadoPedidoRepository.findById(dto.getEstadoPedido().getId())
//                .orElseThrow(() -> new RuntimeException("Estado de Pedido con ID " + dto.getEstadoPedido().getId() + " no encontrado."));
//        Sucursal sucursal = sucursalRepository.findById(dto.getSucursal().getId())
//                .orElseThrow(() -> new RuntimeException("Sucursal con ID " + dto.getSucursal().getId() + " no encontrada."));
//        TipoEnvio tipoEnvio = tipoEnvioRepository.findById(dto.getTipoEnvio().getId())
//                .orElseThrow(() -> new RuntimeException("Tipo de Envío con ID " + dto.getTipoEnvio().getId() + " no encontrado."));
//        TipoPago tipoPago = tipoPagoRepository.findById(dto.getTipoPago().getId())
//                .orElseThrow(() -> new RuntimeException("Tipo de Pago con ID " + dto.getTipoPago().getId() + " no encontrado."));
//        Usuario usuario = usuarioRepository.findById(dto.getUsuario().getId())
//                .orElseThrow(() -> new RuntimeException("Usuario con ID " + dto.getUsuario().getId() + " no encontrado."));
//
//        // 2. Update basic fields of existing Pedido entity using the mapper
//        // Collections are ignored by the mapper, handling them below.
//        pedidoMapper.updatePedidoFromDto(dto, existingPedido);
//        existingPedido.setEstadoPedido(estadoPedido);
//        existingPedido.setSucursal(sucursal);
//        existingPedido.setTipoEnvio(tipoEnvio);
//        existingPedido.setTipoPago(tipoPago);
//        existingPedido.setUsuario(usuario);
//
//        // 3. Handle DireccionPedido (OneToOne)
//        // This logic handles create, update, or delete of DireccionPedido
//        if (dto.getDireccionPedido() != null) {
//            if (existingPedido.getDireccionPedido() == null) {
//                // No existing DireccionPedido, create a new one
//                DireccionPedido newDireccionPedido = direccionPedidoMapper.toEntity(dto.getDireccionPedido());
//                newDireccionPedido.setPedido(existingPedido);
//                existingPedido.setDireccionPedido(newDireccionPedido);
//                // The cascade (if configured) will save it
//            } else {
//                // Existing DireccionPedido, update it
//                direccionPedidoMapper.updateDireccionPedidoFromDto(dto.getDireccionPedido(), existingPedido.getDireccionPedido());
//                // No need to set pedido again, it's already linked
//            }
//        } else {
//            // DTO has no DireccionPedido, but entity has one -> delete it
//            if (existingPedido.getDireccionPedido() != null) {
//                direccionPedidoRepository.delete(existingPedido.getDireccionPedido());
//                existingPedido.setDireccionPedido(null);
//            }
//        }
//
//
//        // 4. Handle DetallePedido (OneToMany) - Complex update logic
//        // Clear existing children to allow orphanRemoval to work (if configured on entity)
//        // OR, manage individual adds/updates/deletes. Clearing is simpler with orphanRemoval.
//        detallePedidoRepository.deleteAll(existingPedido.getDetallePedidoList()); // Delete old ones
//        existingPedido.getDetallePedidoList().clear(); // Clear the collection
//
//        if (dto.getDetallePedidoList() != null && !dto.getDetallePedidoList().isEmpty()) {
//            for (DetallePedidoDTO detalleDto : dto.getDetallePedidoList()) {
//                Articulo articulo = articuloRepository.findById(detalleDto.getArticulo().getId())
//                        .orElseThrow(() -> new RuntimeException("Artículo con ID " + detalleDto.getArticulo().getId() + " no encontrado para DetallePedido."));
//
//                DetallePedido newDetalle = detallePedidoMapper.toEntity(detalleDto);
//                newDetalle.setPedido(existingPedido); // Link to parent
//                newDetalle.setArticulo(articulo);
//                existingPedido.getDetallePedidoList().add(newDetalle);
//            }
//        }
//
//        // 5. Handle DetallePromocion (OneToMany) - Complex update logic
//        detallePromocionRepository.deleteAll(existingPedido.getDetallePromocionList()); // Delete old ones
//        existingPedido.getDetallePromocionList().clear(); // Clear the collection
//
//        if (dto.getDetallePromocionList() != null && !dto.getDetallePromocionList().isEmpty()) {
//            for (DetallePromocionDTO detalleDto : dto.getDetallePromocionList()) {
//                Promocion promocion = promocionRepository.findById(detalleDto.getPromocion().getId())
//                        .orElseThrow(() -> new RuntimeException("Promoción con ID " + detalleDto.getPromocion().getId() + " no encontrada para DetallePromocion."));
//
//                DetallePromocion newDetalle = detallePromocionMapper.toEntity(detalleDto);
//                newDetalle.setPedido(existingPedido); // Link to parent
//                newDetalle.setPromocion(promocion);
//                existingPedido.getDetallePromocionList().add(newDetalle);
//            }
//        }
//
//        // Save the updated parent entity
//        Pedido updatedPedido = pedidoRepository.save(existingPedido);
//        return pedidoMapper.toDto(updatedPedido);
//    }

    // --- Delete Operation ---

    @Transactional
    public void eliminarPedido(Long id) throws Exception {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido con ID " + id + " no encontrado para eliminar."));

        try {
            pedidoRepository.delete(pedido);
            logger.info("Pedido con ID {} eliminado con éxito.", id);
        } catch (Exception e) {
            logger.error("Error al intentar eliminar el pedido con ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error al eliminar el pedido con ID " + id + ": " + e.getMessage(), e);
        }
    }
}