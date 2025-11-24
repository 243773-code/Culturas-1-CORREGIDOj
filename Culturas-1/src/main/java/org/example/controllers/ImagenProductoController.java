package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.ImagenProducto;
import org.example.services.ImagenProductoService;

import java.util.List;
import java.util.Map;

public class ImagenProductoController {

    private final ImagenProductoService imagenProductoService;
    private final Gson gson;

    public ImagenProductoController() {
        this.imagenProductoService = new ImagenProductoService();
        this.gson = new Gson();
    }

    public void getAllImagenes(Context ctx) {
        try {
            List<ImagenProducto> imagenes = imagenProductoService.getAllImagenes();
            ctx.status(200).json(Map.of("success", true, "data", imagenes, "count", imagenes.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getImagenById(Context ctx) {
        try {
            int imagenId = Integer.parseInt(ctx.pathParam("id"));
            ImagenProducto imagen = imagenProductoService.getImagenById(imagenId);
            ctx.status(200).json(Map.of("success", true, "data", imagen));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getImagenesByProductoId(Context ctx) {
        try {
            int productoId = Integer.parseInt(ctx.pathParam("productoId"));
            List<ImagenProducto> imagenes = imagenProductoService.getImagenesByProductoId(productoId);
            ctx.status(200).json(Map.of("success", true, "data", imagenes, "count", imagenes.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void createImagen(Context ctx) {
        try {
            ImagenProducto imagen = ctx.bodyAsClass(ImagenProducto.class);
            ImagenProducto created = imagenProductoService.createImagen(imagen);
            ctx.status(201).json(Map.of("success", true, "message", "Imagen creada exitosamente", "data", created));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void updateImagen(Context ctx) {
        try {
            int imagenId = Integer.parseInt(ctx.pathParam("id"));
            ImagenProducto imagen = ctx.bodyAsClass(ImagenProducto.class);
            ImagenProducto updated = imagenProductoService.updateImagen(imagenId, imagen);
            ctx.status(200).json(Map.of("success", true, "message", "Imagen actualizada exitosamente", "data", updated));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void deleteImagen(Context ctx) {
        try {
            int imagenId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = imagenProductoService.deleteImagen(imagenId);
            if (deleted) {
                ctx.status(200).json(Map.of("success", true, "message", "Imagen eliminada exitosamente"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void countImagenes(Context ctx) {
        try {
            long count = imagenProductoService.countImagenes();
            ctx.status(200).json(Map.of("success", true, "count", count));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }
}