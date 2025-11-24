package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.Estatus;
import org.example.services.EstatusService;

import java.util.List;
import java.util.Map;

public class EstatusController {

    private final EstatusService estatusService;
    private final Gson gson;
    public EstatusController() {
        this.estatusService = new EstatusService();
        this.gson = new Gson();
    }

    public void getAllEstatus(Context ctx) {
        try {
            List<Estatus> estatuses = estatusService.getAllEstatus();
            ctx.status(200).json(Map.of("success", true, "data", estatuses, "count", estatuses.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getEstatusById(Context ctx) {
        try {
            int estatusId = Integer.parseInt(ctx.pathParam("id"));
            Estatus estatus = estatusService.getEstatusById(estatusId);
            ctx.status(200).json(Map.of("success", true, "data", estatus));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getEstatusByNombre(Context ctx) {
        try {
            String nombre = ctx.pathParam("nombre");
            Estatus estatus = estatusService.getEstatusByNombre(nombre);
            ctx.status(200).json(Map.of("success", true, "data", estatus));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getEstatusByTabla(Context ctx) {
        try {
            String tabla = ctx.pathParam("tabla");
            List<Estatus> estatuses = estatusService.getEstatusByTabla(tabla);
            ctx.status(200).json(Map.of("success", true, "data", estatuses, "count", estatuses.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void createEstatus(Context ctx) {
        try {
            Estatus estatus = ctx.bodyAsClass(Estatus.class);
            Estatus created = estatusService.createEstatus(estatus);
            ctx.status(201).json(Map.of("success", true, "message", "Estatus creado exitosamente", "data", created));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("success", false, "error", e.getMessage()));
        } catch (RuntimeException e) {
            ctx.status(409).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void updateEstatus(Context ctx) {
        try {
            int estatusId = Integer.parseInt(ctx.pathParam("id"));
            Estatus estatus = ctx.bodyAsClass(Estatus.class);
            Estatus updated = estatusService.updateEstatus(estatusId, estatus);
            ctx.status(200).json(Map.of("success", true, "message", "Estatus actualizado exitosamente", "data", updated));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void deleteEstatus(Context ctx) {
        try {
            int estatusId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = estatusService.deleteEstatus(estatusId);
            if (deleted) {
                ctx.status(200).json(Map.of("success", true, "message", "Estatus eliminado exitosamente"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void countEstatus(Context ctx) {
        try {
            long count = estatusService.countEstatus();
            ctx.status(200).json(Map.of("success", true, "count", count));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }
}