package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.HistorialPrecioController;

public class HistorialPrecioRoutes {

    private final HistorialPrecioController historialPrecioController;

    public HistorialPrecioRoutes() {
        this.historialPrecioController = new HistorialPrecioController();
    }

    public void register(Javalin app) {
        app.get("/api/historial-precios", historialPrecioController::getAllHistorialPrecios);
        app.get("/api/historial-precios/count", historialPrecioController::countHistorialPrecios);
        app.get("/api/historial-precios/producto/{productoId}", historialPrecioController::getHistorialPreciosByProductoId);
        app.get("/api/historial-precios/{id}", historialPrecioController::getHistorialPrecioById);
        app.post("/api/historial-precios", historialPrecioController::createHistorialPrecio);
        app.put("/api/historial-precios/{id}", historialPrecioController::updateHistorialPrecio);
        app.delete("/api/historial-precios/{id}", historialPrecioController::deleteHistorialPrecio);
    }
}