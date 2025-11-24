package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.Proveedor;
import org.example.services.ProveedorService;

import java.util.List;
import java.util.Map;

public class ProveedorController {

    private final ProveedorService proveedorService;
    private final Gson gson;

    public ProveedorController() {
        this.proveedorService = new ProveedorService();
        this.gson = new Gson();
    }

    public void getAllProveedores(Context ctx) {
        try {
            List<Proveedor> proveedores = proveedorService.getAllProveedores();
            ctx.status(200).json(Map.of("success", true, "data", proveedores, "count", proveedores.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error al obtener proveedores: " + e.getMessage()));
        }
    }

    public void getProveedorById(Context ctx) {
        try {
            int proveedorId = Integer.parseInt(ctx.pathParam("id"));
            Proveedor proveedor = proveedorService.getProveedorById(proveedorId);
            ctx.status(200).json(Map.of("success", true, "data", proveedor));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getProveedorByNombre(Context ctx) {
        try {
            String nombre = ctx.pathParam("nombre");
            Proveedor proveedor = proveedorService.getProveedorByNombre(nombre);
            ctx.status(200).json(Map.of("success", true, "data", proveedor));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void createProveedor(Context ctx) {
        try {
            Proveedor proveedor = ctx.bodyAsClass(Proveedor.class);
            Proveedor created = proveedorService.createProveedor(proveedor);
            ctx.status(201).json(Map.of("success", true, "message", "Proveedor creado exitosamente", "data", created));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("success", false, "error", e.getMessage()));
        } catch (RuntimeException e) {
            ctx.status(409).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void updateProveedor(Context ctx) {
        try {
            int proveedorId = Integer.parseInt(ctx.pathParam("id"));
            Proveedor proveedor = ctx.bodyAsClass(Proveedor.class);
            Proveedor updated = proveedorService.updateProveedor(proveedorId, proveedor);
            ctx.status(200).json(Map.of("success", true, "message", "Proveedor actualizado exitosamente", "data", updated));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void deleteProveedor(Context ctx) {
        try {
            int proveedorId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = proveedorService.deleteProveedor(proveedorId);
            if (deleted) {
                ctx.status(200).json(Map.of("success", true, "message", "Proveedor eliminado exitosamente"));
            } else {
                ctx.status(404).json(Map.of("success", false, "error", "No se pudo eliminar"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void countProveedores(Context ctx) {
        try {
            long count = proveedorService.countProveedores();
            ctx.status(200).json(Map.of("success", true, "count", count));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }
}