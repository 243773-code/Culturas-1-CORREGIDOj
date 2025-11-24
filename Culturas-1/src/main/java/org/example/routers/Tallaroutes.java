package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.TallaController;

public class Tallaroutes {

    private final TallaController tallaController;

    public Tallaroutes() {
        this.tallaController = new TallaController();
    }

    public void register(Javalin app) {
        app.get("/api/tallas", tallaController::getAllTallas);
        app.get("/api/tallas/count", tallaController::countTallas);
        app.get("/api/tallas/codigo/{codigo}", tallaController::getTallaByCodigo);
        app.get("/api/tallas/{id}", tallaController::getTallaById);
        app.post("/api/tallas", tallaController::createTalla);
        app.put("/api/tallas/{id}", tallaController::updateTalla);
        app.delete("/api/tallas/{id}", tallaController::deleteTalla);
    }
}