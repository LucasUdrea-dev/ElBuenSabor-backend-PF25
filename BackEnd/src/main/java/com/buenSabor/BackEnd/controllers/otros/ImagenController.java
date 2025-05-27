/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.otros;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author oscarloha
 */

@RestController
@RequestMapping("/api/imagenes")
public class ImagenController {

    @PostMapping
    public String subirImagen(@RequestParam("imagen") MultipartFile imagen){

        try {
            
            String directorioDestino = System.getProperty("user.dir") + File.separator + "images";
            File carpetaDestino = new File(directorioDestino);

            if (!carpetaDestino.exists()) {
                carpetaDestino.mkdirs();
            }

            File archivoDestino = new File(carpetaDestino, imagen.getOriginalFilename());
            imagen.transferTo(archivoDestino);

            return "Imagen subida exitosamente: " + archivoDestino.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al subir la imagen";
        }

    }

    @DeleteMapping("/{nombreImagen}")
    public String borrarImagen(@PathVariable String nombreImagen){

        String directorioDestino = System.getProperty("user.dir") + File.separator + "images";
        File archivoAEliminar = new File(directorioDestino, nombreImagen);

        if (archivoAEliminar.exists()) {
            
            if (archivoAEliminar.delete()) {
                return "Imagen eliminada exitosamente: " + nombreImagen;
            } else {
                return "Error al eliminar la imagen: " + nombreImagen;
            }

        } else {
            return "La imagen no existe" + nombreImagen;
        }

    }

    @GetMapping("/{nombreImagen}")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String nombreImagen){

        try {
            
            Path rutaImagen = Paths.get(System.getProperty("user.dir")).resolve("images").resolve(nombreImagen).normalize();

            Resource recurso = new UrlResource(rutaImagen.toUri());

            if (recurso.exists() && recurso.isReadable()) {
                
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + nombreImagen + "\"")
                        .body(recurso);

            }else{
                return ResponseEntity.notFound().build();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

}