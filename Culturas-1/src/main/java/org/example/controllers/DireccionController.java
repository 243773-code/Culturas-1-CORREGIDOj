package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.Direccion;
import org.example.services.DireccionService;

import java.util.List;
import java.util.Map;

public class DireccionController {

    private final DireccionService direccionService;
    private final Gson gson;

    public DireccionController() {
        this.direccionService = new DireccionService();
        this.gson = new Gson();
    }

    public void getAllDirecciones(Context ctx) {
        try {
            List<Direccion> direcciones = direccionService.getAllDirecciones();
            ctx.status(200).json(Map.of("success", true, "data", direcciones, "count", direcciones.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getDireccionById(Context ctx) {
        try {
            int direccionId = Integer.parseInt(ctx.pathParam("id"));
            Direccion direccion = direccionService.getDireccionById(direccionId);
            ctx.status(200).json(Map.of("success", true, "data", direccion));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getDireccionesByUsuarioId(Context ctx) {
        try {
            int usuarioId = Integer.parseInt(ctx.pathParam("usuarioId"));
            List<Direccion> direcciones = direccionService.getDireccionesByUsuarioId(usuarioId);
            ctx.status(200).json(Map.of("success", true, "data", direcciones, "count", direcciones.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void createDireccion(Context ctx) {
        try {
            Direccion direccion = ctx.bodyAsClass(Direccion.class);
            Direccion created = direccionService.createDireccion(direccion);
            ctx.status(201).json(Map.of("success", true, "message", "Dirección creada exitosamente", "data", created));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void updateDireccion(Context ctx) {
        try {
            int direccionId = Integer.parseInt(ctx.pathParam("id"));
            Direccion direccion = ctx.bodyAsClass(Direccion.class);
            Direccion updated = direccionService.updateDireccion(direccionId, direccion);
            ctx.status(200).json(Map.of("success", true, "message", "Dirección actualizada exitosamente", "data", updated));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void deleteDireccion(Context ctx) {
        try {
            int direccionId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = direccionService.deleteDireccion(direccionId);
            if (deleted) {
                ctx.status(200).json(Map.of("success", true, "message", "Dirección eliminada exitosamente"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void countDirecciones(Context ctx) {
        try {
            long count = direccionService.countDirecciones();
            ctx.status(200).json(Map.of("success", true, "count", count));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }
}