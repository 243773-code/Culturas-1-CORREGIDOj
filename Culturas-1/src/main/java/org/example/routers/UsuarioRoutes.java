package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.UsuarioController;

public class UsuarioRoutes {

    private final UsuarioController usuarioController;

    public UsuarioRoutes() {
        this.usuarioController = new UsuarioController();
    }

    public void register(Javalin app) {
        app.get("/api/usuarios", usuarioController::getAllUsuarios);
        app.get("/api/usuarios/count", usuarioController::countUsuarios);
        app.get("/api/usuarios/email/{email}", usuarioController::getUsuarioByEmail);
        app.get("/api/usuarios/rol/{rolId}", usuarioController::getUsuariosByRolId);
        app.get("/api/usuarios/{id}", usuarioController::getUsuarioById);
        app.post("/api/usuarios", usuarioController::createUsuario);
        app.put("/api/usuarios/{id}", usuarioController::updateUsuario);
        app.delete("/api/usuarios/{id}", usuarioController::deleteUsuario);
    }
}