package com.buenSabor.BackEnd.controllers.company;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.company.sucursal.SucursalCreateDTO;
import com.buenSabor.BackEnd.dto.company.sucursal.SucursalResponseDTO;
import com.buenSabor.BackEnd.dto.company.sucursal.SucursalUpdateDTO;
import com.buenSabor.BackEnd.mapper.SucursalMapper;
import com.buenSabor.BackEnd.models.company.Sucursal;
import com.buenSabor.BackEnd.services.company.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/sucursales")
@Tag(name = "Sucursales", description = "Operaciones relacionadas con la entidad Sucursal")
public class SucursalController extends BeanControllerImpl<Sucursal, SucursalService> {

    private final SucursalService sucursalService;
    private final SucursalMapper sucursalMapper;

    public SucursalController(SucursalService sucursalService, SucursalMapper sucursalMapper) {
        this.sucursalService = sucursalService;
        this.sucursalMapper = sucursalMapper;
    }

    @Operation(summary = "Guardar una nueva sucursal")
    @PostMapping("/crear")
    public ResponseEntity<?> crearSucursal(@Valid @RequestBody SucursalCreateDTO dto) {
        try {
            Sucursal sucursal = sucursalMapper.toEntity(dto);
            Sucursal guardada = sucursalService.save(sucursal);
            return ResponseEntity.status(HttpStatus.CREATED).body(sucursalMapper.toResponseDto(guardada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al crear la sucursal.\"}");
        }
    }

    @Operation(summary = "Listar todas las sucursales")
    @GetMapping("/listar")
    public ResponseEntity<?> findAllSucursales() {
        try {
            List<SucursalResponseDTO> dtos = sucursalMapper.toResponseDtoList(sucursalService.findAll());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener las sucursales.\"}");
        }
    }

    @Operation(summary = "Listar todas las sucursales existentes")
    @GetMapping("")
    public ResponseEntity<?> findAllSucursalesExistentes() {
        try {
            List<SucursalResponseDTO> dtos = sucursalMapper.toResponseDtoList(sucursalService.findAllExistente());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener las sucursales.\"}");
        }
    }

    @Operation(summary = "Obtener una sucursal por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getSucursalById(@PathVariable Long id) {
        try {
            Sucursal sucursal = sucursalService.findById(id);
            if (sucursal == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Sucursal no encontrada.\"}");
            }
            return ResponseEntity.ok(sucursalMapper.toResponseDto(sucursal));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener la sucursal.\"}");
        }
    }

    @Operation(summary = "Actualizar una sucursal")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarSucursal(@PathVariable Long id, @Valid @RequestBody SucursalUpdateDTO dto) {
        try {
            Sucursal sucursal = sucursalService.findById(id);
            if (sucursal == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Sucursal no encontrada.\"}");
            }
            sucursalMapper.updateFromUpdateDto(dto, sucursal);
            Sucursal actualizada = sucursalService.save(sucursal);
            return ResponseEntity.ok(sucursalMapper.toResponseDto(actualizada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al actualizar la sucursal.\"}");
        }
    }

    @Operation(summary = "Eliminar una sucursal")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSucursal(@PathVariable Long id) {
        try {
            Sucursal sucursal = sucursalService.findById(id);
            if (sucursal == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Sucursal no encontrada.\"}");
            }
            sucursalService.eliminarSucursal(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al eliminar la sucursal.\"}");
        }
    }

    @Operation(summary = "Listar sucursales paginadas")
    @GetMapping("/paged")
    public ResponseEntity<?> getPagedSucursales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<SucursalResponseDTO> dtoPage = sucursalService.findAll(pageable)
                    .map(sucursalMapper::toResponseDto);
            return ResponseEntity.ok(dtoPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener las sucursales paginadas.\"}");
        }
    }
}
