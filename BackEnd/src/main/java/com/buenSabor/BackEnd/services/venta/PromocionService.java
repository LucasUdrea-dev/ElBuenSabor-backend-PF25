package com.buenSabor.BackEnd.services.venta;

import com.buenSabor.BackEnd.models.venta.Promocion;
import com.buenSabor.BackEnd.models.venta.PromocionArticulo;
import com.buenSabor.BackEnd.repositories.bean.BeanRepository;
import com.buenSabor.BackEnd.repositories.venta.PromocionRepository;
import com.buenSabor.BackEnd.services.bean.BeanServiceImpl;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromocionService extends BeanServiceImpl<Promocion, Long> {

    @Autowired
    private PromocionRepository promocionRepository;

    public PromocionService(BeanRepository<Promocion, Long> beanRepository) {
        super(beanRepository);
    }

    @Override
    @Transactional
    public Promocion save(Promocion promocion) throws Exception{

        try {
            
            if (promocion.getPromocionArticuloList() != null) {
                
                for (PromocionArticulo detalle : promocion.getPromocionArticuloList()) {
                    
                    detalle.setPromocion(promocion);

                }

            }

            return promocionRepository.save(promocion);

        } catch (Exception e) {
            System.err.println("Error al guardar Promocion: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Error al guardar Promocion: " + e.getMessage(), e);
        }

    }

    
    @Override
    @Transactional
    public Promocion update(Long id,Promocion promocion) throws Exception{


        promocion.setId(id);

        try {
            
            if (promocion.getPromocionArticuloList() != null) {
                
                for (PromocionArticulo detalle : promocion.getPromocionArticuloList()) {
                    
                    detalle.setPromocion(promocion);

                }

            }

            return promocionRepository.save(promocion);

        } catch (Exception e) {
            System.err.println("Error al actualizar Promocion: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Error al actualizar Promocion: " + e.getMessage(), e);
        }

    }

    public List<Promocion> obtenerParaVenta() throws Exception{
        try {
            return promocionRepository.findByExisteTrue();
        } catch (Exception e) {
            throw new Exception("Error al obtener promociones para vender" + e.getMessage());
        }
    }
     

}
