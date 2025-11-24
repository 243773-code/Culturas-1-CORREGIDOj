package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.EmpleadoController;

public class Empleadoroutes {

    private final EmpleadoController empleadoController;

    public Empleadoroutes() {
        this.empleadoController = new EmpleadoController();
    }

    public void register(Javalin app) {
        app.get("/api/empleados", empleadoController::getAllEmpleados);
        app.get("/api/empleados/count", empleadoController::countEmpleados);
        app.get("/api/empleados/usuario/{usuarioId}", empleadoController::getEmpleadoByUsuarioId);
        app.get("/api/empleados/sucursal/{sucursalId}", empleadoController::getEmpleadosBySucursalId);
        app.get("/api/empleados/{id}", empleadoController::getEmpleadoById);
        app.post("/api/empleados", empleadoController::createEmpleado);
        app.put("/api/empleados/{id}", empleadoController::updateEmpleado);
        app.delete("/api/empleados/{id}", empleadoController::deleteEmpleado);
    }
}