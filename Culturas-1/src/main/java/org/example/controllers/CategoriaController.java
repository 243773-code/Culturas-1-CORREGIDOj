package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.Categoria;
import org.example.services.CategoriaService;

import java.util.List;
import java.util.Map;

/**
 * CategoriaController - Controlador REST para CATEGORIA
 */
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final Gson gson;

    public CategoriaController() {
        this.categoriaService = new CategoriaService();
        this.gson = new Gson();
    }

    public void getAllCategorias(Context ctx) {
        try {
            List<Categoria> categorias = categoriaService.getAllCategorias();

            ctx.status(200);
            ctx.json(Map.of(
                    "success", true,
                    "data", categorias,
                    "count", categorias.size()
            ));

        } catch (Exception e) {
            ctx.status(500);
            ctx.json(Map.of(
                    "success", false,
                    "error", "Error al obtener categorías: " + e.getMessage()
            ));
        }
    }

    public void getCategoriaById(Context ctx) {
        try {
            int categoriaId = Integer.parseInt(ctx.pathParam("id"));
            Categoria categoria = categoriaService.getCategoriaById(categoriaId);

            ctx.status(200);
            ctx.json(Map.of(
                    "success", true,
                    "data", categoria
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
                    "error", "Error al obtener categoría: " + e.getMessage()
            ));
        }
    }

    public void getCategoriaByNombre(Context ctx) {
        try {
            String nombre = ctx.pathParam("nombre");
            Categoria categoria = categoriaService.getCategoriaByNombre(nombre);

            ctx.status(200);
            ctx.json(Map.of(
                    "success", true,
                    "data", categoria
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
                    "error", "Error al obtener categoría: " + e.getMessage()
            ));
        }
    }

    public void createCategoria(Context ctx) {
        try {
            Categoria categoria = ctx.bodyAsClass(Categoria.class);
            Categoria createdCategoria = categoriaService.createCategoria(categoria);

            ctx.status(201);
            ctx.json(Map.of(
                    "success", true,
                    "message", "Categoría creada exitosamente",
                    "data", createdCategoria
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
                    "error", "Error al crear categoría: " + e.getMessage()
            ));
        }
    }

    public void updateCategoria(Context ctx) {
        try {
            int categoriaId = Integer.parseInt(ctx.pathParam("id"));
            Categoria categoria = ctx.bodyAsClass(Categoria.class);

            Categoria updatedCategoria = categoriaService.updateCategoria(categoriaId, categoria);

            ctx.status(200);
            ctx.json(Map.of(
                    "success", true,
                    "message", "Categoría actualizada exitosamente",
                    "data", updatedCategoria
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
                    "error", "Error al actualizar categoría: " + e.getMessage()
            ));
        }
    }

    public void deleteCategoria(Context ctx) {
        try {
            int categoriaId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = categoriaService.deleteCategoria(categoriaId);

            if (deleted) {
                ctx.status(200);
                ctx.json(Map.of(
                        "success", true,
                        "message", "Categoría eliminada exitosamente"
                ));
            } else {
                ctx.status(404);
                ctx.json(Map.of(
                        "success", false,
                        "error", "No se pudo eliminar la categoría"
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
                    "error", "Error al eliminar categoría: " + e.getMessage()
            ));
        }
    }

    public void countCategorias(Context ctx) {
        try {
            long count = categoriaService.countCategorias();

            ctx.status(200);
            ctx.json(Map.of(
                    "success", true,
                    "count", count
            ));

        } catch (Exception e) {
            ctx.status(500);
            ctx.json(Map.of(
                    "success", false,
                    "error", "Error al contar categorías: " + e.getMessage()
            ));
        }
    }
}