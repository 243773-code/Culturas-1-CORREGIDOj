package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.HistorialPrecio;
import org.example.services.HistorialPrecioService;

import java.util.List;
import java.util.Map;

public class HistorialPrecioController {

    private final HistorialPrecioService historialPrecioService;
    private final Gson gson;

    public HistorialPrecioController() {
        this.historialPrecioService = new HistorialPrecioService();
        this.gson = new Gson();
    }

    public void getAllHistorialPrecios(Context ctx) {
        try {
            List<HistorialPrecio> historial = historialPrecioService.getAllHistorialPrecios();
            ctx.status(200).json(Map.of("success", true, "data", historial, "count", historial.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getHistorialPrecioById(Context ctx) {
        try {
            int historialId = Integer.parseInt(ctx.pathParam("id"));
            HistorialPrecio historial = historialPrecioService.getHistorialPrecioById(historialId);
            ctx.status(200).json(Map.of("success", true, "data", historial));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getHistorialPreciosByProductoId(Context ctx) {
        try {
            int productoId = Integer.parseInt(ctx.pathParam("productoId"));
            List<HistorialPrecio> historial = historialPrecioService.getHistorialPreciosByProductoId(productoId);
            ctx.status(200).json(Map.of("success", true, "data", historial, "count", historial.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void createHistorialPrecio(Context ctx) {
        try {
            HistorialPrecio historial = ctx.bodyAsClass(HistorialPrecio.class);
            HistorialPrecio created = historialPrecioService.createHistorialPrecio(historial);
            ctx.status(201).json(Map.of("success", true, "message", "Historial de precio creado exitosamente", "data", created));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void updateHistorialPrecio(Context ctx) {
        try {
            int historialId = Integer.parseInt(ctx.pathParam("id"));
            HistorialPrecio historial = ctx.bodyAsClass(HistorialPrecio.class);
            HistorialPrecio updated = historialPrecioService.updateHistorialPrecio(historialId, historial);
            ctx.status(200).json(Map.of("success", true, "message", "Historial de precio actualizado exitosamente", "data", updated));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void deleteHistorialPrecio(Context ctx) {
        try {
            int historialId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = historialPrecioService.deleteHistorialPrecio(historialId);
            if (deleted) {
                ctx.status(200).json(Map.of("success", true, "message", "Historial de precio eliminado exitosamente"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void countHistorialPrecios(Context ctx) {
        try {
            long count = historialPrecioService.countHistorialPrecios();
            ctx.status(200).json(Map.of("success", true, "count", count));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }
}