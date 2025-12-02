package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.ArticuloManufacturadoDetalleInsumo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloManufacturadoDetalleInsumoRepository
        extends BeanRepository<ArticuloManufacturadoDetalleInsumo, Long> {

    public List<ArticuloManufacturadoDetalleInsumo> findByArticuloInsumo_Id(Long id);

}
