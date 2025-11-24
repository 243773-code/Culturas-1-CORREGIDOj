package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.Rol;
import org.example.services.RolService;

import java.util.List;
import java.util.Map;

/**
 * RolController - Controlador REST para ROL
 * Maneja peticiones HTTP y devuelve respuestas JSON
 */
public class RolController {

    private final RolService rolService;
    private final Gson gson;

    public RolController() {
        this.rolService = new RolService();
        this.gson = new Gson();
    }

    /**
     * GET /api/roles - Obtener todos los roles
     */
    public void getAllRoles(Context ctx) {
        try {
            List<Rol> roles = rolService.getAllRoles();

            ctx.status(200);
            ctx.json(Map.of(
                    "success", true,
                    "data", roles,
                    "count", roles.size()
            ));

        } catch (Exception e) {
            ctx.status(500);
            ctx.json(Map.of(
                    "success", false,
                    "error", "Error al obtener roles: " + e.getMessage()
            ));
        }
    }

    /**
     * GET /api/roles/:id - Obtener rol por ID
     */
    public void getRolById(Context ctx) {
        try {
            int rolId = Integer.parseInt(ctx.pathParam("id"));
            Rol rol = rolService.getRolById(rolId);

            ctx.status(200);
            ctx.json(Map.of(
                    "success", true,
                    "data", rol
            ));

        } catch (NumberFormatException e) {
            ctx.status(400);
            ctx.json(Map.of(
                    "success", false,
                    "error", "ID inválido"
            ));

        } catch (RuntimeException e) {
            ctx.status(404);
            ctx.json(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));

        } catch (Exception e) {
            ctx.status(500);
            ctx.json(Map.of(
                    "success", false,
                    "error", "Error al obtener rol: " + e.getMessage()
            ));
        }
    }

    /**
     * GET /api/roles/nombre/:nombre - Obtener rol por nombre
     */
    public void getRolByNombre(Context ctx) {
        try {
            String nombre = ctx.pathParam("nombre");
            Rol rol = rolService.getRolByNombre(nombre);

            ctx.status(200);
            ctx.json(Map.of(
                    "success", true,
                    "data", rol
            ));

        } catch (RuntimeException e) {
            ctx.status(404);
            ctx.json(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));

        } catch (Exception e) {
            ctx.status(500);
            ctx.json(Map.of(
                    "success", false,
                    "error", "Error al obtener rol: " + e.getMessage()
            ));
        }
    }

    /**
     * POST /api/roles - Crear nuevo rol
     */
    public void createRol(Context ctx) {
        try {
            Rol rol = ctx.bodyAsClass(Rol.class);
            Rol createdRol = rolService.createRol(rol);

            ctx.status(201);
            ctx.json(Map.of(
                    "success", true,
                    "message", "Rol creado exitosamente",
                    "data", createdRol
            ));

        } catch (IllegalArgumentException e) {
            ctx.status(400);
            ctx.json(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));

        } catch (RuntimeException e) {
            ctx.status(409);
            ctx.json(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));

        } catch (Exception e) {
            ctx.status(500);
            ctx.json(Map.of(
                    "success", false,
                    "error", "Error al crear rol: " + e.getMessage()
            ));
        }
    }

    /**
     * PUT /api/roles/:id - Actualizar rol existente
     */
    public void updateRol(Context ctx) {
        try {
            int rolId = Integer.parseInt(ctx.pathParam("id"));
            Rol rol = ctx.bodyAsClass(Rol.class);

            Rol updatedRol = rolService.updateRol(rolId, rol);

            ctx.status(200);
            ctx.json(Map.of(
                    "success", true,
                    "message", "Rol actualizado exitosamente",
                    "data", updatedRol
            ));

        } catch (NumberFormatException e) {
            ctx.status(400);
            ctx.json(Map.of(
                    "success", false,
                    "error", "ID inválido"
            ));

        } catch (IllegalArgumentException e) {
            ctx.status(400);
            ctx.json(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));

        } catch (RuntimeException e) {
            ctx.status(404);
            ctx.json(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));

        } catch (Exception e) {
            ctx.status(500);
            ctx.json(Map.of(
                    "success", false,
                    "error", "Error al actualizar rol: " + e.getMessage()
            ));
        }
    }

    /**
     * DELETE /api/roles/:id - Eliminar rol
     */
    public void deleteRol(Context ctx) {
        try {
            int rolId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = rolService.deleteRol(rolId);

            if (deleted) {
                ctx.status(200);
                ctx.json(Map.of(
                        "success", true,
                        "message", "Rol eliminado exitosamente"
                ));
            } else {
                ctx.status(404);
                ctx.json(Map.of(
                        "success", false,
                        "error", "No se pudo eliminar el rol"
                ));
            }

        } catch (NumberFormatException e) {
            ctx.status(400);
            ctx.json(Map.of(
                    "success", false,
                    "error", "ID inválido"
            ));

        } catch (RuntimeException e) {
            ctx.status(404);
            ctx.json(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));

        } catch (Exception e) {
            ctx.status(500);
            ctx.json(Map.of(
                    "success", false,
                    "error", "Error al eliminar rol: " + e.getMessage()
            ));
        }
    }

    /**
     * GET /api/roles/count - Contar roles
     */
    public void countRoles(Context ctx) {
        try {
            long count = rolService.countRoles();

            ctx.status(200);
            ctx.json(Map.of(
                    "success", true,
                    "count", count
            ));

        } catch (Exception e) {
            ctx.status(500);
            ctx.json(Map.of(
                    "success", false,
                    "error", "Error al contar roles: " + e.getMessage()
            ));
        }
    }
}