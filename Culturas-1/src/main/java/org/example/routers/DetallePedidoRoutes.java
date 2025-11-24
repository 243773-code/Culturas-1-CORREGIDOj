package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.DetallePedidoController;

public class DetallePedidoRoutes {

    private final DetallePedidoController controller;

    public DetallePedidoRoutes() {
        this.controller = new DetallePedidoController();
    }

    public void register(Javalin app) {
        app.get("/api/detalle-pedidos", controller::getAllDetallesPedido);
        app.get("/api/detalle-pedidos/count", controller::countDetallesPedido);
        app.get("/api/detalle-pedidos/pedido/{pedidoId}", controller::getDetallesByPedidoId);
        app.get("/api/detalle-pedidos/{id}", controller::getDetallePedidoById);
        app.post("/api/detalle-pedidos", controller::createDetallePedido);
        app.put("/api/detalle-pedidos/{id}", controller::updateDetallePedido);
        app.delete("/api/detalle-pedidos/{id}", controller::deleteDetallePedido);
    }
}