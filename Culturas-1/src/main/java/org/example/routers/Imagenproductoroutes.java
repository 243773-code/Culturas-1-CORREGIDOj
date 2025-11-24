package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.ImagenProductoController;

public class Imagenproductoroutes {

    private final ImagenProductoController imagenProductoController;

    public Imagenproductoroutes() {
        this.imagenProductoController = new ImagenProductoController();
    }

    public void register(Javalin app) {
        app.get("/api/imagenes", imagenProductoController::getAllImagenes);
        app.get("/api/imagenes/count", imagenProductoController::countImagenes);
        app.get("/api/imagenes/producto/{productoId}", imagenProductoController::getImagenesByProductoId);
        app.get("/api/imagenes/{id}", imagenProductoController::getImagenById);
        app.post("/api/imagenes", imagenProductoController::createImagen);
        app.put("/api/imagenes/{id}", imagenProductoController::updateImagen);
        app.delete("/api/imagenes/{id}", imagenProductoController::deleteImagen);
    }
}
