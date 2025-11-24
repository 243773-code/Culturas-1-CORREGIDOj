package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.Empleado;
import org.example.services.EmpleadoService;

import java.util.List;
import java.util.Map;

public class EmpleadoController {

    private final EmpleadoService empleadoService;
    private final Gson gson;

    public EmpleadoController() {
        this.empleadoService = new EmpleadoService();
        this.gson = new Gson();
    }

    public void getAllEmpleados(Context ctx) {
        try {
            List<Empleado> empleados = empleadoService.getAllEmpleados();
            ctx.status(200).json(Map.of("success", true, "data", empleados, "count", empleados.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getEmpleadoById(Context ctx) {
        try {
            int empleadoId = Integer.parseInt(ctx.pathParam("id"));
            Empleado empleado = empleadoService.getEmpleadoById(empleadoId);
            ctx.status(200).json(Map.of("success", true, "data", empleado));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getEmpleadoByUsuarioId(Context ctx) {
        try {
            int usuarioId = Integer.parseInt(ctx.pathParam("usuarioId"));
            Empleado empleado = empleadoService.getEmpleadoByUsuarioId(usuarioId);
            ctx.status(200).json(Map.of("success", true, "data", empleado));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getEmpleadosBySucursalId(Context ctx) {
        try {
            int sucursalId = Integer.parseInt(ctx.pathParam("sucursalId"));
            List<Empleado> empleados = empleadoService.getEmpleadosBySucursalId(sucursalId);
            ctx.status(200).json(Map.of("success", true, "data", empleados, "count", empleados.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void createEmpleado(Context ctx) {
        try {
            Empleado empleado = ctx.bodyAsClass(Empleado.class);
            Empleado created = empleadoService.createEmpleado(empleado);
            ctx.status(201).json(Map.of("success", true, "message", "Empleado creado exitosamente", "data", created));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void updateEmpleado(Context ctx) {
        try {
            int empleadoId = Integer.parseInt(ctx.pathParam("id"));
            Empleado empleado = ctx.bodyAsClass(Empleado.class);
            Empleado updated = empleadoService.updateEmpleado(empleadoId, empleado);
            ctx.status(200).json(Map.of("success", true, "message", "Empleado actualizado exitosamente", "data", updated));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void deleteEmpleado(Context ctx) {
        try {
            int empleadoId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = empleadoService.deleteEmpleado(empleadoId);
            if (deleted) {
                ctx.status(200).json(Map.of("success", true, "message", "Empleado eliminado exitosamente"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void countEmpleados(Context ctx) {
        try {
            long count = empleadoService.countEmpleados();
            ctx.status(200).json(Map.of("success", true, "count", count));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }
}