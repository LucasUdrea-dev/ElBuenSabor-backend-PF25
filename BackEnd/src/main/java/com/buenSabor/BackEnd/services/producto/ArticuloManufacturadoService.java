package com.buenSabor.BackEnd.services.producto;

import com.buenSabor.BackEnd.dto.producto.manufacturado.ArticuloManufacturadoDTO;
import com.buenSabor.BackEnd.dto.producto.manufacturadodetalle.ArticuloManufacturadoDetalleInsumoDTO;
import com.buenSabor.BackEnd.mapper.ArticuloMapper;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.models.producto.*;
import com.buenSabor.BackEnd.models.venta.Promocion;
import com.buenSabor.BackEnd.models.venta.PromocionArticulo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.company.SucursalRepository;
import com.buenSabor.BackEnd.repositories.producto.*;
import com.buenSabor.BackEnd.repositories.venta.PromocionRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticuloManufacturadoService extends BeanServiceImpl<ArticuloManufacturado, Long> {

    @Autowired
    ArticuloManufacturadoRepository manufacturadoRepository;
    @Autowired
    SucursalRepository sucursalRepository;
    @Autowired
    SubcategorioRepository subcategorioRepository;
    @Autowired
    UnidadMedidaRepository unidadMedidaRepository;
    @Autowired
    ArticuloInsumoRepository insumoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    ArticuloMapper mapper;
       @Autowired
    private PromocionRepository promocionRepository;
    @Autowired
    private ArticuloManufacturadoRepository articuloManufacturadoRepository;

    public ArticuloManufacturadoService(BeanRepository<ArticuloManufacturado, Long> beanRepository,
            ArticuloManufacturadoRepository manufacturadoRepository, SucursalRepository sucursalRepository,
            SubcategorioRepository subcategorioRepository, UnidadMedidaRepository unidadMedidaRepository,
            ArticuloInsumoRepository insumoRepository, ArticuloMapper mapper) {
        super(beanRepository);
        this.manufacturadoRepository = manufacturadoRepository;
        this.sucursalRepository = sucursalRepository;
        this.subcategorioRepository = subcategorioRepository;
        this.unidadMedidaRepository = unidadMedidaRepository;
        this.insumoRepository = insumoRepository;
        this.mapper = mapper;
    }

    @Transactional
    public ArticuloManufacturadoDTO crearManufacturado(ArticuloManufacturadoDTO dto) {

        // 1) Mapear DTO → Entidad
        ArticuloManufacturado manufacturado = mapper.toEntity(dto);

        // 2) Sustituir referencias por objetos managed
        Subcategoria subManaged = subcategorioRepository.findById(dto.getSubcategoria().getId())
                .orElseThrow(() -> new EntityNotFoundException("Subcategoria no encontrada"));
        manufacturado.setSubcategoria(subManaged);

        UnidadMedida umManaged = unidadMedidaRepository.findById(dto.getUnidadMedida().getId())
                .orElseThrow(() -> new EntityNotFoundException("Unidad de medida no encontrada"));
        manufacturado.setUnidadMedida(umManaged);

        Sucursal sucManaged = sucursalRepository.findById(dto.getSucursalId())
                .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada"));
        manufacturado.setSucursal(sucManaged);

        // 3) Crear detalles de insumo (evitar duplicados)
        List<ArticuloManufacturadoDetalleInsumo> detalles = new java.util.ArrayList<>();
        java.util.Set<Long> insumosYaProcesados = new java.util.HashSet<>();

        for (ArticuloManufacturadoDetalleInsumoDTO d : dto.getInsumos()) {
            Long insumoId = d.getArticuloInsumo().getId();

            // Verificar duplicados
            if (insumosYaProcesados.contains(insumoId)) {
                continue; // Omitir duplicado
            }

            ArticuloManufacturadoDetalleInsumo det = new ArticuloManufacturadoDetalleInsumo();
            det.setArticuloManufacturado(manufacturado);
            det.setArticuloInsumo(
                    insumoRepository.findById(insumoId)
                            .orElseThrow(() -> new EntityNotFoundException("Articulo insumo no encontrado")));
            det.setCantidad(d.getCantidad());
            detalles.add(det);
            insumosYaProcesados.add(insumoId);
        }
        manufacturado.setDetalleInsumos(detalles);

        // 4) Persistir
        ArticuloManufacturado saved = manufacturadoRepository.save(manufacturado);

        // 5) Rellenar el DTO con los IDs generados
        dto.setId(saved.getId());
        dto.setSucursalId(saved.getSucursal().getId());
        dto.setTiempoEstimado(saved.getTiempoEstimado());
        dto.setPreparacion(saved.getPreparacion());

        for (int i = 0; i < dto.getInsumos().size(); i++) {
            Long detalleId = saved.getDetalleInsumos().get(i).getId();
            dto.getInsumos().get(i).setId(detalleId);
        }

        // 6) Devolver el DTO
        return dto;
    }

    @Transactional
    public ArticuloManufacturadoDTO actualizarManufacturado(Long id, ArticuloManufacturadoDTO dto) {
        
        // 1. Recuperar la entidad existente
        ArticuloManufacturado manufacturado = manufacturadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "ArticuloManufacturado con id " + id + " no existe"));

        // --- A. CAPTURAR PRECIO ANTERIOR (Vital para calcular la diferencia) ---
        Double precioAnterior = manufacturado.getPrecio();
        Double precioNuevo = dto.getPrecio();

        // 2. Actualizar campos simples
        manufacturado.setNombre(dto.getNombre());
        manufacturado.setDescripcion(dto.getDescripcion());
        manufacturado.setPrecio(precioNuevo); 
        manufacturado.setExiste(dto.getExiste());
        manufacturado.setEsParaElaborar(dto.getEsParaElaborar());
        manufacturado.setImagenArticulo(dto.getImagenArticulo());
        manufacturado.setTiempoEstimado(dto.getTiempoEstimado());
        manufacturado.setPreparacion(dto.getPreparacion());

        // 3. Sustituir referencias (Subcategoria, Unidad, Sucursal)
        Subcategoria subcategoria = subcategorioRepository.findById(dto.getSubcategoria().getId())
                .orElseThrow(() -> new EntityNotFoundException("Subcategoria no encontrada"));

        if (dto.getSubcategoria().getCategoria() != null && 
            !subcategoria.getCategoria().getId().equals(dto.getSubcategoria().getCategoria().getId())) {
             subcategoria.setCategoria(
                    categoriaRepository.findById(dto.getSubcategoria().getCategoria().getId())
                            .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada")));
        }
        manufacturado.setSubcategoria(subcategoria);
        
        manufacturado.setUnidadMedida(
                unidadMedidaRepository.findById(dto.getUnidadMedida().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Unidad de medida no encontrada")));
        
        manufacturado.setSucursal(
                sucursalRepository.findById(dto.getSucursalId())
                        .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada")));

        // 4. Actualizar detalles de insumo (Logic de eliminar/agregar)
        List<ArticuloManufacturadoDetalleInsumo> actuales = manufacturado.getDetalleInsumos();
        List<ArticuloManufacturadoDetalleInsumoDTO> nuevosDTO = dto.getInsumos();
        
        java.util.Set<Long> insumosEnRequest = new java.util.HashSet<>();
        for (ArticuloManufacturadoDetalleInsumoDTO d : nuevosDTO) {
            if (d.getArticuloInsumo() != null && d.getArticuloInsumo().getId() != null) {
                insumosEnRequest.add(d.getArticuloInsumo().getId());
            }
        }

        actuales.removeIf(det -> !insumosEnRequest.contains(det.getArticuloInsumo().getId()));

        java.util.Set<Long> procesados = new java.util.HashSet<>();
        for (ArticuloManufacturadoDetalleInsumoDTO d : nuevosDTO) {
            Long insumoId = d.getArticuloInsumo().getId();

            if (procesados.contains(insumoId)) continue; 
            procesados.add(insumoId);

            Optional<ArticuloManufacturadoDetalleInsumo> opt = actuales.stream()
                    .filter(det -> det.getArticuloInsumo().getId().equals(insumoId))
                    .findFirst();

            if (opt.isPresent()) {
                opt.get().setCantidad(d.getCantidad());
            } else {
                ArticuloManufacturadoDetalleInsumo nuevoDetalle = new ArticuloManufacturadoDetalleInsumo();
                nuevoDetalle.setArticuloManufacturado(manufacturado);
                nuevoDetalle.setArticuloInsumo(insumoRepository.findById(insumoId)
                        .orElseThrow(() -> new EntityNotFoundException("Articulo insumo no encontrado con ID: " + insumoId)));
                nuevoDetalle.setCantidad(d.getCantidad());
                actuales.add(nuevoDetalle);
            }
        }

       
        if (precioNuevo != null && precioAnterior != null && !precioNuevo.equals(precioAnterior)) {
            
            
            double diferencia = precioNuevo - precioAnterior;

        
            List<PromocionArticulo> promocionesRelacionadas = manufacturado.getPromocionArticuloList();

            if (promocionesRelacionadas != null) {
                for (PromocionArticulo pa : promocionesRelacionadas) {
                    
                    int cantidadEnPromo = pa.getCantidad(); 
                    if (cantidadEnPromo > 0) {
                        Promocion promo = pa.getIdPromocion(); 
                        
                        double impactoTotal = diferencia * cantidadEnPromo;
                        
                       
                        double precioPromoActual = promo.getPrecioRebajado() != null ? promo.getPrecioRebajado() : 0.0;
                        double nuevoPrecioFinal = precioPromoActual + impactoTotal;
                        
                        promo.setPrecioRebajado(nuevoPrecioFinal);
                        
                       
                        promocionRepository.save(promo);
                    }
                }
            }
        }

        // 5. Guardar cambios del manufacturado
        ArticuloManufacturado saved = manufacturadoRepository.save(manufacturado);

        // 6. Rellenar DTO de respuesta
        dto.setId(saved.getId());
        dto.getSubcategoria().setDenominacion(saved.getSubcategoria().getDenominacion());
        if (saved.getSubcategoria().getCategoria() != null) {
            dto.getSubcategoria().getCategoria()
                    .setDenominacion(saved.getSubcategoria().getCategoria().getDenominacion());
            dto.getSubcategoria().getCategoria().setId(saved.getSubcategoria().getCategoria().getId());
        }
        dto.setSucursalId(saved.getSucursal().getId());
        
        // Mapeo seguro de insumos de respuesta
        List<ArticuloManufacturadoDetalleInsumoDTO> insumosRespuesta = new ArrayList<>();
        for(ArticuloManufacturadoDetalleInsumo detalle : saved.getDetalleInsumos()){
            ArticuloManufacturadoDetalleInsumoDTO detDto = new ArticuloManufacturadoDetalleInsumoDTO();
            detDto.setId(detalle.getId());
            detDto.setCantidad(detalle.getCantidad());
            // Aquí deberías mapear también el InsumoDTO interno si lo necesitas en el front
            insumosRespuesta.add(detDto);
        }
        dto.setInsumos(insumosRespuesta);

        return dto;
    }
    
    @Transactional
    public ArticuloManufacturado eliminarLogico(Long id) {
        // Recupero articulo a eliminar
        ArticuloManufacturado manufacturado = articuloManufacturadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Articulo manufacturado con id " + id + " no existe"));

        // verifico que el articulo existe(atributo)
        if (!manufacturado.getExiste()) {
            throw new IllegalStateException(
                    "El articulo manufacturado con id " + id + " ya no existe");
        }

        // Seteo valor y guardo
        manufacturado.setExiste(false);
        return articuloManufacturadoRepository.save(manufacturado);
    }

    public List<ArticuloManufacturado> findByExisteTrue() throws Exception {
        try {
            return articuloManufacturadoRepository.findByExisteTrue();
        } catch (Exception e) {
            throw new Exception("Error al buscar insumos disponibles: " + e.getMessage());
        }
    }

    public List<ArticuloManufacturado> buscarPorNombre(String nombre) throws Exception {
        try {
            return articuloManufacturadoRepository.findByNombreContainingIgnoreCase(nombre);
        } catch (Exception e) {
            throw new Exception("Error al buscar por nombre: " + e.getMessage());
        }
    }

    public List<ArticuloManufacturado> buscarPorSubcategoria(Long subCategoriaId) throws Exception {
        try {
            return articuloManufacturadoRepository.findBySubcategoria_Id(subCategoriaId);
        } catch (Exception e) {
            throw new Exception("Error al buscar por subcategoría: " + e.getMessage());
        }
    }

}
