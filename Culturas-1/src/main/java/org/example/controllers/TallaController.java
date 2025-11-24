package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.Talla;
import org.example.services.TallaService;

import java.util.List;
import java.util.Map;

public class TallaController {

    private final TallaService tallaService;
    private final Gson gson;

    public TallaController() {
        this.tallaService = new TallaService();
        this.gson = new Gson();
    }

    public void getAllTallas(Context ctx) {
        try {
            List<Talla> tallas = tallaService.getAllTallas();

            ctx.status(200);
            ctx.json(Map.of(
                    "success", true,
                    "data", tallas,
                    "count", tallas.size()
            ));

        } catch (Exception e) {
            ctx.status(500);
            ctx.json(Map.of(
                    "success", false,
                    "error", "Error al obtener tallas: " + e.getMessage()
            ));
        }
    }

    public void getTallaById(Context ctx) {
        try {
            int tallaId = Integer.parseInt(ctx.pathParam("id"));
            Talla talla = tallaService.getTallaById(tallaId);

            ctx.status(200);
            ctx.json(Map.of(
                    "success", true,
                    "data", talla
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
                    "error", "Error al obtener talla: " + e.getMessage()
            ));
        }
    }

    public void getTallaByCodigo(Context ctx) {
        try {
            String codigo = ctx.pathParam("codigo");
            Talla talla = tallaService.getTallaByCodigo(codigo);

            ctx.status(200);
            ctx.json(Map.of(
                    "success", true,
                    "data", talla
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
                    "error", "Error al obtener talla: " + e.getMessage()
            ));
        }
    }

    public void createTalla(Context ctx) {
        try {
            Talla talla = ctx.bodyAsClass(Talla.class);
            Talla createdTalla = tallaService.createTalla(talla);

            ctx.status(201);
            ctx.json(Map.of(
                    "success", true,
                    "message", "Talla creada exitosamente",
                    "data", createdTalla
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
                    "error", "Error al crear talla: " + e.getMessage()
            ));
        }
    }

    public void updateTalla(Context ctx) {
        try {
            int tallaId = Integer.parseInt(ctx.pathParam("id"));
            Talla talla = ctx.bodyAsClass(Talla.class);

            Talla updatedTalla = tallaService.updateTalla(tallaId, talla);

            ctx.status(200);
            ctx.json(Map.of(
                    "success", true,
                    "message", "Talla actualizada exitosamente",
                    "data", updatedTalla
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
                    "error", "Error al actualizar talla: " + e.getMessage()
            ));
        }
    }

    public void deleteTalla(Context ctx) {
        try {
            int tallaId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = tallaService.deleteTalla(tallaId);

            if (deleted) {
                ctx.status(200);
                ctx.json(Map.of(
                        "success", true,
                        "message", "Talla eliminada exitosamente"
                ));
            } else {
                ctx.status(404);
                ctx.json(Map.of(
                        "success", false,
                        "error", "No se pudo eliminar la talla"
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
                    "error", "Error al eliminar talla: " + e.getMessage()
            ));
        }
    }

    public void countTallas(Context ctx) {
        try {
            long count = tallaService.countTallas();

            ctx.status(200);
            ctx.json(Map.of(
                    "success", true,
                    "count", count
            ));

        } catch (Exception e) {
            ctx.status(500);
            ctx.json(Map.of(
                    "success", false,
                    "error", "Error al contar tallas: " + e.getMessage()
            ));
        }
    }
}