package com.buenSabor.BackEnd.services.stock;

import com.buenSabor.BackEnd.dto.stock.StockCheckRequest;
import com.buenSabor.BackEnd.dto.stock.StockCheckResponse;
import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.ArticuloManufacturado;
import com.buenSabor.BackEnd.models.producto.StockArticuloInsumo;
import com.buenSabor.BackEnd.services.producto.ArticuloInsumoService;
import com.buenSabor.BackEnd.services.producto.ArticuloManufacturadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    @Autowired
    private ArticuloInsumoService insumoService;

    @Autowired
    private ArticuloManufacturadoService manufacturadoService;

    /**
     * Actualiza el stock de un insumo de manera segura, manejando concurrencia
     * @param idInsumo ID del insumo a actualizar
     * @param cantidad Cantidad a sumar (puede ser negativa para restar)
     * @param sucursalId ID de la sucursal
     * @return true si la operación fue exitosa, false si no hay suficiente stock o no se encontró el insumo
     */
    @Transactional
    public boolean actualizarStockInsumo(Long idInsumo, int cantidad, Long sucursalId) {
        try {
            ArticuloInsumo insumo = insumoService.findById(idInsumo);
            if (insumo == null || insumo.getStockArticuloInsumo() == null) {
                return false;
            }
            
            StockArticuloInsumo stock = insumo.getStockArticuloInsumo();
            
            // Verificar que el stock pertenece a la sucursal
            if (!stock.getSucursal().getId().equals(sucursalId)) {
                return false;
            }
            
            // Actualizar stock de manera segura
            return stock.actualizarStock(cantidad);
            
        } catch (Exception e) {
            // Log the error if needed
            return false;
        }
    }

    @Transactional(readOnly = true)
    public StockCheckResponse verificarStock(StockCheckRequest request) {
        StockCheckResponse response = new StockCheckResponse(true, "Stock suficiente para todos los productos");

        for (StockCheckRequest.ProductoCheck producto : request.getProductos()) {
            if (producto.getTipo().equalsIgnoreCase("INSUMO")) {
                verificarStockInsumo(producto, response, request.getSucursalId());
            } else if (producto.getTipo().equalsIgnoreCase("MANUFACTURADO")) {
                verificarStockManufacturado(producto, response, request.getSucursalId());
            }

            if (!response.isHayStockSuficiente()) {
                response.setHayStockSuficiente(false);
                response.setMensaje("No hay stock suficiente para algunos productos");
            }
        }

        return response;
    }

    private void verificarStockInsumo(StockCheckRequest.ProductoCheck producto, StockCheckResponse response,
            Long sucursalId) {
        ArticuloInsumo insumo;
        try {
            insumo = insumoService.findById(producto.getId());
        } catch (Exception e) {
            response.agregarProductoFaltante(producto.getId(), "Producto no encontrado", producto.getCantidad());
            response.setHayStockSuficiente(false);
            return;
        }

        StockArticuloInsumo stock = insumo.getStockArticuloInsumo();

        // Verificar si el stock es de la sucursal correcta
        if (stock == null || !stock.getSucursal().getId().equals(sucursalId)) {
            response.agregarProductoFaltante(insumo.getId(), insumo.getNombre(), producto.getCantidad());
            response.setHayStockSuficiente(false);
            return;
        }

        if (stock.getCantidad() < producto.getCantidad()) {
            int faltante = producto.getCantidad() - stock.getCantidad();
            response.agregarProductoFaltante(insumo.getId(), insumo.getNombre(), faltante);
            response.setHayStockSuficiente(false);
        }
    }

    private void verificarStockManufacturado(StockCheckRequest.ProductoCheck producto, StockCheckResponse response,
            Long sucursalId) {
        ArticuloManufacturado manufacturado;
        try {
            manufacturado = manufacturadoService.findById(producto.getId());
        } catch (Exception e) {
            response.agregarProductoFaltante(producto.getId(), "Producto no encontrado", producto.getCantidad());
            response.setHayStockSuficiente(false);
            return;
        }

        // Verificar si el producto manufacturado pertenece a la sucursal
        if (!manufacturado.getSucursal().getId().equals(sucursalId)) {
            response.agregarProductoFaltante(manufacturado.getId(), manufacturado.getNombre(), producto.getCantidad());
            response.setHayStockSuficiente(false);
            return;
        }

        // Verificar stock de cada insumo necesario para el producto manufacturado
        manufacturado.getDetalleInsumos().forEach(detalle -> {
            ArticuloInsumo insumo = detalle.getArticuloInsumo();
            int cantidadNecesaria = detalle.getCantidad() * producto.getCantidad();
            StockArticuloInsumo stock = insumo.getStockArticuloInsumo();

            // Verificar si el stock es de la sucursal correcta
            if (stock == null || !stock.getSucursal().getId().equals(sucursalId) ||
                    stock.getCantidad() < cantidadNecesaria) {

                int faltante = cantidadNecesaria - (stock != null ? stock.getCantidad() : 0);
                response.agregarProductoFaltante(insumo.getId(),
                        String.format("%s (para %s)", insumo.getNombre(), manufacturado.getNombre()),
                        faltante);
                response.setHayStockSuficiente(false);
            }
        });
    }
}
