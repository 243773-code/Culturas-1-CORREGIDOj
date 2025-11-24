package org.example.routers;

import io.javalin.Javalin;
import org.example.controllers.AuthController;

/**
 * Rutas de Autenticación
 * Define los endpoints de login, registro, etc.
 */
public class AuthRoutes {

    private final AuthController authController;

    public AuthRoutes() {
        this.authController = new AuthController();
    }

    public void register(Javalin app) {
        // POST /api/auth/login - Login de usuario
        app.post("/api/auth/login", authController::login);

        // POST /api/auth/register - Registro de nuevo usuario
        app.post("/api/auth/register", authController::register);

        // GET /api/auth/check-email/{email} - Verificar si email existe
        app.get("/api/auth/check-email/{email}", authController::checkEmail);

        // GET /api/auth/me - Obtener usuario autenticado (requiere JWT)
        app.get("/api/auth/me", authController::me);
    }
}