package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.EstatusController;

public class Estatusroutes {

    private final EstatusController estatusController;

    public Estatusroutes() {
        this.estatusController = new EstatusController();
    }

    public void register(Javalin app) {
        app.get("/api/estatus", estatusController::getAllEstatus);
        app.get("/api/estatus/count", estatusController::countEstatus);
        app.get("/api/estatus/nombre/{nombre}", estatusController::getEstatusByNombre);
        app.get("/api/estatus/tabla/{tabla}", estatusController::getEstatusByTabla);
        app.get("/api/estatus/{id}", estatusController::getEstatusById);
        app.post("/api/estatus", estatusController::createEstatus);
        app.put("/api/estatus/{id}", estatusController::updateEstatus);
        app.delete("/api/estatus/{id}", estatusController::deleteEstatus);
    }
}