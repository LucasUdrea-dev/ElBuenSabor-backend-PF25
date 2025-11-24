package com.buenSabor.BackEnd.mapper;

import com.buenSabor.BackEnd.config.mapperConfig;
import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloCreateDTO;
import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloResponseDTO;
import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloUpdateDTO;
import com.buenSabor.BackEnd.dto.producto.insumo.InsumoCreateDTO;
import com.buenSabor.BackEnd.dto.producto.insumo.InsumoResponseDTO;
import com.buenSabor.BackEnd.dto.producto.insumo.InsumoUpdateDTO;
import com.buenSabor.BackEnd.dto.producto.manufacturado.ArticuloManufacturadoCreateDTO;
import com.buenSabor.BackEnd.dto.producto.manufacturado.ArticuloManufacturadoResponseDTO;
import com.buenSabor.BackEnd.dto.producto.manufacturado.ArticuloManufacturadoUpdateDTO;
import com.buenSabor.BackEnd.dto.producto.manufacturadodetalle.ArticuloManufacturadoDetalleResponseDTO;
import com.buenSabor.BackEnd.mapper.bean.BeanMapper;
import com.buenSabor.BackEnd.models.producto.Articulo;
import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturado;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.SubclassMapping;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
@Mapper(
    componentModel = "spring",
    config = mapperConfig.class,
    uses = {
        SubcategoriaMapper.class,
        UnidadMedidaMapper.class,
        StockArticuloInsumoMapper.class,
        ArticuloManufacturadoDetalleInsumoMapper.class 
    }
)
public interface ArticuloMapper extends BeanMapper<
    Articulo,               // Entidad Padre
    ArticuloResponseDTO,    // DTO Respuesta Padre (o genérico)
    ArticuloCreateDTO,      // DTO Crear Padre
    ArticuloUpdateDTO,      // DTO Actualizar Padre
    ArticuloCadenaSimpleDTO // DTO Simple
> {

    // =================================================================
    // 1. DE ENTIDAD A RESPONSE DTO (Lectura)
    // =================================================================

    @Override
    @SubclassMapping(source = ArticuloInsumo.class, target = InsumoResponseDTO.class)
    @SubclassMapping(source = ArticuloManufacturado.class, target = ArticuloManufacturadoResponseDTO.class)
    ArticuloResponseDTO toResponseDto(Articulo entity);

    // --- Métodos específicos para las subclases (MapStruct los usa internamente) ---

    
    InsumoResponseDTO toInsumoResponseDto(ArticuloInsumo entity);

   
    ArticuloManufacturadoResponseDTO toManufacturadoResponseDto(ArticuloManufacturado entity);


    // =================================================================
    // 2. DE CREATE DTO A ENTIDAD (Creación)
    // =================================================================

    @Override
    @SubclassMapping(source = InsumoCreateDTO.class, target = ArticuloInsumo.class)
    @SubclassMapping(source = ArticuloManufacturadoCreateDTO.class, target = ArticuloManufacturado.class)
    // NOTA: He quitado @Mapping(target="id", ignore=true) porque tu error indicaba que Articulo no tiene ID visible.
    Articulo toEntity(ArticuloCreateDTO createDto);

    // Mapeo específico para Insumo
    // (MapStruct ignorará automáticamente los campos que no coinciden gracias a tu mapperConfig)
    ArticuloInsumo toEntity(InsumoCreateDTO dto);

    // Mapeo específico para Manufacturado
    @Mapping(target = "detalleInsumos", ignore = true) // Se suelen crear en un paso posterior o service
    @Mapping(target = "sucursal", ignore = true)       // Se suele asignar en el service
    ArticuloManufacturado toEntity(ArticuloManufacturadoCreateDTO dto);


    // =================================================================
    // 3. DE UPDATE DTO A ENTIDAD (Actualización)
    // =================================================================

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    // He quitado el ignore de ID para evitar el error "Unknown property id"
    void updateFromUpdateDto(ArticuloUpdateDTO updateDto, @MappingTarget Articulo entity);

    // Métodos específicos para redirigir el cast
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInsumoFromDto(InsumoUpdateDTO dto, @MappingTarget ArticuloInsumo entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "detalleInsumos", ignore = true) // Manejar lista manualmente si es necesario
    @Mapping(target = "sucursal", ignore = true)
    void updateManufacturadoFromDto(ArticuloManufacturadoUpdateDTO dto, @MappingTarget ArticuloManufacturado entity);



}