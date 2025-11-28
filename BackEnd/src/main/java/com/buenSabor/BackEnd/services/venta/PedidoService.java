package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.dto.venta.detallepedido.DetallePedidoDTO;
import com.buenSabor.BackEnd.dto.venta.detallepromocion.DetallePromocionDTO;
import com.buenSabor.BackEnd.dto.venta.pedido.PedidoConDireccionDTO;

import com.buenSabor.BackEnd.enums.TypeDelivery;
import com.buenSabor.BackEnd.enums.TypeState;
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
import com.buenSabor.BackEnd.models.venta.PromocionArticulo;
import com.buenSabor.BackEnd.models.venta.TipoEnvio;
import com.buenSabor.BackEnd.models.venta.TipoPago;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.company.SucursalRepository;
import com.buenSabor.BackEnd.repositories.producto.ArticuloRepository;
import com.buenSabor.BackEnd.repositories.ubicacion.DireccionRepository;
import com.buenSabor.BackEnd.repositories.user.UsuarioRepository;
import com.buenSabor.BackEnd.repositories.venta.*;
import com.buenSabor.BackEnd.services.WebSocketService;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import com.buenSabor.BackEnd.dto.stock.StockCheckResponse;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturado;
import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturadoDetalleInsumo;
import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import com.buenSabor.BackEnd.models.venta.HistoricoEstadoPedido;
import java.lang.RuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                .orElseThrow(() -> new RuntimeException(
                        "Estado de Pedido con ID " + dto.getEstadoPedido().getId() + " no encontrado."));
        Sucursal sucursal = sucursalRepository.findById(dto.getSucursal().getId())
                .orElseThrow(
                        () -> new RuntimeException("Sucursal con ID " + dto.getSucursal().getId() + " no encontrada."));
        TipoEnvio tipoEnvio = tipoEnvioRepository.findById(dto.getTipoEnvio().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Tipo de Envío con ID " + dto.getTipoEnvio().getId() + " no encontrado."));
        TipoPago tipoPago = tipoPagoRepository.findById(dto.getTipoPago().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Tipo de Pago con ID " + dto.getTipoPago().getId() + " no encontrado."));
        Usuario usuario = usuarioRepository.findById(dto.getUsuario().getId())
                .orElseThrow(
                        () -> new RuntimeException("Usuario con ID " + dto.getUsuario().getId() + " no encontrado."));

        // 2. Validar contenido
        boolean tieneArticulos = dto.getDetallePedidoList() != null && !dto.getDetallePedidoList().isEmpty();
        boolean tienePromociones = dto.getDetallePromocionList() != null && !dto.getDetallePromocionList().isEmpty();

        if (!tieneArticulos && !tienePromociones) {
            logger.warn("Intento de crear pedido sin artículos ni promociones para usuario ID: {}",
                    dto.getUsuario().getId());
            throw new RuntimeException("El pedido debe contener al menos un artículo o una promoción.");
        }

        // 3. VALIDACIÓN DE STOCK
        Map<Long, Integer> insumosNecesarios = calcularInsumosNecesarios(dto);
        
        StockCheckResponse stockResponse = verificarYValidarStock(insumosNecesarios, sucursal.getId());

        if (!stockResponse.isHayStockSuficiente()) {
            logger.warn("Stock insuficiente. Faltantes: {}", stockResponse.getProductosFaltantes().size());
            throw new RuntimeException("Stock insuficiente: " + stockResponse.getMensaje() +
                    ". Productos faltantes: " + stockResponse.getProductosFaltantes());
        }

        logger.info("Stock validado. Creando pedido.");

        // 4. Crear entidad Pedido
        Pedido pedido = pedidoMapper.toEntity(dto);
        pedido.setEstadoPedido(estadoPedido);
        pedido.setSucursal(sucursal);
        pedido.setTipoEnvio(tipoEnvio);
        pedido.setTipoPago(tipoPago);
        pedido.setUsuario(usuario);
        pedido.setDetallePedidoList(new ArrayList<>());
        pedido.setDetallePromocionList(new ArrayList<>());
        
        // --- HISTÓRICO ESTADO INICIAL ---
        // Al crear, guardamos el primer estado en el histórico
        HistoricoEstadoPedido historicoInicial = new HistoricoEstadoPedido();
        historicoInicial.setEstadoPedido(estadoPedido);
        historicoInicial.setPedido(pedido);
        historicoInicial.setFechaCambio(new Date());
        
        if (pedido.getHistoricoEstados() == null) {
            pedido.setHistoricoEstados(new ArrayList<>());
        }
        pedido.getHistoricoEstados().add(historicoInicial);
        // --------------------------------

        // 5. Manejar Dirección
        manejarDireccionPedido(pedido, dto, tipoEnvio);

        // 6. Guardar pedido (para generar ID y propagar cascade)
        Pedido savedPedido = pedidoRepository.save(pedido);

        // 7. Agregar Detalles (Artículos y Promociones)
        agregarDetallesAlPedido(savedPedido, dto);

        // 8. REDUCIR STOCK
        reducirStockYActualizarExistencia(insumosNecesarios, sucursal.getId());

        // 9. Guardar cambios finales (con detalles vinculados)
        Pedido finalPedido = pedidoRepository.save(savedPedido);
        logger.info("Pedido creado exitosamente con ID: {}", finalPedido.getId());

        PedidoConDireccionDTO pedidoFinalDto = pedidoMapper.toPedidoConDireccionDto(finalPedido);

        // 10. NOTIFICACIÓN WEBSOCKET
        try {
            TypeState estadoEnum = finalPedido.getEstadoPedido().getNombreEstado();
            webSocketService.notifyStatusChange(finalPedido.getId(), estadoEnum, pedidoFinalDto);
        } catch (Exception e) {
            logger.error("Error al enviar notificación WebSocket para pedido ID {}", finalPedido.getId(), e);
        }

        return pedidoFinalDto;
    }

    @Transactional
    public PedidoConDireccionDTO actualizarPedido(Long id, PedidoConDireccionDTO dto) throws Exception {
        Pedido existingPedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido con ID " + id + " no encontrado para actualizar."));

        // 1. Guardar estado anterior para comparar
        EstadoPedido estadoAnterior = existingPedido.getEstadoPedido();

        // 2. Obtener nuevas entidades relacionadas
        EstadoPedido nuevoEstado = estadoPedidoRepository.findById(dto.getEstadoPedido().getId())
                .orElseThrow(() -> new RuntimeException("Estado de Pedido no encontrado."));
        
        Sucursal sucursal = sucursalRepository.findById(dto.getSucursal().getId())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada."));
        
        TipoEnvio tipoEnvio = tipoEnvioRepository.findById(dto.getTipoEnvio().getId())
                .orElseThrow(() -> new RuntimeException("Tipo de Envío no encontrado."));
        
        TipoPago tipoPago = tipoPagoRepository.findById(dto.getTipoPago().getId())
                .orElseThrow(() -> new RuntimeException("Tipo de Pago no encontrado."));
        
        Usuario usuario = usuarioRepository.findById(dto.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        // 3. Actualizar campos básicos
        pedidoMapper.updatePedidoFromDto(dto, existingPedido);
        existingPedido.setTiempoEstimado(dto.getTiempoEstimado());

        // 4. Asignar relaciones
        existingPedido.setEstadoPedido(nuevoEstado);
        existingPedido.setSucursal(sucursal);
        existingPedido.setTipoEnvio(tipoEnvio);
        existingPedido.setTipoPago(tipoPago);
        existingPedido.setUsuario(usuario);

        // --- 5. LÓGICA DE HISTÓRICO DE ESTADO ---
        // Si el estado cambió, guardamos el histórico manualmente (sin listeners)
        if (!nuevoEstado.getId().equals(estadoAnterior.getId())) {
            HistoricoEstadoPedido historico = new HistoricoEstadoPedido();
            historico.setEstadoPedido(nuevoEstado);
            historico.setPedido(existingPedido);
            historico.setFechaCambio(new Date());
            
            if (existingPedido.getHistoricoEstados() == null) {
                existingPedido.setHistoricoEstados(new ArrayList<>());
            }
            existingPedido.getHistoricoEstados().add(historico);
        }
        // ---------------------------------------

        // 6. Manejar Dirección
        manejarDireccionPedidoUpdate(existingPedido, dto, tipoEnvio);

        // 7. Manejar Detalles (Reemplazo completo)
        actualizarDetallesPedido(existingPedido, dto);

        // 8. Guardar todo
        Pedido updatedPedido = pedidoRepository.save(existingPedido);
        PedidoConDireccionDTO pedidoActualizadoDto = pedidoMapper.toPedidoConDireccionDto(updatedPedido);

        // 9. NOTIFICACIÓN WEBSOCKET
        try {
            TypeState estadoEnum = updatedPedido.getEstadoPedido().getNombreEstado();
            webSocketService.notifyStatusChange(updatedPedido.getId(), estadoEnum, pedidoActualizadoDto);
        } catch (IllegalArgumentException e) {
            logger.warn("El estado '{}' no existe en TypeState.", updatedPedido.getEstadoPedido().getNombreEstado());
        } catch (Exception e) {
            logger.error("Error WebSocket update pedido ID {}", id, e);
        }

        return pedidoActualizadoDto;
    }

    // --- Helper Methods para limpiar el código principal ---

    private Map<Long, Integer> calcularInsumosNecesarios(PedidoConDireccionDTO dto) {
        Map<Long, Integer> insumos = new HashMap<>();
        
        // Artículos
        if (dto.getDetallePedidoList() != null) {
            for (DetallePedidoDTO d : dto.getDetallePedidoList()) {
                Articulo art = articuloRepository.findById(d.getArticulo().getId()).orElseThrow();
                procesarArticuloParaStock(art, d.getCantidad(), insumos);
            }
        }
        // Promociones
        if (dto.getDetallePromocionList() != null) {
            for (DetallePromocionDTO d : dto.getDetallePromocionList()) {
                Promocion promo = promocionRepository.findById(d.getPromocion().getId()).orElseThrow();
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

    private void manejarDireccionPedido(Pedido pedido, PedidoConDireccionDTO dto, TipoEnvio tipoEnvio) {
        if (tipoEnvio.getTipoDelivery() == TypeDelivery.DELIVERY || tipoEnvio.getTipoDelivery() == TypeDelivery.TAKEAWAY) {
            if (dto.getDireccion() == null || dto.getDireccion().getDireccion() == null) {
                throw new RuntimeException("Dirección requerida para " + tipoEnvio.getTipoDelivery());
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
        // Misma lógica que crear, pero considerando si ya existe para actualizar
        if (tipoEnvio.getTipoDelivery() == TypeDelivery.DELIVERY || tipoEnvio.getTipoDelivery() == TypeDelivery.TAKEAWAY) {
            if (dto.getDireccion() == null) throw new RuntimeException("Dirección requerida");
            
            if (pedido.getDireccionPedido() == null) {
                manejarDireccionPedido(pedido, dto, tipoEnvio); // Crear nueva
            } else {
                // Actualizar existente
                DireccionPedido currentDirPed = pedido.getDireccionPedido();
                direccionMapper.updateDireccionFromDto(dto.getDireccion().getDireccion(), currentDirPed.getDireccion());
                direccionRepository.save(currentDirPed.getDireccion());
                direccionPedidoMapper.updateDireccionPedidoFromDto(dto.getDireccion(), currentDirPed);
            }
        } else {
            if (pedido.getDireccionPedido() != null) {
                // Borrar si ya no es delivery
                if(pedido.getDireccionPedido().getDireccion() != null) {
                    direccionRepository.delete(pedido.getDireccionPedido().getDireccion());
                }
                pedido.setDireccionPedido(null);
            }
        }
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

    // --- Métodos de Stock (Privados) ---
    // (Mantienen la lógica original de verificación y reducción)
    
    private StockCheckResponse verificarYValidarStock(Map<Long, Integer> insumosNecesarios, Long sucursalId) {
        StockCheckResponse response = new StockCheckResponse(true, "Stock suficiente");
        for (Map.Entry<Long, Integer> entry : insumosNecesarios.entrySet()) {
            Long insumoId = entry.getKey();
            Integer cantidad = entry.getValue();
            try {
                ArticuloInsumo insumo = (ArticuloInsumo) articuloRepository.findById(insumoId).orElseThrow();
                StockArticuloInsumo stock = insumo.getStockArticuloInsumo();
                
                if (stock == null || !stock.getSucursal().getId().equals(sucursalId)) {
                    response.agregarProductoFaltante(insumoId, insumo.getNombre(), cantidad);
                    response.setHayStockSuficiente(false);
                    continue;
                }
                if (stock.getCantidad() < cantidad) {
                    response.agregarProductoFaltante(insumoId, insumo.getNombre(), cantidad - stock.getCantidad());
                    response.setHayStockSuficiente(false);
                }
            } catch (Exception e) {
                response.setHayStockSuficiente(false);
            }
        }
        if (!response.isHayStockSuficiente()) response.setMensaje("Stock insuficiente");
        return response;
    }

    private void reducirStockYActualizarExistencia(Map<Long, Integer> insumosNecesarios, Long sucursalId) {
        for (Map.Entry<Long, Integer> entry : insumosNecesarios.entrySet()) {
            Long insumoId = entry.getKey();
            Integer cantidad = entry.getValue();
            ArticuloInsumo insumo = (ArticuloInsumo) articuloRepository.findById(insumoId).orElseThrow();
            StockArticuloInsumo stock = insumo.getStockArticuloInsumo();
            
            if (stock != null && stock.getSucursal().getId().equals(sucursalId)) {
                int nuevoStock = stock.getCantidad() - cantidad;
                stock.setCantidad(nuevoStock);
                if (nuevoStock < stock.getMinStock()) insumo.setExiste(false);
                articuloRepository.save(insumo);
            }
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
        try {
            Pedido pedido = (Pedido) pedidoRepository.findByIdAndExisteTrue(id);
            return pedidoMapper.toPedidoConDireccionDto(pedido);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar pedido", e);
        }
    }
}