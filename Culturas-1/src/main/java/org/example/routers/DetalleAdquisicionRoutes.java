package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.DetalleAdquisicionController;

public class DetalleAdquisicionRoutes {

    private final DetalleAdquisicionController controller;

    public DetalleAdquisicionRoutes() {
        this.controller = new DetalleAdquisicionController();
    }

    public void register(Javalin app) {
        app.get("/api/detalle-adquisiciones", controller::getAllDetallesAdquisicion);
        app.get("/api/detalle-adquisiciones/count", controller::countDetallesAdquisicion);
        app.get("/api/detalle-adquisiciones/adquisicion/{adquisicionId}", controller::getDetallesByAdquisicionId);
        app.get("/api/detalle-adquisiciones/{id}", controller::getDetalleAdquisicionById);
        app.post("/api/detalle-adquisiciones", controller::createDetalleAdquisicion);
        app.put("/api/detalle-adquisiciones/{id}", controller::updateDetalleAdquisicion);
        app.delete("/api/detalle-adquisiciones/{id}", controller::deleteDetalleAdquisicion);
    }
}