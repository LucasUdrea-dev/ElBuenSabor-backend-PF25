/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto;

import com.buenSabor.BackEnd.models.producto.ArticuloInsumo;
import com.buenSabor.BackEnd.models.producto.Subcategoria;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author oscarloha
 */

public class ArticuloVentaDTO {
    
    public class ArticuloDto {

    private Long id; // (Bean)

    private String nombre; // (Articulo)
    private String descripcion; // (Articulo)
    private Double precio; // (Articulo)
    private Boolean existe; // (Articulo)
    private Boolean esParaElaborar; // (Articulo)
    private String imagenArticulo; // (Articulo)
    private Subcategoria subCategoria; // (Articulo)
    private Long unidadMedidaId; // (Articulo)
    // ----- ArticuloManufacturado -----
    private Date tiempoEstimado; // (ArticuloManufacturado)
    //detalle 
    private List<ArticuloInsumo> insumos = new ArrayList<>();

        public ArticuloDto() {
        }

        public ArticuloDto(Long id, String nombre, String descripcion, Double precio, Boolean existe, Boolean esParaElaborar, String imagenArticulo, Subcategoria subCategoria, Long unidadMedidaId, Date tiempoEstimado) {
            this.id = id;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.precio = precio;
            this.existe = existe;
            this.esParaElaborar = esParaElaborar;
            this.imagenArticulo = imagenArticulo;
            this.subCategoria = subCategoria;
            this.unidadMedidaId = unidadMedidaId;
            this.tiempoEstimado = tiempoEstimado;
        }

        public List<ArticuloInsumo> getInsumos() {
            return insumos;
        }

        public void setInsumos(List<ArticuloInsumo> insumos) {
            this.insumos = insumos;
        }

        
       

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public Double getPrecio() {
            return precio;
        }

        public void setPrecio(Double precio) {
            this.precio = precio;
        }

        public Boolean getExiste() {
            return existe;
        }

        public void setExiste(Boolean existe) {
            this.existe = existe;
        }

        public Boolean getEsParaElaborar() {
            return esParaElaborar;
        }

        public void setEsParaElaborar(Boolean esParaElaborar) {
            this.esParaElaborar = esParaElaborar;
        }

        public String getImagenArticulo() {
            return imagenArticulo;
        }

        public void setImagenArticulo(String imagenArticulo) {
            this.imagenArticulo = imagenArticulo;
        }

        public Subcategoria getSubCategoria() {
            return subCategoria;
        }

        public void setSubCategoria(Subcategoria subCategoria) {
            this.subCategoria = subCategoria;
        }

        public Long getUnidadMedidaId() {
            return unidadMedidaId;
        }

        public void setUnidadMedidaId(Long unidadMedidaId) {
            this.unidadMedidaId = unidadMedidaId;
        }

        public Date getTiempoEstimado() {
            return tiempoEstimado;
        }

        public void setTiempoEstimado(Date tiempoEstimado) {
            this.tiempoEstimado = tiempoEstimado;
        }
    
    
    
}

}
