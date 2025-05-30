/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.controllers.otros;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 *
 * @author oscarloha
 */
@RestController
@RequestMapping("/api/imagenes")
public class ImagenController {

    private static final Logger logger = LoggerFactory.getLogger(ImagenController.class);

    @Value("${imagenes.ruta:images}")
    private String rutaImagenes;

    @PostMapping
    public ResponseEntity<String> subirImagen(@RequestParam("imagen") MultipartFile imagen) {
        try {
            if (imagen.isEmpty()) {
                return ResponseEntity.badRequest().body("No se recibió ningún archivo.");
            }

            if (!imagen.getContentType().startsWith("image/")) {
                return ResponseEntity.badRequest().body("El archivo no es una imagen válida.");
            }

            String nombreArchivo = StringUtils.cleanPath(imagen.getOriginalFilename());

            Path carpetaDestino = Paths.get(rutaImagenes).toAbsolutePath().normalize();
            Files.createDirectories(carpetaDestino);

            Path rutaArchivo = carpetaDestino.resolve(nombreArchivo);
            imagen.transferTo(rutaArchivo.toFile());

            return ResponseEntity.ok("Imagen subida exitosamente: " + rutaArchivo.toString());

        } catch (IOException | IllegalStateException e) {
            logger.error("Error al subir la imagen", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen");
        }
    }

    @DeleteMapping("/{nombreImagen}")
    public ResponseEntity<String> borrarImagen(@PathVariable String nombreImagen) {
        try {
            String nombreSanitizado = Paths.get(nombreImagen).getFileName().toString();
            Path rutaArchivo = Paths.get(rutaImagenes).toAbsolutePath().resolve(nombreSanitizado);

            File archivo = rutaArchivo.toFile();
            if (!archivo.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La imagen no existe: " + nombreImagen);
            }

            if (archivo.delete()) {
                return ResponseEntity.ok("Imagen eliminada exitosamente: " + nombreImagen);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo eliminar la imagen");
            }

        } catch (Exception e) {
            logger.error("Error al eliminar la imagen", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la imagen");
        }
    }

    @GetMapping("/{nombreImagen}")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String nombreImagen) {
        try {
            String nombreSanitizado = Paths.get(nombreImagen).getFileName().toString();
            Path rutaArchivo = Paths.get(rutaImagenes).toAbsolutePath().resolve(nombreSanitizado).normalize();

            Resource recurso = new UrlResource(rutaArchivo.toUri());

            if (recurso.exists() && recurso.isReadable()) {
                String mimeType = Files.probeContentType(rutaArchivo);
                mimeType = mimeType != null ? mimeType : "application/octet-stream";

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(mimeType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + nombreSanitizado + "\"")
                        .body(recurso);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (MalformedURLException e) {
            logger.error("URL malformada al intentar acceder a la imagen", e);
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            logger.error("Error al detectar el tipo MIME", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
