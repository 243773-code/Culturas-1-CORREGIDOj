package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.DireccionController;

public class Direccionroutes {

    private final DireccionController direccionController;

    public Direccionroutes() {
        this.direccionController = new DireccionController();
    }

    public void register(Javalin app) {
        app.get("/api/direcciones", direccionController::getAllDirecciones);
        app.get("/api/direcciones/count", direccionController::countDirecciones);
        app.get("/api/direcciones/usuario/{usuarioId}", direccionController::getDireccionesByUsuarioId);
        app.get("/api/direcciones/{id}", direccionController::getDireccionById);
        app.post("/api/direcciones", direccionController::createDireccion);
        app.put("/api/direcciones/{id}", direccionController::updateDireccion);
        app.delete("/api/direcciones/{id}", direccionController::deleteDireccion);
    }
}