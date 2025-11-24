package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.Producto;
import org.example.services.ProductoService;

import java.util.List;
import java.util.Map;

public class ProductoController {

    private final ProductoService productoService;
    private final Gson gson;

    public ProductoController() {
        this.productoService = new ProductoService();
        this.gson = new Gson();
    }

    public void getAllProductos(Context ctx) {
        try {
            List<Producto> productos = productoService.getAllProductos();
            ctx.status(200).json(Map.of("success", true, "data", productos, "count", productos.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getProductoById(Context ctx) {
        try {
            int productoId = Integer.parseInt(ctx.pathParam("id"));
            Producto producto = productoService.getProductoById(productoId);
            ctx.status(200).json(Map.of("success", true, "data", producto));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getProductoBySku(Context ctx) {
        try {
            String sku = ctx.pathParam("sku");
            Producto producto = productoService.getProductoBySku(sku);
            ctx.status(200).json(Map.of("success", true, "data", producto));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getProductosByCategoriaId(Context ctx) {
        try {
            int categoriaId = Integer.parseInt(ctx.pathParam("categoriaId"));
            List<Producto> productos = productoService.getProductosByCategoriaId(categoriaId);
            ctx.status(200).json(Map.of("success", true, "data", productos, "count", productos.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void createProducto(Context ctx) {
        try {
            Producto producto = ctx.bodyAsClass(Producto.class);
            Producto created = productoService.createProducto(producto);
            ctx.status(201).json(Map.of("success", true, "message", "Producto creado exitosamente", "data", created));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("success", false, "error", e.getMessage()));
        } catch (RuntimeException e) {
            ctx.status(409).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void updateProducto(Context ctx) {
        try {
            int productoId = Integer.parseInt(ctx.pathParam("id"));
            Producto producto = ctx.bodyAsClass(Producto.class);
            Producto updated = productoService.updateProducto(productoId, producto);
            ctx.status(200).json(Map.of("success", true, "message", "Producto actualizado exitosamente", "data", updated));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void deleteProducto(Context ctx) {
        try {
            int productoId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = productoService.deleteProducto(productoId);
            if (deleted) {
                ctx.status(200).json(Map.of("success", true, "message", "Producto eliminado exitosamente"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void countProductos(Context ctx) {
        try {
            long count = productoService.countProductos();
            ctx.status(200).json(Map.of("success", true, "count", count));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }
}