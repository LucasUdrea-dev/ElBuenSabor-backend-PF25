package com.buenSabor.BackEnd.repositories.producto;

import com.buenSabor.BackEnd.models.producto.ArticuloManufacturadoDetalleInsumo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloManufacturadoDetalleInsumoRepository
        extends BeanRepository<ArticuloManufacturadoDetalleInsumo, Long> {

}
