package com.buenSabor.BackEnd.controllers.venta;

import com.buenSabor.BackEnd.controllers.bean.BeanControllerImpl;
import com.buenSabor.BackEnd.dto.venta.tipopromo.TipoPromocionDTO;
import com.buenSabor.BackEnd.models.venta.TipoPromocion;
import com.buenSabor.BackEnd.services.venta.TipoPromocionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tipoPromociones")
@Tag(name = "TipoPromocion", description = "Operaciones relacionadas con entidad TipoPromocion")
public class TipoPromocionController extends BeanControllerImpl<TipoPromocion, TipoPromocionService> {

    private final TipoPromocionService tipoPromocionService;

    @Autowired
    public TipoPromocionController(TipoPromocionService tipoPromocionService) {
        this.tipoPromocionService = tipoPromocionService;
    }

    @Operation(summary = "Listar todos los tipos de promociones con DTO")
    @GetMapping("") 
    public ResponseEntity<List<TipoPromocionDTO>> findAllTipoPromocionesDTO() {
        try {
            List<TipoPromocionDTO> dtos = tipoPromocionService.findAllTipoPromocionesDTO();
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
           
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); 
        }
    }

    @Operation(summary = "Ver un tipo de promoción por ID con DTO")
    @GetMapping("/{id}") 
    public ResponseEntity<TipoPromocionDTO> getTipoPromocionDTOById(@PathVariable Long id) {
        try {
            TipoPromocionDTO dto = tipoPromocionService.findTipoPromocionDTOById(id);
            if (dto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); 
            }
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
          
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); 
        }
    }

   

}
