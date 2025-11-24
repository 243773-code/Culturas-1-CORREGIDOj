package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.Adquisicion;
import org.example.services.AdquisicionService;

import java.util.List;
import java.util.Map;

public class AdquisicionController {

    private final AdquisicionService adquisicionService;
    private final Gson gson;

    public AdquisicionController() {
        this.adquisicionService = new AdquisicionService();
        this.gson = new Gson();
    }

    public void getAllAdquisiciones(Context ctx) {
        try {
            List<Adquisicion> adquisiciones = adquisicionService.getAllAdquisiciones();
            ctx.status(200).json(Map.of("success", true, "data", adquisiciones, "count", adquisiciones.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getAdquisicionById(Context ctx) {
        try {
            int adquisicionId = Integer.parseInt(ctx.pathParam("id"));
            Adquisicion adquisicion = adquisicionService.getAdquisicionById(adquisicionId);
            ctx.status(200).json(Map.of("success", true, "data", adquisicion));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getAdquisicionesByProveedorId(Context ctx) {
        try {
            int proveedorId = Integer.parseInt(ctx.pathParam("proveedorId"));
            List<Adquisicion> adquisiciones = adquisicionService.getAdquisicionesByProveedorId(proveedorId);
            ctx.status(200).json(Map.of("success", true, "data", adquisiciones, "count", adquisiciones.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getAdquisicionesByEmpleadoId(Context ctx) {
        try {
            int empleadoId = Integer.parseInt(ctx.pathParam("empleadoId"));
            List<Adquisicion> adquisiciones = adquisicionService.getAdquisicionesByEmpleadoId(empleadoId);
            ctx.status(200).json(Map.of("success", true, "data", adquisiciones, "count", adquisiciones.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void createAdquisicion(Context ctx) {
        try {
            Adquisicion adquisicion = ctx.bodyAsClass(Adquisicion.class);
            Adquisicion created = adquisicionService.createAdquisicion(adquisicion);
            ctx.status(201).json(Map.of("success", true, "message", "Adquisición creada exitosamente", "data", created));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void updateAdquisicion(Context ctx) {
        try {
            int adquisicionId = Integer.parseInt(ctx.pathParam("id"));
            Adquisicion adquisicion = ctx.bodyAsClass(Adquisicion.class);
            Adquisicion updated = adquisicionService.updateAdquisicion(adquisicionId, adquisicion);
            ctx.status(200).json(Map.of("success", true, "message", "Adquisición actualizada exitosamente", "data", updated));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void deleteAdquisicion(Context ctx) {
        try {
            int adquisicionId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = adquisicionService.deleteAdquisicion(adquisicionId);
            if (deleted) {
                ctx.status(200).json(Map.of("success", true, "message", "Adquisición eliminada exitosamente"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void countAdquisiciones(Context ctx) {
        try {
            long count = adquisicionService.countAdquisiciones();
            ctx.status(200).json(Map.of("success", true, "count", count));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }
}