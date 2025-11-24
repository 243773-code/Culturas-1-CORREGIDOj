package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.PagoController;

public class PagoRoutes {

    private final PagoController pagoController;

    public PagoRoutes() {
        this.pagoController = new PagoController();
    }

    public void register(Javalin app) {
        app.get("/api/pagos", pagoController::getAllPagos);
        app.get("/api/pagos/count", pagoController::countPagos);
        app.get("/api/pagos/pedido/{pedidoId}", pagoController::getPagosByPedidoId);
        app.get("/api/pagos/{id}", pagoController::getPagoById);
        app.post("/api/pagos", pagoController::createPago);
        app.put("/api/pagos/{id}", pagoController::updatePago);
        app.delete("/api/pagos/{id}", pagoController::deletePago);
    }
}