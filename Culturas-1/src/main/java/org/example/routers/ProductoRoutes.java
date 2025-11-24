package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.ProductoController;

public class ProductoRoutes {

    private final ProductoController productoController;

    public ProductoRoutes() {
        this.productoController = new ProductoController();
    }

    public void register(Javalin app) {
        app.get("/api/productos", productoController::getAllProductos);
        app.get("/api/productos/count", productoController::countProductos);
        app.get("/api/productos/sku/{sku}", productoController::getProductoBySku);
        app.get("/api/productos/categoria/{categoriaId}", productoController::getProductosByCategoriaId);
        app.get("/api/productos/{id}", productoController::getProductoById);
        app.post("/api/productos", productoController::createProducto);
        app.put("/api/productos/{id}", productoController::updateProducto);
        app.delete("/api/productos/{id}", productoController::deleteProducto);
    }
}