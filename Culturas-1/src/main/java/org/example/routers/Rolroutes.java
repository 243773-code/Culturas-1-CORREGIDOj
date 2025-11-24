package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.RolController;

/**
 * RolRoutes - Definición de rutas/endpoints para ROL
 * Mapea URLs a métodos del controlador
 */
public class Rolroutes {

    private final RolController rolController;

    public Rolroutes() {
        this.rolController = new RolController();
    }

    /**
     * Registrar todas las rutas de ROL en Javalin
     *
     * Endpoints:
     * GET    /api/roles          - Obtener todos los roles
     * GET    /api/roles/:id      - Obtener rol por ID
     * GET    /api/roles/nombre/:nombre - Obtener rol por nombre
     * POST   /api/roles          - Crear nuevo rol
     * PUT    /api/roles/:id      - Actualizar rol
     * DELETE /api/roles/:id      - Eliminar rol
     * GET    /api/roles/count    - Contar roles
     */
    public void register(Javalin app) {
        // Obtener todos los roles
        app.get("/api/roles", rolController::getAllRoles);

        // Contar roles (debe ir ANTES de /:id para evitar conflictos)
        app.get("/api/roles/count", rolController::countRoles);

        // Obtener rol por nombre (debe ir ANTES de /:id)
        app.get("/api/roles/nombre/{nombre}", rolController::getRolByNombre);

        // Obtener rol por ID
        app.get("/api/roles/{id}", rolController::getRolById);

        // Crear nuevo rol
        app.post("/api/roles", rolController::createRol);

        // Actualizar rol
        app.put("/api/roles/{id}", rolController::updateRol);

        // Eliminar rol
        app.delete("/api/roles/{id}", rolController::deleteRol);
    }
}