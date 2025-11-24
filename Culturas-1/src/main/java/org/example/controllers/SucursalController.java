package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.Sucursal;
import org.example.services.SucursalService;

import java.util.List;
import java.util.Map;

public class SucursalController {

    private final SucursalService sucursalService;
    private final Gson gson;

    public SucursalController() {
        this.sucursalService = new SucursalService();
        this.gson = new Gson();
    }

    public void getAllSucursales(Context ctx) {
        try {
            List<Sucursal> sucursales = sucursalService.getAllSucursales();
            ctx.status(200).json(Map.of("success", true, "data", sucursales, "count", sucursales.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error al obtener sucursales: " + e.getMessage()));
        }
    }

    public void getSucursalById(Context ctx) {
        try {
            int sucursalId = Integer.parseInt(ctx.pathParam("id"));
            Sucursal sucursal = sucursalService.getSucursalById(sucursalId);
            ctx.status(200).json(Map.of("success", true, "data", sucursal));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error al obtener sucursal: " + e.getMessage()));
        }
    }

    public void getSucursalesByCiudad(Context ctx) {
        try {
            String ciudad = ctx.pathParam("ciudad");
            List<Sucursal> sucursales = sucursalService.getSucursalesByCiudad(ciudad);
            ctx.status(200).json(Map.of("success", true, "data", sucursales, "count", sucursales.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void createSucursal(Context ctx) {
        try {
            Sucursal sucursal = ctx.bodyAsClass(Sucursal.class);
            Sucursal created = sucursalService.createSucursal(sucursal);
            ctx.status(201).json(Map.of("success", true, "message", "Sucursal creada exitosamente", "data", created));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error al crear sucursal: " + e.getMessage()));
        }
    }

    public void updateSucursal(Context ctx) {
        try {
            int sucursalId = Integer.parseInt(ctx.pathParam("id"));
            Sucursal sucursal = ctx.bodyAsClass(Sucursal.class);
            Sucursal updated = sucursalService.updateSucursal(sucursalId, sucursal);
            ctx.status(200).json(Map.of("success", true, "message", "Sucursal actualizada exitosamente", "data", updated));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error al actualizar sucursal: " + e.getMessage()));
        }
    }

    public void deleteSucursal(Context ctx) {
        try {
            int sucursalId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = sucursalService.deleteSucursal(sucursalId);
            if (deleted) {
                ctx.status(200).json(Map.of("success", true, "message", "Sucursal eliminada exitosamente"));
            } else {
                ctx.status(404).json(Map.of("success", false, "error", "No se pudo eliminar la sucursal"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error al eliminar sucursal: " + e.getMessage()));
        }
    }

    public void countSucursales(Context ctx) {
        try {
            long count = sucursalService.countSucursales();
            ctx.status(200).json(Map.of("success", true, "count", count));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error al contar sucursales: " + e.getMessage()));
        }
    }
}