package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.EnvioController;

public class EnvioRoutes {

    private final EnvioController envioController;

    public EnvioRoutes() {
        this.envioController = new EnvioController();
    }

    public void register(Javalin app) {
        app.get("/api/envios", envioController::getAllEnvios);
        app.get("/api/envios/count", envioController::countEnvios);
        app.get("/api/envios/pedido/{pedidoId}", envioController::getEnvioByPedidoId);
        app.get("/api/envios/{id}", envioController::getEnvioById);
        app.post("/api/envios", envioController::createEnvio);
        app.put("/api/envios/{id}", envioController::updateEnvio);
        app.delete("/api/envios/{id}", envioController::deleteEnvio);
    }
}