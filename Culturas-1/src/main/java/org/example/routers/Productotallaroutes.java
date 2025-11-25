package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.ProductoTallaController;

public class Productotallaroutes {

    private final ProductoTallaController controller;

    public Productotallaroutes() {
        this.controller = new ProductoTallaController();
    }

    public void register(Javalin app) {
        app.get("/api/producto-tallas", controller::getAll);
        app.get("/api/producto-tallas/count", controller::count);
        app.get("/api/producto-tallas/{id}", controller::getById);
        app.post("/api/producto-tallas", controller::create);
        app.put("/api/producto-tallas/{id}", controller::update);
        app.patch("/api/producto-tallas/{id}/cantidad", controller::updateCantidad); // 👈 PATCH
        app.delete("/api/producto-tallas/{id}", controller::delete);
    }
}
