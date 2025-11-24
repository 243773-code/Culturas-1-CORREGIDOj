package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.AdquisicionController;

public class Adquisicionroutes {

    private final AdquisicionController adquisicionController;

    public Adquisicionroutes() {
        this.adquisicionController = new AdquisicionController();
    }

    public void register(Javalin app) {
        app.get("/api/adquisiciones", adquisicionController::getAllAdquisiciones);
        app.get("/api/adquisiciones/count", adquisicionController::countAdquisiciones);
        app.get("/api/adquisiciones/proveedor/{proveedorId}", adquisicionController::getAdquisicionesByProveedorId);
        app.get("/api/adquisiciones/empleado/{empleadoId}", adquisicionController::getAdquisicionesByEmpleadoId);
        app.get("/api/adquisiciones/{id}", adquisicionController::getAdquisicionById);
        app.post("/api/adquisiciones", adquisicionController::createAdquisicion);
        app.put("/api/adquisiciones/{id}", adquisicionController::updateAdquisicion);
        app.delete("/api/adquisiciones/{id}", adquisicionController::deleteAdquisicion);
    }
}