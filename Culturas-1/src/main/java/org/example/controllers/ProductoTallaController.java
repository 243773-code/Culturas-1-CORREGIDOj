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

    public void getAll(Context ctx) {
        try {
            List<ProductoTalla> list = productoTallaService.getAll();
            ctx.status(200).json(Map.of(
                    "success", true,
                    "data", list,
                    "count", list.size()
            ));
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "success", false,
                    "error", "Error al obtener producto_tallas: " + e.getMessage()
            ));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ProductoTalla pt = productoTallaService.getById(id);
            ctx.status(200).json(Map.of(
                    "success", true,
                    "data", pt
            ));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "success", false,
                    "error", "ID inválido"
            ));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "success", false,
                    "error", "Error al obtener producto_talla: " + e.getMessage()
            ));
        }
    }

    public void create(Context ctx) {
        try {
            ProductoTalla pt = ctx.bodyAsClass(ProductoTalla.class);
            ProductoTalla created = productoTallaService.create(pt);

            ctx.status(201).json(Map.of(
                    "success", true,
                    "message", "Producto_talla creado correctamente",
                    "data", created
            ));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "success", false,
                    "error", "Error al crear producto_talla: " + e.getMessage()
            ));
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ProductoTalla pt = ctx.bodyAsClass(ProductoTalla.class);
            ProductoTalla updated = productoTallaService.update(id, pt);

            ctx.status(200).json(Map.of(
                    "success", true,
                    "message", "Producto_talla actualizado correctamente",
                    "data", updated
            ));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "success", false,
                    "error", "ID inválido"
            ));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "success", false,
                    "error", "Error al actualizar producto_talla: " + e.getMessage()
            ));
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = productoTallaService.delete(id);

            if (deleted) {
                ctx.status(200).json(Map.of(
                        "success", true,
                        "message", "Producto_talla eliminado correctamente"
                ));
            } else {
                ctx.status(404).json(Map.of(
                        "success", false,
                        "error", "No se pudo eliminar el registro"
                ));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "success", false,
                    "error", "ID inválido"
            ));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "success", false,
                    "error", "Error al eliminar producto_talla: " + e.getMessage()
            ));
        }
    }

    public void count(Context ctx) {
        try {
            long count = productoTallaService.count();
            ctx.status(200).json(Map.of(
                    "success", true,
                    "count", count
            ));
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "success", false,
                    "error", "Error al contar producto_tallas: " + e.getMessage()
            ));
        }
    }

    // 🔥 PATCH SOLO CANTIDAD
    public void updateCantidad(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));

            // body esperado: { "cantidad": 25 }
            Map<String, Object> body = ctx.bodyAsClass(Map.class);

            if (!body.containsKey("cantidad")) {
                ctx.status(400).json(Map.of(
                        "success", false,
                        "error", "Falta el campo 'cantidad'"
                ));
                return;
            }

            // Gson mapea números a Double por defecto
            int cantidad = ((Number) body.get("cantidad")).intValue();

            boolean updated = productoTallaService.updateCantidad(id, cantidad);

            if (updated) {
                ctx.status(200).json(Map.of(
                        "success", true,
                        "message", "Cantidad actualizada correctamente"
                ));
            } else {
                ctx.status(404).json(Map.of(
                        "success", false,
                        "error", "No se encontró producto_talla con ese ID"
                ));
            }

        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of(
                    "success", false,
                    "error", "ID inválido"
            ));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "success", false,
                    "error", "Error al actualizar cantidad: " + e.getMessage()
            ));
        }
    }
}
