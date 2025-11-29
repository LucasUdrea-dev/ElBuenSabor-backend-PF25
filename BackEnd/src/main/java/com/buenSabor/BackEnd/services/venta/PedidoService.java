package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.dto.stock.StockCheckResponse;
import com.buenSabor.BackEnd.dto.venta.detallepedido.DetallePedidoDTO;
import com.buenSabor.BackEnd.dto.venta.detallepromocion.DetallePromocionDTO;
import com.buenSabor.BackEnd.dto.venta.pedido.PedidoConDireccionDTO;
import com.buenSabor.BackEnd.enums.TypeDelivery;
import com.buenSabor.BackEnd.enums.TypeState;
import com.buenSabor.BackEnd.mapper.*;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.producto.*;
import com.buenSabor.BackEnd.models.ubicacion.Direccion;
import com.buenSabor.BackEnd.models.user.Usuario;
import com.buenSabor.BackEnd.models.venta.*;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.company.SucursalRepository;
import com.buenSabor.BackEnd.repositories.producto.ArticuloRepository;
import com.buenSabor.BackEnd.repositories.ubicacion.DireccionRepository;
import com.buenSabor.BackEnd.repositories.user.UsuarioRepository;
import com.buenSabor.BackEnd.repositories.venta.*;
import com.buenSabor.BackEnd.services.WebSocketService;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import com.buenSabor.BackEnd.services.stock.StockService; // IMPORTANTE: Importar el servicio de stock
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
    private final DireccionRepository direccionRepository;

    // REFACTOR: Usamos StockService en lugar del Repositorio directo
    private final StockService stockService;

    private final PedidoMapper pedidoMapper;
    private final DetallePedidoMapper detallePedidoMapper;
    private final DetallePromocionMapper detallePromocionMapper;
    private final DireccionPedidoMapper direccionPedidoMapper;
    private final DireccionMapper direccionMapper;
    private final WebSocketService webSocketService;

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
            DireccionRepository direccionRepository,
            StockService stockService, // Inyección del servicio
            PedidoMapper pedidoMapper,
            DetallePedidoMapper detallePedidoMapper,
            DetallePromocionMapper detallePromocionMapper,
            DireccionPedidoMapper direccionPedidoMapper,
            DireccionMapper direccionMapper,
            WebSocketService webSocketService) {
        super(beanRepository);
        this.pedidoRepository = pedidoRepository;
        this.estadoPedidoRepository = estadoPedidoRepository;
        this.sucursalRepository = sucursalRepository;
        this.tipoEnvioRepository = tipoEnvioRepository;
        this.tipoPagoRepository = tipoPagoRepository;
        this.usuarioRepository = usuarioRepository;
        this.articuloRepository = articuloRepository;
        this.promocionRepository = promocionRepository;
        this.direccionRepository = direccionRepository;
        this.stockService = stockService;
        this.pedidoMapper = pedidoMapper;
        this.detallePedidoMapper = detallePedidoMapper;
        this.detallePromocionMapper = detallePromocionMapper;
        this.direccionPedidoMapper = direccionPedidoMapper;
        this.direccionMapper = direccionMapper;
        this.webSocketService = webSocketService;
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
        logger.info("Iniciando creación de pedido para usuario ID: {}", dto.getUsuario().getId());

        // 1. Validar entidades relacionadas
        EstadoPedido estadoPedido = estadoPedidoRepository.findById(dto.getEstadoPedido().getId())
                .orElseThrow(() -> new RuntimeException("Estado de Pedido no encontrado."));
        Sucursal sucursal = sucursalRepository.findById(dto.getSucursal().getId())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada."));
        TipoEnvio tipoEnvio = tipoEnvioRepository.findById(dto.getTipoEnvio().getId())
                .orElseThrow(() -> new RuntimeException("Tipo de Envío no encontrado."));
        TipoPago tipoPago = tipoPagoRepository.findById(dto.getTipoPago().getId())
                .orElseThrow(() -> new RuntimeException("Tipo de Pago no encontrado."));
        Usuario usuario = usuarioRepository.findById(dto.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        // 2. Validar contenido
        boolean tieneArticulos = dto.getDetallePedidoList() != null && !dto.getDetallePedidoList().isEmpty();
        boolean tienePromociones = dto.getDetallePromocionList() != null && !dto.getDetallePromocionList().isEmpty();

        if (!tieneArticulos && !tienePromociones) {
            throw new RuntimeException("El pedido debe contener al menos un artículo o una promoción.");
        }

        // 3. VALIDACIÓN DE STOCK 
Map<Long, Integer> insumosNecesarios = calcularInsumosNecesarios(dto);
StockCheckResponse stockResponse = stockService.validarStock(insumosNecesarios, sucursal.getId());

if (!stockResponse.isHayStockSuficiente()) {
    String detalleFaltantes = stockResponse.getProductosFaltantes().stream()
            .map(p -> p.getNombre() + " (faltan " + p.getCantidadFaltante() + " unidades)")
            .collect(Collectors.joining(", "));

    String mensajeUsuario = String.format(
            "No es posible completar el pedido debido a falta de stock. Los siguientes productos no están disponibles en las cantidades solicitadas: %s. Por favor, ajuste su pedido e intente nuevamente.",
            detalleFaltantes
    );

    logger.warn("Stock insuficiente en sucursal {}. Faltantes: {}", sucursal.getId(), detalleFaltantes);
    
    throw new RuntimeException(mensajeUsuario);
}

        logger.info("Stock validado correctamente. Procediendo a crear pedido.");

        // 4. Crear entidad Pedido
        Pedido pedido = pedidoMapper.toEntity(dto);
        pedido.setEstadoPedido(estadoPedido);
        pedido.setSucursal(sucursal);
        pedido.setTipoEnvio(tipoEnvio);
        pedido.setTipoPago(tipoPago);
        pedido.setUsuario(usuario);
        pedido.setDetallePedidoList(new ArrayList<>());
        pedido.setDetallePromocionList(new ArrayList<>());

        // Histórico Estado Inicial
        HistoricoEstadoPedido historicoInicial = new HistoricoEstadoPedido();
        historicoInicial.setEstadoPedido(estadoPedido);
        historicoInicial.setPedido(pedido);
        historicoInicial.setFechaCambio(new Date());
        if (pedido.getHistoricoEstados() == null)
            pedido.setHistoricoEstados(new ArrayList<>());
        pedido.getHistoricoEstados().add(historicoInicial);

        // 5. Manejar Dirección
        manejarDireccionPedido(pedido, dto, tipoEnvio);

        // 6. Guardar pedido inicial
        Pedido savedPedido = pedidoRepository.save(pedido);

        // 7. Agregar Detalles
        agregarDetallesAlPedido(savedPedido, dto);

        // 8. REDUCIR STOCK (Delegado a StockService)
        // Ya que el pedido se guardó parcialmente bien, ejecutamos la resta de stock
        stockService.descontarStock(insumosNecesarios, sucursal.getId());

        // 9. Guardar cambios finales
        Pedido finalPedido = pedidoRepository.save(savedPedido);
        logger.info("Pedido creado exitosamente con ID: {}", finalPedido.getId());

        PedidoConDireccionDTO pedidoFinalDto = pedidoMapper.toPedidoConDireccionDto(finalPedido);

        // 10. Notificación WebSocket
        enviarNotificacionWebSocket(finalPedido, pedidoFinalDto);

        return pedidoFinalDto;
    }

   @Transactional
public PedidoConDireccionDTO actualizarPedido(Long id, PedidoConDireccionDTO dto) throws Exception {
    Pedido existingPedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El pedido que intenta actualizar no existe."));

    // --- 1. PREPARACIÓN DE DATOS ---
    EstadoPedido estadoAnterior = existingPedido.getEstadoPedido();
    EstadoPedido nuevoEstado = estadoPedidoRepository.findById(dto.getEstadoPedido().getId())
            .orElseThrow(() -> new RuntimeException("Estado de Pedido no encontrado."));
    Sucursal sucursal = sucursalRepository.findById(dto.getSucursal().getId())
            .orElseThrow(() -> new RuntimeException("Sucursal no encontrada."));

    // --- 2. GESTIÓN DE STOCK ---
    
    // A. Liberar Stock Previo:
    // Calculamos qué insumos tenía el pedido ANTES de la modificación y los devolvemos al inventario.
    PedidoConDireccionDTO oldDto = pedidoMapper.toPedidoConDireccionDto(existingPedido);
    Map<Long, Integer> insumosLiberados = calcularInsumosNecesarios(oldDto);
    stockService.reponerStock(insumosLiberados, existingPedido.getSucursal().getId());

    // B. Determinar si se requiere stock nuevo:
    // Si el NUEVO estado es Cancelado o Rechazado, no descontamos nada 
    boolean pedidoCancelado = isEstadoCanceladoORechazado(nuevoEstado.getNombreEstado());
    
    if (!pedidoCancelado) {
        // C. Calcular nuevos requerimientos 
        Map<Long, Integer> insumosNuevos = calcularInsumosNecesarios(dto);

        // D. Validar disponibilidad
        StockCheckResponse stockResponse = stockService.validarStock(insumosNuevos, sucursal.getId());

        if (!stockResponse.isHayStockSuficiente()) {
            
            String detalleFaltantes = stockResponse.getProductosFaltantes().stream()
                    .map(p -> p.getNombre() + " (faltan " + p.getCantidadFaltante() + " unidades)")
                    .collect(Collectors.joining(", "));

            String mensajeUsuario = String.format(
                    "No es posible actualizar el pedido debido a falta de stock. Los siguientes productos no están disponibles: %s. Por favor, revise las cantidades.",
                    detalleFaltantes
            );

        
            // El "reponerStock" del paso A se deshace, por lo que el inventario vuelve a su estado original seguro
            throw new RuntimeException(mensajeUsuario);
        }

        // E. Descontar el stock nuevo
        stockService.descontarStock(insumosNuevos, sucursal.getId());
    } else {
        logger.info("El pedido ID {} pasa a estado {}, el stock ha sido liberado y no se realizarán nuevos descuentos.", id, nuevoEstado.getNombreEstado());
    }

    // --- 3. ACTUALIZACIÓN DE ENTIDADES ---
    
    // Validar otras entidades
    TipoEnvio tipoEnvio = tipoEnvioRepository.findById(dto.getTipoEnvio().getId())
            .orElseThrow(() -> new RuntimeException("Tipo de Envío no encontrado."));
    TipoPago tipoPago = tipoPagoRepository.findById(dto.getTipoPago().getId())
            .orElseThrow(() -> new RuntimeException("Tipo de Pago no encontrado."));
    Usuario usuario = usuarioRepository.findById(dto.getUsuario().getId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

    // Actualizar campos básicos
    pedidoMapper.updatePedidoFromDto(dto, existingPedido);
    existingPedido.setTiempoEstimado(dto.getTiempoEstimado());
    existingPedido.setEstadoPedido(nuevoEstado);
    existingPedido.setSucursal(sucursal);
    existingPedido.setTipoEnvio(tipoEnvio);
    existingPedido.setTipoPago(tipoPago);
    existingPedido.setUsuario(usuario);

    // Histórico de estados
    if (!nuevoEstado.getId().equals(estadoAnterior.getId())) {
        HistoricoEstadoPedido historico = new HistoricoEstadoPedido();
        historico.setEstadoPedido(nuevoEstado);
        historico.setPedido(existingPedido);
        historico.setFechaCambio(new Date());
        if (existingPedido.getHistoricoEstados() == null)
            existingPedido.setHistoricoEstados(new ArrayList<>());
        existingPedido.getHistoricoEstados().add(historico);
    }

    // Actualizar Direcciones y Detalles
    manejarDireccionPedidoUpdate(existingPedido, dto, tipoEnvio);
    actualizarDetallesPedido(existingPedido, dto);

    // Guardar cambios finales
    Pedido updatedPedido = pedidoRepository.save(existingPedido);
    PedidoConDireccionDTO pedidoActualizadoDto = pedidoMapper.toPedidoConDireccionDto(updatedPedido);

    // Notificación
    enviarNotificacionWebSocket(updatedPedido, pedidoActualizadoDto);

    return pedidoActualizadoDto;
}

private boolean isEstadoCanceladoORechazado(TypeState estado) {
    return estado == TypeState.CANCELLED || estado == TypeState.REJECTED;
}


    private Map<Long, Integer> calcularInsumosNecesarios(PedidoConDireccionDTO dto) {
        Map<Long, Integer> insumos = new HashMap<>();

        if (dto.getDetallePedidoList() != null) {
            for (DetallePedidoDTO d : dto.getDetallePedidoList()) {
                Articulo art = articuloRepository.findById(d.getArticulo().getId())
                        .orElseThrow(() -> new RuntimeException("Artículo no encontrado: " + d.getArticulo().getId()));
                procesarArticuloParaStock(art, d.getCantidad(), insumos);
            }
        }
        if (dto.getDetallePromocionList() != null) {
            for (DetallePromocionDTO d : dto.getDetallePromocionList()) {
                Promocion promo = promocionRepository.findById(d.getPromocion().getId())
                        .orElseThrow(() -> new RuntimeException("Promoción no encontrada"));
                for (PromocionArticulo pa : promo.getPromocionArticuloList()) {
                    procesarArticuloParaStock(pa.getIdArticulo(), pa.getCantidad() * d.getCantidad(), insumos);
                }
            }
        }
        return insumos;
    }

    private void procesarArticuloParaStock(Articulo articulo, int cantidad, Map<Long, Integer> insumos) {
        if (articulo instanceof ArticuloManufacturado man) {
            for (ArticuloManufacturadoDetalleInsumo det : man.getDetalleInsumos()) {
                insumos.merge(det.getArticuloInsumo().getId(), det.getCantidad() * cantidad, Integer::sum);
            }
        } else if (articulo instanceof ArticuloInsumo) {
            insumos.merge(articulo.getId(), cantidad, Integer::sum);
        }
    }

    // --- Gestión de Direcciones y Otros ---

    private void manejarDireccionPedido(Pedido pedido, PedidoConDireccionDTO dto, TipoEnvio tipoEnvio) {
        if (esEnvioConDireccion(tipoEnvio)) {
            if (dto.getDireccion() == null || dto.getDireccion().getDireccion() == null) {
                throw new RuntimeException("Dirección requerida para el tipo de envío seleccionado.");
            }
            Direccion dir = direccionMapper.toEntity(dto.getDireccion().getDireccion());
            direccionRepository.save(dir);
            DireccionPedido dirPed = direccionPedidoMapper.toEntity(dto.getDireccion());
            dirPed.setDireccion(dir);
            dirPed.setPedido(pedido);
            pedido.setDireccionPedido(dirPed);
        } else {
            pedido.setDireccionPedido(null);
        }
    }

    private void manejarDireccionPedidoUpdate(Pedido pedido, PedidoConDireccionDTO dto, TipoEnvio tipoEnvio) {
        if (esEnvioConDireccion(tipoEnvio)) {
            if (dto.getDireccion() == null)
                throw new RuntimeException("Dirección requerida");

            if (pedido.getDireccionPedido() == null) {
                manejarDireccionPedido(pedido, dto, tipoEnvio);
            } else {
                DireccionPedido currentDirPed = pedido.getDireccionPedido();
                direccionMapper.updateDireccionFromDto(dto.getDireccion().getDireccion(), currentDirPed.getDireccion());
                direccionRepository.save(currentDirPed.getDireccion());
                direccionPedidoMapper.updateDireccionPedidoFromDto(dto.getDireccion(), currentDirPed);
            }
        } else {
            if (pedido.getDireccionPedido() != null) {
                if (pedido.getDireccionPedido().getDireccion() != null) {
                    direccionRepository.delete(pedido.getDireccionPedido().getDireccion());
                }
                pedido.setDireccionPedido(null);
            }
        }
    }

    private boolean esEnvioConDireccion(TipoEnvio tipoEnvio) {
        return tipoEnvio.getTipoDelivery() == TypeDelivery.DELIVERY || false;
    }

    private void agregarDetallesAlPedido(Pedido pedido, PedidoConDireccionDTO dto) {
        if (dto.getDetallePedidoList() != null) {
            for (DetallePedidoDTO d : dto.getDetallePedidoList()) {
                Articulo art = articuloRepository.findById(d.getArticulo().getId()).orElseThrow();
                DetallePedido det = detallePedidoMapper.toEntity(d);
                det.setPedido(pedido);
                det.setArticulo(art);
                pedido.getDetallePedidoList().add(det);
            }
        }
        if (dto.getDetallePromocionList() != null) {
            for (DetallePromocionDTO d : dto.getDetallePromocionList()) {
                Promocion promo = promocionRepository.findById(d.getPromocion().getId()).orElseThrow();
                DetallePromocion det = detallePromocionMapper.toEntity(d);
                det.setPedido(pedido);
                det.setPromocion(promo);
                pedido.getDetallePromocionList().add(det);
            }
        }
    }

    private void actualizarDetallesPedido(Pedido pedido, PedidoConDireccionDTO dto) {
        pedido.getDetallePedidoList().clear();
        pedido.getDetallePromocionList().clear();
        agregarDetallesAlPedido(pedido, dto);
    }

    private void enviarNotificacionWebSocket(Pedido pedido, PedidoConDireccionDTO dto) {
        try {
            TypeState estadoEnum = pedido.getEstadoPedido().getNombreEstado();
            webSocketService.notifyStatusChange(pedido.getId(), estadoEnum, dto);
        } catch (Exception e) {
            logger.error("Error al enviar notificación WebSocket para pedido ID {}", pedido.getId(), e);
        }
    }

    @Transactional
    public void eliminarPedido(Long id) throws Exception {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("No encontrado"));
        pedido.setExiste(Boolean.FALSE);
        pedidoRepository.save(pedido);
    }

    public List<PedidoConDireccionDTO> findAllExistentes() {
        return pedidoRepository.findAllByExisteTrue().stream()
                .map(pedidoMapper::toPedidoConDireccionDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PedidoConDireccionDTO findByIdExistente(Long id) {
        Pedido pedido = (Pedido) pedidoRepository.findByIdAndExisteTrue(id);
        return pedidoMapper.toPedidoConDireccionDto(pedido);
    }
}