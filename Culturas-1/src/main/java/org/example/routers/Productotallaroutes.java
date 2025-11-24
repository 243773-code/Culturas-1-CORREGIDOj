package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.ProductoTallaController;

public class Productotallaroutes {

    private final ProductoTallaController controller;

    public Productotallaroutes() {
        this.controller = new ProductoTallaController();
    }

    public void register(Javalin app) {
        app.get("/api/producto-tallas", controller::getAllProductoTallas);
        app.get("/api/producto-tallas/count", controller::countProductoTallas);
        app.get("/api/producto-tallas/producto/{productoId}", controller::getProductoTallasByProductoId);
        app.get("/api/producto-tallas/{id}", controller::getProductoTallaById);
        app.post("/api/producto-tallas", controller::createProductoTalla);
        app.put("/api/producto-tallas/{id}", controller::updateProductoTalla);
        app.delete("/api/producto-tallas/{id}", controller::deleteProductoTalla);
    }
}