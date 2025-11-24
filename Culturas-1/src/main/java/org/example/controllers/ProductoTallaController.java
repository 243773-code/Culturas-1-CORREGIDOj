package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.ProductoTalla;
import org.example.services.ProductoTallaService;

import java.util.List;
import java.util.Map;

public class ProductoTallaController {

    private final ProductoTallaService productoTallaService;
    private final Gson gson;

    public ProductoTallaController() {
        this.productoTallaService = new ProductoTallaService();
        this.gson = new Gson();
    }

    public void getAllProductoTallas(Context ctx) {
        try {
            List<ProductoTalla> items = productoTallaService.getAllProductoTallas();
            ctx.status(200).json(Map.of("success", true, "data", items, "count", items.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getProductoTallaById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ProductoTalla item = productoTallaService.getProductoTallaById(id);
            ctx.status(200).json(Map.of("success", true, "data", item));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getProductoTallasByProductoId(Context ctx) {
        try {
            int productoId = Integer.parseInt(ctx.pathParam("productoId"));
            List<ProductoTalla> items = productoTallaService.getProductoTallasByProductoId(productoId);
            ctx.status(200).json(Map.of("success", true, "data", items, "count", items.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void createProductoTalla(Context ctx) {
        try {
            ProductoTalla item = ctx.bodyAsClass(ProductoTalla.class);
            ProductoTalla created = productoTallaService.createProductoTalla(item);
            ctx.status(201).json(Map.of("success", true, "message", "Creado exitosamente", "data", created));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void updateProductoTalla(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ProductoTalla item = ctx.bodyAsClass(ProductoTalla.class);
            ProductoTalla updated = productoTallaService.updateProductoTalla(id, item);
            ctx.status(200).json(Map.of("success", true, "message", "Actualizado exitosamente", "data", updated));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void deleteProductoTalla(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = productoTallaService.deleteProductoTalla(id);
            if (deleted) {
                ctx.status(200).json(Map.of("success", true, "message", "Eliminado exitosamente"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void countProductoTallas(Context ctx) {
        try {
            long count = productoTallaService.countProductoTallas();
            ctx.status(200).json(Map.of("success", true, "count", count));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }
}