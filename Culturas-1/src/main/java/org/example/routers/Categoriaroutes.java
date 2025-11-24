package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.CategoriaController;

/**
 * CategoriaRoutes - Definición de rutas para CATEGORIA
 */
public class Categoriaroutes {

    private final CategoriaController categoriaController;

    public Categoriaroutes() {
        this.categoriaController = new CategoriaController();
    }

    /**
     * Endpoints:
     * GET    /api/categorias          - Obtener todas las categorías
     * GET    /api/categorias/:id      - Obtener categoría por ID
     * GET    /api/categorias/nombre/:nombre - Obtener categoría por nombre
     * POST   /api/categorias          - Crear nueva categoría
     * PUT    /api/categorias/:id      - Actualizar categoría
     * DELETE /api/categorias/:id      - Eliminar categoría
     * GET    /api/categorias/count    - Contar categorías
     */
    public void register(Javalin app) {
        app.get("/api/categorias", categoriaController::getAllCategorias);
        app.get("/api/categorias/count", categoriaController::countCategorias);
        app.get("/api/categorias/nombre/{nombre}", categoriaController::getCategoriaByNombre);
        app.get("/api/categorias/{id}", categoriaController::getCategoriaById);
        app.post("/api/categorias", categoriaController::createCategoria);
        app.put("/api/categorias/{id}", categoriaController::updateCategoria);
        app.delete("/api/categorias/{id}", categoriaController::deleteCategoria);
    }
}