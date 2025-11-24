package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.DetalleAdquisicion;
import org.example.services.DetalleAdquisicionService;

import java.util.List;
import java.util.Map;

public class DetalleAdquisicionController {

    private final DetalleAdquisicionService detalleAdquisicionService;
    private final Gson gson;

    public DetalleAdquisicionController() {
        this.detalleAdquisicionService = new DetalleAdquisicionService();
        this.gson = new Gson();
    }

    public void getAllDetallesAdquisicion(Context ctx) {
        try {
            List<DetalleAdquisicion> detalles = detalleAdquisicionService.getAllDetallesAdquisicion();
            ctx.status(200).json(Map.of("success", true, "data", detalles, "count", detalles.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getDetalleAdquisicionById(Context ctx) {
        try {
            int detalleId = Integer.parseInt(ctx.pathParam("id"));
            DetalleAdquisicion detalle = detalleAdquisicionService.getDetalleAdquisicionById(detalleId);
            ctx.status(200).json(Map.of("success", true, "data", detalle));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getDetallesByAdquisicionId(Context ctx) {
        try {
            int adquisicionId = Integer.parseInt(ctx.pathParam("adquisicionId"));
            List<DetalleAdquisicion> detalles = detalleAdquisicionService.getDetallesByAdquisicionId(adquisicionId);
            ctx.status(200).json(Map.of("success", true, "data", detalles, "count", detalles.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void createDetalleAdquisicion(Context ctx) {
        try {
            DetalleAdquisicion detalle = ctx.bodyAsClass(DetalleAdquisicion.class);
            DetalleAdquisicion created = detalleAdquisicionService.createDetalleAdquisicion(detalle);
            ctx.status(201).json(Map.of("success", true, "message", "Detalle creado exitosamente", "data", created));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void updateDetalleAdquisicion(Context ctx) {
        try {
            int detalleId = Integer.parseInt(ctx.pathParam("id"));
            DetalleAdquisicion detalle = ctx.bodyAsClass(DetalleAdquisicion.class);
            DetalleAdquisicion updated = detalleAdquisicionService.updateDetalleAdquisicion(detalleId, detalle);
            ctx.status(200).json(Map.of("success", true, "message", "Detalle actualizado exitosamente", "data", updated));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void deleteDetalleAdquisicion(Context ctx) {
        try {
            int detalleId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = detalleAdquisicionService.deleteDetalleAdquisicion(detalleId);
            if (deleted) {
                ctx.status(200).json(Map.of("success", true, "message", "Detalle eliminado exitosamente"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void countDetallesAdquisicion(Context ctx) {
        try {
            long count = detalleAdquisicionService.countDetallesAdquisicion();
            ctx.status(200).json(Map.of("success", true, "count", count));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }
}