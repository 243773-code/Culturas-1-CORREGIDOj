package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.PedidoController;

public class PedidoRoutes {

    private final PedidoController pedidoController;

    public PedidoRoutes() {
        this.pedidoController = new PedidoController();
    }

    public void register(Javalin app) {
        app.get("/api/pedidos", pedidoController::getAllPedidos);
        app.get("/api/pedidos/count", pedidoController::countPedidos);
        app.get("/api/pedidos/usuario/{usuarioId}", pedidoController::getPedidosByUsuarioId);
        app.get("/api/pedidos/estatus/{estatusId}", pedidoController::getPedidosByEstatusId);
        app.get("/api/pedidos/{id}", pedidoController::getPedidoById);
        app.post("/api/pedidos", pedidoController::createPedido);
        app.put("/api/pedidos/{id}", pedidoController::updatePedido);
        app.delete("/api/pedidos/{id}", pedidoController::deletePedido);
    }
}