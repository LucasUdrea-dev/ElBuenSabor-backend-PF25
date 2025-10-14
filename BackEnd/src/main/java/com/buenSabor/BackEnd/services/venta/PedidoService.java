package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.Exception.PedidoNotFoundException;
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
import com.buenSabor.BackEnd.models.venta.PromocionArticulo;
import com.buenSabor.BackEnd.models.venta.TipoEnvio;
import com.buenSabor.BackEnd.models.venta.TipoPago;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.company.SucursalRepository;
import com.buenSabor.BackEnd.repositories.producto.ArticuloRepository;
import com.buenSabor.BackEnd.repositories.ubicacion.DireccionRepository;
import com.buenSabor.BackEnd.repositories.user.UsuarioRepository;
import com.buenSabor.BackEnd.repositories.venta.*;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import com.buenSabor.BackEnd.dto.stock.StockCheckResponse;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturado;
import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturadoDetalleInsumo;
import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import java.lang.RuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    //private final DetallePedidoRepository detallePedidoRepository;
    //private final DetallePromocionRepository detallePromocionRepository;
    private final DireccionRepository direccionRepository;
    //private final DireccionPedidoRepository direccionPedidoRepository;

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
            //DetallePedidoRepository detallePedidoRepository,
            //DetallePromocionRepository detallePromocionRepository,
            DireccionRepository direccionRepository,
            //DireccionPedidoRepository direccionPedidoRepository,
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
        //this.detallePedidoRepository = detallePedidoRepository;
        //this.detallePromocionRepository = detallePromocionRepository;
        this.direccionRepository = direccionRepository;
        //this.direccionPedidoRepository = direccionPedidoRepository;
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
        logger.info("Iniciando creación de pedido para usuario ID: {}", dto.getUsuario().getId());

        // 1. Validar entidades relacionadas
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

        // 2. Validar que el pedido tenga al menos un artículo o una promoción
        boolean tieneArticulos = dto.getDetallePedidoList() != null && !dto.getDetallePedidoList().isEmpty();
        boolean tienePromociones = dto.getDetallePromocionList() != null && !dto.getDetallePromocionList().isEmpty();
        
        if (!tieneArticulos && !tienePromociones) {
            logger.warn("Intento de crear pedido sin artículos ni promociones para usuario ID: {}", dto.getUsuario().getId());
            throw new RuntimeException("El pedido debe contener al menos un artículo o una promoción.");
        }

        // 3. VALIDACIÓN DE STOCK - Construir request para verificación
        Map<Long, Integer> insumosNecesarios = new HashMap<>();
        
        // Procesar artículos del pedido
        if (dto.getDetallePedidoList() != null && !dto.getDetallePedidoList().isEmpty()) {
            for (DetallePedidoDTO detalleDto : dto.getDetallePedidoList()) {
                Articulo articulo = articuloRepository.findById(detalleDto.getArticulo().getId())
                        .orElseThrow(() -> new RuntimeException("Artículo con ID " + detalleDto.getArticulo().getId() + " no encontrado."));
                
                if (articulo instanceof ArticuloManufacturado) {
                    // Calcular insumos necesarios para manufacturados
                    ArticuloManufacturado manufacturado = (ArticuloManufacturado) articulo;
                    for (ArticuloManufacturadoDetalleInsumo detalle : manufacturado.getDetalleInsumos()) {
                        Long insumoId = detalle.getArticuloInsumo().getId();
                        int cantidadNecesaria = detalle.getCantidad() * detalleDto.getCantidad();
                        insumosNecesarios.merge(insumoId, cantidadNecesaria, Integer::sum);
                    }
                } else if (articulo instanceof ArticuloInsumo) {
                    // Insumo directo
                    Long insumoId = articulo.getId();
                    insumosNecesarios.merge(insumoId, detalleDto.getCantidad(), Integer::sum);
                }
            }
        }

        // Procesar promociones del pedido
        if (dto.getDetallePromocionList() != null && !dto.getDetallePromocionList().isEmpty()) {
            for (DetallePromocionDTO detalleDto : dto.getDetallePromocionList()) {
                Promocion promocion = promocionRepository.findById(detalleDto.getPromocion().getId())
                        .orElseThrow(() -> new RuntimeException("Promoción con ID " + detalleDto.getPromocion().getId() + " no encontrada."));
                
                // Calcular insumos de los artículos de la promoción
                for (PromocionArticulo pa : promocion.getPromocionArticuloList()) {
                    Articulo articulo = pa.getIdArticulo();
                    int cantidadPromo = pa.getCantidad() * detalleDto.getCantidad();
                    
                    if (articulo instanceof ArticuloManufacturado) {
                        ArticuloManufacturado manufacturado = (ArticuloManufacturado) articulo;
                        for (ArticuloManufacturadoDetalleInsumo detalle : manufacturado.getDetalleInsumos()) {
                            Long insumoId = detalle.getArticuloInsumo().getId();
                            int cantidadNecesaria = detalle.getCantidad() * cantidadPromo;
                            insumosNecesarios.merge(insumoId, cantidadNecesaria, Integer::sum);
                        }
                    } else if (articulo instanceof ArticuloInsumo) {
                        Long insumoId = articulo.getId();
                        insumosNecesarios.merge(insumoId, cantidadPromo, Integer::sum);
                    }
                }
            }
        }

        // 4. Verificar stock disponible y generar respuesta detallada
        StockCheckResponse stockResponse = verificarYValidarStock(insumosNecesarios, sucursal.getId());
        
        if (!stockResponse.isHayStockSuficiente()) {
            logger.warn("Stock insuficiente para crear pedido. Productos faltantes: {}", 
                    stockResponse.getProductosFaltantes().size());
            throw new RuntimeException("Stock insuficiente: " + stockResponse.getMensaje() + 
                    ". Productos faltantes: " + stockResponse.getProductosFaltantes());
        }

        // 5. Stock validado - Proceder con la creación del pedido
        logger.info("Stock validado correctamente. Procediendo con la creación del pedido.");
        
        Pedido pedido = pedidoMapper.toEntity(dto);
        pedido.setEstadoPedido(estadoPedido);
        pedido.setSucursal(sucursal);
        pedido.setTipoEnvio(tipoEnvio);
        pedido.setTipoPago(tipoPago);
        pedido.setUsuario(usuario);
        pedido.setDetallePedidoList(new ArrayList<>());
        pedido.setDetallePromocionList(new ArrayList<>());

        // 6. Manejar DireccionPedido
        if (tipoEnvio.getTipoDelivery() == TypeDelivery.DELIVERY || tipoEnvio.getTipoDelivery() == TypeDelivery.TAKEAWAY) {
            if (dto.getDireccion() == null || dto.getDireccion().getDireccion() == null) {
                throw new RuntimeException("Para el tipo de envío " + tipoEnvio.getTipoDelivery() + ", se requiere una Dirección de Pedido completa.");
            }
            Direccion direccionDetails = direccionMapper.toEntity(dto.getDireccion().getDireccion());
            direccionRepository.save(direccionDetails);
            DireccionPedido direccionPedido = direccionPedidoMapper.toEntity(dto.getDireccion());
            direccionPedido.setDireccion(direccionDetails);
            direccionPedido.setPedido(pedido);
            pedido.setDireccionPedido(direccionPedido);
        } else {
            pedido.setDireccionPedido(null);
        }

        // 7. Guardar pedido
        Pedido savedPedido = pedidoRepository.save(pedido);

        // 8. Agregar DetallePedido
        if (dto.getDetallePedidoList() != null && !dto.getDetallePedidoList().isEmpty()) {
            for (DetallePedidoDTO detalleDto : dto.getDetallePedidoList()) {
                Articulo articulo = articuloRepository.findById(detalleDto.getArticulo().getId())
                        .orElseThrow(() -> new RuntimeException("Artículo con ID " + detalleDto.getArticulo().getId() + " no encontrado."));
                DetallePedido detallePedido = detallePedidoMapper.toEntity(detalleDto);
                detallePedido.setPedido(savedPedido);
                detallePedido.setArticulo(articulo);
                savedPedido.getDetallePedidoList().add(detallePedido);
            }
        }

        // 9. Agregar DetallePromocion
        if (dto.getDetallePromocionList() != null && !dto.getDetallePromocionList().isEmpty()) {
            for (DetallePromocionDTO detalleDto : dto.getDetallePromocionList()) {
                Promocion promocion = promocionRepository.findById(detalleDto.getPromocion().getId())
                        .orElseThrow(() -> new RuntimeException("Promoción con ID " + detalleDto.getPromocion().getId() + " no encontrada."));
                DetallePromocion detallePromocion = detallePromocionMapper.toEntity(detalleDto);
                detallePromocion.setPedido(savedPedido);
                detallePromocion.setPromocion(promocion);
                savedPedido.getDetallePromocionList().add(detallePromocion);
            }
        }

        // 10. REDUCIR STOCK y actualizar existencia de insumos
        reducirStockYActualizarExistencia(insumosNecesarios, sucursal.getId());

        // 11. Guardar cambios finales
        Pedido finalPedido = pedidoRepository.save(savedPedido);
        logger.info("Pedido creado exitosamente con ID: {}", finalPedido.getId());
        
        return pedidoMapper.toPedidoConDireccionDto(finalPedido);
    }

    /**
     * Verifica el stock disponible para los insumos necesarios
     */
    private StockCheckResponse verificarYValidarStock(Map<Long, Integer> insumosNecesarios, Long sucursalId) {
        StockCheckResponse response = new StockCheckResponse(true, "Stock suficiente");
        
        for (Map.Entry<Long, Integer> entry : insumosNecesarios.entrySet()) {
            Long insumoId = entry.getKey();
            Integer cantidadNecesaria = entry.getValue();
            
            try {
                ArticuloInsumo insumo = (ArticuloInsumo) articuloRepository.findById(insumoId)
                        .orElseThrow(() -> new RuntimeException("Insumo con ID " + insumoId + " no encontrado."));
                
                StockArticuloInsumo stock = insumo.getStockArticuloInsumo();
                
                if (stock == null || !stock.getSucursal().getId().equals(sucursalId)) {
                    response.agregarProductoFaltante(insumoId, insumo.getNombre(), cantidadNecesaria);
                    response.setHayStockSuficiente(false);
                    continue;
                }
                
                if (stock.getCantidad() < cantidadNecesaria) {
                    int faltante = cantidadNecesaria - stock.getCantidad();
                    response.agregarProductoFaltante(insumoId, insumo.getNombre(), faltante);
                    response.setHayStockSuficiente(false);
                }
            } catch (Exception e) {
                logger.error("Error al verificar stock del insumo ID {}: {}", insumoId, e.getMessage());
                response.agregarProductoFaltante(insumoId, "Insumo ID " + insumoId, cantidadNecesaria);
                response.setHayStockSuficiente(false);
            }
        }
        
        if (!response.isHayStockSuficiente()) {
            response.setMensaje("Stock insuficiente para algunos productos");
        }
        
        return response;
    }

    /**
     * Reduce el stock de los insumos y actualiza su existencia según cantidad mínima
     */
    private void reducirStockYActualizarExistencia(Map<Long, Integer> insumosNecesarios, Long sucursalId) {
        for (Map.Entry<Long, Integer> entry : insumosNecesarios.entrySet()) {
            Long insumoId = entry.getKey();
            Integer cantidadAReducir = entry.getValue();
            
            try {
                ArticuloInsumo insumo = (ArticuloInsumo) articuloRepository.findById(insumoId)
                        .orElseThrow(() -> new RuntimeException("Insumo con ID " + insumoId + " no encontrado."));
                
                StockArticuloInsumo stock = insumo.getStockArticuloInsumo();
                
                if (stock != null && stock.getSucursal().getId().equals(sucursalId)) {
                    // Reducir stock
                    int nuevoStock = stock.getCantidad() - cantidadAReducir;
                    stock.setCantidad(nuevoStock);
                    
                    // Actualizar existencia según cantidad mínima
                    if (nuevoStock < stock.getMinStock()) {
                        logger.warn("Stock del insumo {} ({}) por debajo del mínimo. Marcando como no existente.", 
                                insumo.getNombre(), insumoId);
                        insumo.setExiste(false);
                    }
                    
                    articuloRepository.save(insumo);
                    logger.debug("Stock reducido para insumo ID {}: {} -> {}", insumoId, 
                            stock.getCantidad() + cantidadAReducir, nuevoStock);
                }
            } catch (Exception e) {
                logger.error("Error al reducir stock del insumo ID {}: {}", insumoId, e.getMessage());
                throw new RuntimeException("Error al reducir stock del insumo ID " + insumoId, e);
            }
        }
    }

    @Transactional
    public PedidoConDireccionDTO actualizarPedido(Long id, PedidoConDireccionDTO dto) throws Exception {
        // Busca el pedido existente o lanza una excepción si no se encuentra.
        Pedido existingPedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido con ID " + id + " no encontrado para actualizar."));

        // 1. Obtener y establecer relaciones ManyToOne (entidades existentes desde la BD)
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

        // 2. Actualizar campos básicos del Pedido existente usando el mapper y asignaciones directas
        pedidoMapper.updatePedidoFromDto(dto, existingPedido);
        existingPedido.setTiempoEstimado(dto.getTiempoEstimado()); // Asignación directa de String

        // Asignar las entidades completas recuperadas de la base de datos
        existingPedido.setEstadoPedido(estadoPedido);
        existingPedido.setSucursal(sucursal);
        existingPedido.setTipoEnvio(tipoEnvio);
        existingPedido.setTipoPago(tipoPago);
        existingPedido.setUsuario(usuario);

        // 3. Manejar DireccionPedido (condicionalmente según el Tipo de Envío)
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

                existingPedido.setDireccionPedido(newDireccionPedido);
            } else {
                // Existe DireccionPedido asociada, actualizarla
                DireccionPedido currentDireccionPedido = existingPedido.getDireccionPedido();
                Direccion currentDireccionDetails = currentDireccionPedido.getDireccion();

                // Actualizar detalles de Direccion anidados
                direccionMapper.updateDireccionFromDto(dto.getDireccion().getDireccion(), currentDireccionDetails);
                direccionRepository.save(currentDireccionDetails);

                // Actualizar DireccionPedido si tiene otros campos
                direccionPedidoMapper.updateDireccionPedidoFromDto(dto.getDireccion(), currentDireccionPedido);
            }
        } else {
            // Si el tipo de envío no requiere DireccionPedido, se elimina si existía
            if (existingPedido.getDireccionPedido() != null) {
                logger.info("El TipoEnvio cambió a NO-DELIVERY/NO-TAKEAWAY. Eliminando DireccionPedido existente del pedido ID: {}", id);
                if (existingPedido.getDireccionPedido().getDireccion() != null) {
                    direccionRepository.delete(existingPedido.getDireccionPedido().getDireccion());
                }
                existingPedido.setDireccionPedido(null);
            }
            if (dto.getDireccion() != null) {
                logger.warn("Se recibió una DireccionPedidoDTO para un Tipo de Envío (" + tipoEnvio.getTipoDelivery() + ") que no la requiere en la actualización. Será ignorada.");
            }
        }

        // 4. Manejar DetallePedido (OneToMany) - Lógica de reemplazo completo (clear and add)
        existingPedido.getDetallePedidoList().clear(); // CUIDADO: Esto elimina los detalles anteriores

        if (dto.getDetallePedidoList() != null && !dto.getDetallePedidoList().isEmpty()) {
            for (DetallePedidoDTO detalleDto : dto.getDetallePedidoList()) {
                if (detalleDto.getArticulo() == null) {
                    continue; // Omitir detalle sin artículo válido
                }

                Articulo articulo = articuloRepository.findById(detalleDto.getArticulo().getId())
                        .orElseThrow(() -> new RuntimeException("Artículo con ID " + detalleDto.getArticulo().getId() + " no encontrado para DetallePedido."));

                DetallePedido newDetalle = detallePedidoMapper.toEntity(detalleDto);
                newDetalle.setPedido(existingPedido);
                newDetalle.setArticulo(articulo);
                existingPedido.getDetallePedidoList().add(newDetalle);
            }
        }

        // 5. Manejar DetallePromocion (OneToMany) - Lógica de reemplazo completo (clear and add)
        existingPedido.getDetallePromocionList().clear(); // CUIDADO: Esto elimina los detalles anteriores

        if (dto.getDetallePromocionList() != null && !dto.getDetallePromocionList().isEmpty()) {
            for (DetallePromocionDTO detalleDto : dto.getDetallePromocionList()) {
                if (detalleDto.getPromocion() == null) {
                    continue; // Omitir detalle sin promoción válida
                }

                Promocion promocion = promocionRepository.findById(detalleDto.getPromocion().getId())
                        .orElseThrow(() -> new RuntimeException("Promoción con ID " + detalleDto.getPromocion().getId() + " no encontrada para DetallePromocion."));

                DetallePromocion newDetalle = detallePromocionMapper.toEntity(detalleDto);
                newDetalle.setPedido(existingPedido);
                newDetalle.setPromocion(promocion);
                existingPedido.getDetallePromocionList().add(newDetalle);
            }
        }

        // 6. Guardar la entidad padre actualizada (cascading se encargará de los hijos)
        Pedido updatedPedido = pedidoRepository.save(existingPedido);

        // 7. Devolver el DTO del pedido actualizado
        return pedidoMapper.toPedidoConDireccionDto(updatedPedido);
    }

    @Transactional
    public void eliminarPedido(Long id) throws Exception {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido con ID " + id + " no encontrado para eliminar."));

        try {
            pedido.setExiste(Boolean.FALSE);
            pedidoRepository.save(pedido);
            logger.info("Pedido con ID {} eliminado con éxito.", id);
        } catch (Exception e) {
            logger.error("Error al intentar eliminar el pedido con ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error al eliminar el pedido con ID " + id + ": " + e.getMessage(), e);
        }
    }

    public List<PedidoConDireccionDTO> findAllExistentes() {
        List<Pedido> pedidos = pedidoRepository.findAllByExisteTrue();

        // Mapeamos la lista de entidades a una lista de DTOs
        return pedidos.stream()
                .map(pedidoMapper::toPedidoConDireccionDto)
                .collect(Collectors.toList());
    }

     @Transactional(readOnly = true)
    public PedidoConDireccionDTO findByIdExistente(Long id) {
        try {

            Pedido pedido = (Pedido) pedidoRepository.findByIdAndExisteTrue(id);
               
            
            return pedidoMapper.toPedidoConDireccionDto(pedido);
        } catch (PedidoNotFoundException e) {
        
            logger.warn("Advertencia: {}", e.getMessage()); 
            throw e; 
        } catch (Exception e) {
            
            logger.error("Error inesperado al buscar pedido con ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error interno al buscar pedido con ID " + id, e); // Lanzamos una RuntimeException genérica
        }
    }
}



