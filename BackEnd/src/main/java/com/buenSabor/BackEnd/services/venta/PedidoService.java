package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.models.venta.DetallePedido;
import com.buenSabor.BackEnd.models.venta.DetallePromocion;
import com.buenSabor.BackEnd.models.venta.Pedido;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.venta.PedidoRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;

import jakarta.transaction.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService extends BeanServiceImpl<Pedido, Long> {
    public PedidoService(BeanRepository<Pedido, Long> beanRepository) {
        super(beanRepository);
    }

    @Autowired
    private PedidoRepository pedidoRepository;

    private static final long TIEMPO_MAX_DUPLICADO_MS = 1000;

    @Override
    @Transactional
    public Pedido save(Pedido pedido) throws Exception{

        try {

            Optional<Pedido> ultimoPedidoOpt = pedidoRepository.findTopByUsuarioIdOrderByFechaDesc(pedido.getUsuario().getId());

            if (ultimoPedidoOpt.isPresent()) {
                Pedido ultimoPedido = ultimoPedidoOpt.get();
                
                Duration duration = Duration.between(ultimoPedido.getFecha(), pedido.getFecha());

                long diferenciaDeMs = Math.abs(duration.toMillis());

                if (diferenciaDeMs <= TIEMPO_MAX_DUPLICADO_MS) {
                    return ultimoPedido;
                }

            }
            
            if (pedido.getDetallePedidoList() != null) {
                
                for (DetallePedido detallePedido : pedido.getDetallePedidoList()) {
                    detallePedido.setPedido(pedido);
                }

            }

            if(pedido.getDetallePromocionList() != null){
                for (DetallePromocion detallePromocion : pedido.getDetallePromocionList()) {
                    detallePromocion.setPedido(pedido);
                }
            }

            if (pedido.getDireccionPedido() != null) {
                pedido.getDireccionPedido().setPedido(pedido);
            }

            return pedidoRepository.save(pedido);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

    }

    @Override
    @Transactional
    public Pedido update(Long id, Pedido pedido) throws Exception{

        pedido.setId(id);

        try {
            
            if (pedido.getDetallePedidoList() != null) {
                
                for (DetallePedido detallePedido : pedido.getDetallePedidoList()) {
                    detallePedido.setPedido(pedido);
                }

            }

            if(pedido.getDetallePromocionList() != null){
                for (DetallePromocion detallePromocion : pedido.getDetallePromocionList()) {
                    detallePromocion.setPedido(pedido);
                }
            }

            if (pedido.getDireccionPedido() != null) {
                pedido.getDireccionPedido().setPedido(pedido);
            }

            return pedidoRepository.save(pedido);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

    }
    
    @Transactional
    public List<Pedido> obtenerPedidoPorUsuario(Long id) throws Exception{
        try {
            return pedidoRepository.findByUsuario_Id(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
    
}

