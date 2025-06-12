package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.repositories.venta.EstadoPedidoRepository;
import com.buenSabor.BackEnd.repositories.venta.PedidoRepository;
import com.buenSabor.BackEnd.repositories.venta.PromocionRepository;
import com.buenSabor.BackEnd.repositories.venta.TipoEnvioRepository;
import com.buenSabor.BackEnd.repositories.venta.TipoPagoRepository;
import com.buenSabor.BackEnd.dto.venta.detallepedido.DetallePedidoDTO;
import com.buenSabor.BackEnd.dto.venta.detallepromocion.DetallePromocionDTO;
import com.buenSabor.BackEnd.dto.venta.pedido.PedidoConDireccionDTO;


import com.buenSabor.BackEnd.enums.TypeDelivery;
import com.buenSabor.BackEnd.mapper.PedidoMapper;
import com.buenSabor.BackEnd.mapper.DetallePedidoMapper;
import com.buenSabor.BackEnd.mapper.DetallePromocionMapper;
import com.buenSabor.BackEnd.mapper.DireccionPedidoMapper;
import com.buenSabor.BackEnd.mapper.DireccionMapper;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.models.ubicacion.Direccion;
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
import com.buenSabor.BackEnd.repositories.venta.DetalleDireccionPedidoRepository;
import com.buenSabor.BackEnd.repositories.venta.DetallePedidoArticuloRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    private final DetallePedidoArticuloRepository detallePedidoRepository;
    private final DetallePedidoPromocionRepository detallePromocionRepository;
    private final DireccionRepository direccionRepository;
    private final DetalleDireccionPedidoRepository direccionPedidoRepository;

    private final PedidoMapper pedidoMapper;
    private final DetallePedidoMapper detallePedidoMapper;
    private final DetallePromocionMapper detallePromocionMapper;
    private final DireccionPedidoMapper direccionPedidoMapper;
    private final DireccionMapper direccionMapper;

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
            DetallePedidoArticuloRepository detallePedidoRepository,
            DetallePedidoPromocionRepository detallePromocionRepository,
            DireccionRepository direccionRepository,
            DetalleDireccionPedidoRepository direccionPedidoRepository,
            PedidoMapper pedidoMapper,
            DetallePedidoMapper detallePedidoMapper,
            DetallePromocionMapper detallePromocionMapper,
            DireccionPedidoMapper direccionPedidoMapper,
            DireccionMapper direccionMapper
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
        this.direccionRepository = direccionRepository;
        this.direccionPedidoRepository = direccionPedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.detallePedidoMapper = detallePedidoMapper;
        this.detallePromocionMapper = detallePromocionMapper;
        this.direccionPedidoMapper = direccionPedidoMapper;
        this.direccionMapper = direccionMapper;
    }

    // --- Read Operations ---
    @Transactional(readOnly = true)
    public PedidoConDireccionDTO findPedidoConDireccionDTOById(Long id) throws Exception {
        return pedidoRepository.findById(id)
                .map(pedidoMapper::toPedidoConDireccionDto)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<PedidoConDireccionDTO> findAllPedidosConDireccionDTO() throws Exception {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidoMapper.toPedidoConDireccionDtoList(pedidos);
    }

    @Transactional(readOnly = true)
    public Page<PedidoConDireccionDTO> findAllPedidosConDireccionDTO(Pageable pageable) throws Exception {
        Page<Pedido> pedidosPage = pedidoRepository.findAll(pageable);
        return pedidosPage.map(pedidoMapper::toPedidoConDireccionDto);
    }

    @Transactional
    public PedidoConDireccionDTO crearPedido(PedidoConDireccionDTO dto) throws Exception {
        // 1. Obtener y establecer relaciones ManyToOne (entidades existentes)
        EstadoPedido estadoPedido = estadoPedidoRepository.findById(dto.getEstadoPedido().getId())
                .orElseThrow(() -> new RuntimeException("Estado de Pedido con ID " + dto.getEstadoPedido().getId() + " no encontrado."));
        Sucursal sucursal = sucursalRepository.findById(dto.getSucursal().getId())
                .orElseThrow(() -> new RuntimeException("Sucursal con ID " + dto.getSucursal().getId() + " no encontrada."));
        TipoEnvio tipoEnvio = tipoEnvioRepository.findById(dto.getTipoEnvio().getId())
                .orElseThrow(() -> new RuntimeException("Tipo de Envío con ID " + dto.getTipoEnvio().getId() + " no encontrado."));
        TipoPago tipoPago = tipoPagoRepository.findById(dto.getTipoPago().getId())
                .orElseThrow(() -> new RuntimeException("Tipo de Pago con ID " + dto.getTipoPago().getId() + " no encontrado."));
        Usuario usuario = usuarioRepository.findById(dto.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + dto.getUsuario().getId() + " no encontrado."));

        // Validar que el tipo de envío del DTO coincida con el de la base de datos
        if (!tipoEnvio.getTipoDelivery().equals(dto.getTipoEnvio().getTipoDelivery())) {
            throw new RuntimeException("El tipo de envío proporcionado en el DTO no coincide con el tipo de envío en la base de datos.");
        }

        // 2. Mapear los campos básicos del PedidoConDireccionDTO a la entidad Pedido
        Pedido pedido = pedidoMapper.toEntity(dto);
        pedido.setEstadoPedido(estadoPedido);
        pedido.setSucursal(sucursal);
        pedido.setTipoEnvio(tipoEnvio);
        pedido.setTipoPago(tipoPago);
        pedido.setUsuario(usuario);

        // Inicializar listas para evitar NullPointerExceptions
        pedido.setDetallePedidoList(new ArrayList<>());
        pedido.setDetallePromocionList(new ArrayList<>());

        // 3. Manejar DireccionPedido (condicionalmente)
        if (tipoEnvio.getTipoDelivery() == TypeDelivery.DELIVERY || tipoEnvio.getTipoDelivery() == TypeDelivery.TAKEAWAY) {
            if (dto.getDireccion() == null || dto.getDireccion().getDireccion() == null) {
                throw new RuntimeException("Para el tipo de envío " + tipoEnvio.getTipoDelivery() + ", se requiere una Dirección de Pedido completa.");
            }

            // Map DireccionDTO nested inside DireccionPedidoDTO to Direccion entity
            Direccion direccionDetails = direccionMapper.toEntity(dto.getDireccion().getDireccion());
            direccionRepository.save(direccionDetails); // Save the actual address details first

            // Create the DireccionPedido entity, which now just wraps the Direccion
            DireccionPedido direccionPedido = direccionPedidoMapper.toEntity(dto.getDireccion());
            direccionPedido.setDireccion(direccionDetails); // Set the saved Direccion entity
            direccionPedido.setPedido(pedido); // Link DireccionPedido to this Pedido entity
            direccionPedidoRepository.save(direccionPedido); // Save the associative entity

            pedido.setDireccionPedido(direccionPedido); // Set the DireccionPedido on the main Pedido
        } else {
            // If the delivery type does not require a shipping address, ensure none is provided
            if (dto.getDireccion() != null) {
                logger.warn("Se recibió una DireccionPedidoDTO para un Tipo de Envío (" + tipoEnvio.getTipoDelivery() + ") que no la requiere. Será ignorada.");
            }
            pedido.setDireccionPedido(null);
        }

        // 4. Guardar la entidad Pedido primero para obtener su ID
        Pedido savedPedido = pedidoRepository.save(pedido);

        // 5. Manejar DetallePedido (OneToMany)
        if (dto.getDetallePedidoList() != null && !dto.getDetallePedidoList().isEmpty()) {
            for (DetallePedidoDTO detalleDto : dto.getDetallePedidoList()) {
                Articulo articulo = articuloRepository.findById(detalleDto.getArticulo().getId())
                        .orElseThrow(() -> new RuntimeException("Artículo con ID " + detalleDto.getArticulo().getId() + " no encontrado para DetallePedido."));

                DetallePedido detallePedido = detallePedidoMapper.toEntity(detalleDto);
                detallePedido.setPedido(savedPedido);
                detallePedido.setArticulo(articulo);
                savedPedido.getDetallePedidoList().add(detallePedido);
            }
        }

        // 6. Manejar DetallePromocion (OneToMany)
        if (dto.getDetallePromocionList() != null && !dto.getDetallePromocionList().isEmpty()) {
            for (DetallePromocionDTO detalleDto : dto.getDetallePromocionList()) {
                Promocion promocion = promocionRepository.findById(detalleDto.getPromocion().getId())
                        .orElseThrow(() -> new RuntimeException("Promoción con ID " + detalleDto.getPromocion().getId() + " no encontrada para DetallePromocion."));

                DetallePromocion detallePromocion = detallePromocionMapper.toEntity(detalleDto);
                detallePromocion.setPedido(savedPedido);
                detallePromocion.setPromocion(promocion);
                savedPedido.getDetallePromocionList().add(detallePromocion);
            }
        }

        // Re-save the parent to ensure cascades or any updates based on children are flushed
        return pedidoMapper.toPedidoConDireccionDto(pedidoRepository.save(savedPedido));
    }

    @Transactional
    public PedidoConDireccionDTO actualizarPedido(Long id, PedidoConDireccionDTO dto) throws Exception {
        Pedido existingPedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido con ID " + id + " no encontrado para actualizar."));

        // 1. Obtener y establecer relaciones ManyToOne (entidades existentes)
        EstadoPedido estadoPedido = estadoPedidoRepository.findById(dto.getEstadoPedido().getId())
                .orElseThrow(() -> new RuntimeException("Estado de Pedido con ID " + dto.getEstadoPedido().getId() + " no encontrado."));
        Sucursal sucursal = sucursalRepository.findById(dto.getSucursal().getId())
                .orElseThrow(() -> new RuntimeException("Sucursal con ID " + dto.getSucursal().getId() + " no encontrada."));
        TipoEnvio tipoEnvio = tipoEnvioRepository.findById(dto.getTipoEnvio().getId())
                .orElseThrow(() -> new RuntimeException("Tipo de Envío con ID " + dto.getTipoEnvio().getId() + " no encontrado."));
        TipoPago tipoPago = tipoPagoRepository.findById(dto.getTipoPago().getId())
                .orElseThrow(() -> new RuntimeException("Tipo de Pago con ID " + dto.getTipoPago().getId() + " no encontrado."));
        Usuario usuario = usuarioRepository.findById(dto.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + dto.getUsuario().getId() + " no encontrado."));

        // Validar que el tipo de envío del DTO coincida con el de la base de datos
        if (!tipoEnvio.getTipoDelivery().equals(dto.getTipoEnvio().getTipoDelivery())) {
            throw new RuntimeException("El tipo de envío proporcionado en el DTO no coincide con el tipo de envío en la base de datos.");
        }

        // 2. Actualizar campos básicos del Pedido existente usando el mapper
        pedidoMapper.updatePedidoFromDto(dto, existingPedido);
        existingPedido.setEstadoPedido(estadoPedido);
        existingPedido.setSucursal(sucursal);
        existingPedido.setTipoEnvio(tipoEnvio);
        existingPedido.setTipoPago(tipoPago);
        existingPedido.setUsuario(usuario);

        // 3. Manejar DireccionPedido (condicionalmente)
        if (tipoEnvio.getTipoDelivery() == TypeDelivery.DELIVERY || tipoEnvio.getTipoDelivery() == TypeDelivery.TAKEAWAY) {
            if (dto.getDireccion() == null || dto.getDireccion().getDireccion() == null) {
                throw new RuntimeException("Para el tipo de envío " + tipoEnvio.getTipoDelivery() + ", se requiere una Dirección de Pedido completa.");
            }

            if (existingPedido.getDireccionPedido() == null) {
                // No existe DireccionPedido asociada, crear una nueva
                Direccion direccionDetails = direccionMapper.toEntity(dto.getDireccion().getDireccion());
                direccionRepository.save(direccionDetails);

                DireccionPedido newDireccionPedido = direccionPedidoMapper.toEntity(dto.getDireccion());
                newDireccionPedido.setDireccion(direccionDetails);
                newDireccionPedido.setPedido(existingPedido);
                direccionPedidoRepository.save(newDireccionPedido);

                existingPedido.setDireccionPedido(newDireccionPedido);
            } else {
                // Existe DireccionPedido asociada, actualizarla
                DireccionPedido currentDireccionPedido = existingPedido.getDireccionPedido();
                Direccion currentDireccionDetails = currentDireccionPedido.getDireccion();

                // Update the nested Direccion details
                direccionMapper.updateDireccionFromDto(dto.getDireccion().getDireccion(), currentDireccionDetails);
                direccionRepository.save(currentDireccionDetails);

                // Update the DireccionPedido (associative entity) if needed (e.g., if it has other fields)
                direccionPedidoMapper.updateDireccionPedidoFromDto(dto.getDireccion(), currentDireccionPedido);
                direccionPedidoRepository.save(currentDireccionPedido);
            }
        } else {
            // Si el tipo de envío no requiere DireccionPedido
            if (existingPedido.getDireccionPedido() != null) {
                logger.info("El TipoEnvio cambió a NO-DELIVERY/NO-TAKEAWAY. Eliminando DireccionPedido existente del pedido ID: {}", id);
                // First delete the nested Direccion if it's managed by DireccionPedido
                if (existingPedido.getDireccionPedido().getDireccion() != null) {
                    direccionRepository.delete(existingPedido.getDireccionPedido().getDireccion());
                }
                direccionPedidoRepository.delete(existingPedido.getDireccionPedido());
                existingPedido.setDireccionPedido(null);
            }
            if (dto.getDireccion() != null) {
                logger.warn("Se recibió una DireccionPedidoDTO para un Tipo de Envío (" + tipoEnvio.getTipoDelivery() + ") que no la requiere en la actualización. Será ignorada.");
            }
        }

        // 4. Manejar DetallePedido (OneToMany) - Update logic
        detallePedidoRepository.deleteAll(existingPedido.getDetallePedidoList());
        existingPedido.getDetallePedidoList().clear();

        if (dto.getDetallePedidoList() != null && !dto.getDetallePedidoList().isEmpty()) {
            for (DetallePedidoDTO detalleDto : dto.getDetallePedidoList()) {
                Articulo articulo = articuloRepository.findById(detalleDto.getArticulo().getId())
                        .orElseThrow(() -> new RuntimeException("Artículo con ID " + detalleDto.getArticulo().getId() + " no encontrado para DetallePedido."));

                DetallePedido newDetalle = detallePedidoMapper.toEntity(detalleDto);
                newDetalle.setPedido(existingPedido);
                newDetalle.setArticulo(articulo);
                existingPedido.getDetallePedidoList().add(newDetalle);
            }
        }

        // 5. Manejar DetallePromocion (OneToMany) - Update logic
        detallePromocionRepository.deleteAll(existingPedido.getDetallePromocionList());
        existingPedido.getDetallePromocionList().clear();

        if (dto.getDetallePromocionList() != null && !dto.getDetallePromocionList().isEmpty()) {
            for (DetallePromocionDTO detalleDto : dto.getDetallePromocionList()) {
                Promocion promocion = promocionRepository.findById(detalleDto.getPromocion().getId())
                        .orElseThrow(() -> new RuntimeException("Promoción con ID " + detalleDto.getPromocion().getId() + " no encontrada para DetallePromocion."));

                DetallePromocion newDetalle = detallePromocionMapper.toEntity(detalleDto);
                newDetalle.setPedido(existingPedido);
                newDetalle.setPromocion(promocion);
                existingPedido.getDetallePromocionList().add(newDetalle);
            }
        }

        // Guardar la entidad padre actualizada
        Pedido updatedPedido = pedidoRepository.save(existingPedido);
        return pedidoMapper.toPedidoConDireccionDto(updatedPedido);
    }

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