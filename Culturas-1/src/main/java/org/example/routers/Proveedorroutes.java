package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.ProveedorController;

public class Proveedorroutes {

    private final ProveedorController proveedorController;

    public Proveedorroutes() {
        this.proveedorController = new ProveedorController();
    }

    public void register(Javalin app) {
        app.get("/api/proveedores", proveedorController::getAllProveedores);
        app.get("/api/proveedores/count", proveedorController::countProveedores);
        app.get("/api/proveedores/nombre/{nombre}", proveedorController::getProveedorByNombre);
        app.get("/api/proveedores/{id}", proveedorController::getProveedorById);
        app.post("/api/proveedores", proveedorController::createProveedor);
        app.put("/api/proveedores/{id}", proveedorController::updateProveedor);
        app.delete("/api/proveedores/{id}", proveedorController::deleteProveedor);
    }
}