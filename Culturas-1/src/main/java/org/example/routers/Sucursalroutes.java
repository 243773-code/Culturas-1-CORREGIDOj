package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.SucursalController;

public class Sucursalroutes {

    private final SucursalController sucursalController;

    public Sucursalroutes() {
        this.sucursalController = new SucursalController();
    }

    public void register(Javalin app) {
        app.get("/api/sucursales", sucursalController::getAllSucursales);
        app.get("/api/sucursales/count", sucursalController::countSucursales);
        app.get("/api/sucursales/ciudad/{ciudad}", sucursalController::getSucursalesByCiudad);
        app.get("/api/sucursales/{id}", sucursalController::getSucursalById);
        app.post("/api/sucursales", sucursalController::createSucursal);
        app.put("/api/sucursales/{id}", sucursalController::updateSucursal);
        app.delete("/api/sucursales/{id}", sucursalController::deleteSucursal);
    }
}