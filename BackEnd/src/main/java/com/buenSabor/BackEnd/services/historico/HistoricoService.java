package com.buenSabor.BackEnd.services.historico;

import com.buenSabor.BackEnd.dto.estadisticas.TodosHistoricosDTO;
import com.buenSabor.BackEnd.models.producto.HistoricoPrecioCostoArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.HistoricoPrecioVentaArticulo;
import com.buenSabor.BackEnd.models.producto.HistoricoStockArticuloInsumo;
import com.buenSabor.BackEnd.models.venta.HistoricoEstadoPedido;
import com.buenSabor.BackEnd.repositories.producto.HistoricoPrecioCostoArticuloInsumoRepository;
import com.buenSabor.BackEnd.repositories.producto.HistoricoPrecioVentaArticuloRepository;
import com.buenSabor.BackEnd.repositories.producto.HistoricoStockArticuloInsumoRepository;
import com.buenSabor.BackEnd.repositories.venta.HistoricoEstadoPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class HistoricoService {

    @Autowired
    private HistoricoPrecioCostoArticuloInsumoRepository historicoPrecioCostoRepository;

    @Autowired
    private HistoricoPrecioVentaArticuloRepository historicoPrecioVentaRepository;

    @Autowired
    private HistoricoStockArticuloInsumoRepository historicoStockRepository;

    @Autowired
    private HistoricoEstadoPedidoRepository historicoEstadoPedidoRepository;

    public List<HistoricoPrecioCostoArticuloInsumo> obtenerHistoricoPrecioCostoInsumo(Long articuloInsumoId) {
        return historicoPrecioCostoRepository.findByIdArticuloInsumo_IdOrderByFechaDesc(articuloInsumoId);
    }

    public List<HistoricoPrecioVentaArticulo> obtenerHistoricoPrecioVentaArticulo(Long articuloId) {
        return historicoPrecioVentaRepository.findByIdArticulo_IdOrderByFechaDesc(articuloId);
    }

    public List<HistoricoStockArticuloInsumo> obtenerHistoricoStockInsumo(Long stockId) {
        return historicoStockRepository.findByIdstockarticuloInsumo_IdOrderByFechaActualizacionDesc(stockId);
    }

    public List<HistoricoEstadoPedido> obtenerHistoricoEstadosPedido(Long pedidoId) {
        return historicoEstadoPedidoRepository.findByPedido_IdOrderByFechaCambioDesc(pedidoId);
    }

    public HistoricoEstadoPedido obtenerUltimoEstadoPedido(Long pedidoId) {
        List<HistoricoEstadoPedido> historicos = historicoEstadoPedidoRepository.findByPedido_IdOrderByFechaCambioDesc(pedidoId);
        return historicos.isEmpty() ? null : historicos.get(0);
    }

    public HistoricoPrecioCostoArticuloInsumo obtenerUltimoPrecioCostoInsumo(Long articuloInsumoId) {
        List<HistoricoPrecioCostoArticuloInsumo> historicos = historicoPrecioCostoRepository.findByIdArticuloInsumo_IdOrderByFechaDesc(articuloInsumoId);
        return historicos.isEmpty() ? null : historicos.get(0);
    }

    public HistoricoPrecioVentaArticulo obtenerUltimoPrecioVentaArticulo(Long articuloId) {
        List<HistoricoPrecioVentaArticulo> historicos = historicoPrecioVentaRepository.findByIdArticulo_IdOrderByFechaDesc(articuloId);
        return historicos.isEmpty() ? null : historicos.get(0);
    }

    public HistoricoStockArticuloInsumo obtenerUltimoStockInsumo(Long stockId) {
        List<HistoricoStockArticuloInsumo> historicos = historicoStockRepository.findByIdstockarticuloInsumo_IdOrderByFechaActualizacionDesc(stockId);
        return historicos.isEmpty() ? null : historicos.get(0);
    }

    public List<HistoricoPrecioCostoArticuloInsumo> obtenerTodosHistoricosPrecioCosto() {
        return historicoPrecioCostoRepository.findAll();
    }

    public List<HistoricoPrecioVentaArticulo> obtenerTodosHistoricosPrecioVenta() {
        return historicoPrecioVentaRepository.findAll();
    }

    public List<HistoricoStockArticuloInsumo> obtenerTodosHistoricosStock() {
        return historicoStockRepository.findAll();
    }

    public List<HistoricoEstadoPedido> obtenerTodosHistoricosEstadoPedido() {
        return historicoEstadoPedidoRepository.findAll();
    }

    public TodosHistoricosDTO obtenerTodosLosHistoricos() {
        TodosHistoricosDTO todosHistoricos = new TodosHistoricosDTO();
        todosHistoricos.setHistoricosPrecioCosto(historicoPrecioCostoRepository.findAll());
        todosHistoricos.setHistoricosPrecioVenta(historicoPrecioVentaRepository.findAll());
        todosHistoricos.setHistoricosStock(historicoStockRepository.findAll());
        todosHistoricos.setHistoricosEstadoPedido(historicoEstadoPedidoRepository.findAll());
        return todosHistoricos;
    }
}
