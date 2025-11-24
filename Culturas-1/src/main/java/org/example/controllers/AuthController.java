package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.Usuario;
import org.example.services.AuthService;

import java.util.Map;

/**
 * Controller de Autenticación
 * Maneja endpoints de login y registro
 */
public class AuthController {

    private final AuthService authService;
    private final Gson gson;

    public AuthController() {
        this.authService = new AuthService();
        this.gson = new Gson();
    }

    /**
     * POST /api/auth/login
     * Login de usuario
     *
     * Body JSON:
     * {
     *   "email": "usuario@ejemplo.com",
     *   "password": "contraseña123"
     * }
     */
    public void login(Context ctx) {
        try {
            // Obtener credenciales del body
            Map<String, String> credentials = ctx.bodyAsClass(Map.class);
            String email = credentials.get("email");
            String password = credentials.get("password");

            // Intentar login
            Usuario usuario = authService.login(email, password);

            // Respuesta exitosa
            ctx.status(200).json(Map.of(
                    "success", true,
                    "message", "Login exitoso",
                    "data", usuario
            ));

        } catch (IllegalArgumentException e) {
            // Error de validación
            ctx.status(400).json(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        } catch (RuntimeException e) {
            // Credenciales inválidas
            ctx.status(401).json(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        } catch (Exception e) {
            // Error del servidor
            ctx.status(500).json(Map.of(
                    "success", false,
                    "error", "Error en el servidor: " + e.getMessage()
            ));
        }
    }

    /**
     * POST /api/auth/register
     * Registro de nuevo usuario
     *
     * Body JSON:
     * {
     *   "nombre": "Juan",
     *   "apellido": "Pérez",
     *   "email": "juan@ejemplo.com",
     *   "password": "contraseña123",
     *   "telefono": "9611234567"
     * }
     */
    public void register(Context ctx) {
        try {
            // Obtener datos del usuario del body
            Usuario usuario = ctx.bodyAsClass(Usuario.class);

            // Registrar usuario
            Usuario nuevoUsuario = authService.register(usuario);

            // Respuesta exitosa
            ctx.status(201).json(Map.of(
                    "success", true,
                    "message", "Usuario registrado exitosamente",
                    "data", nuevoUsuario
            ));

        } catch (IllegalArgumentException e) {
            // Error de validación
            ctx.status(400).json(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        } catch (RuntimeException e) {
            // Email ya registrado u otro error
            ctx.status(409).json(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        } catch (Exception e) {
            // Error del servidor
            ctx.status(500).json(Map.of(
                    "success", false,
                    "error", "Error en el servidor: " + e.getMessage()
            ));
        }
    }

    /**
     * GET /api/auth/check-email/{email}
     * Verificar si un email ya está registrado
     */
    public void checkEmail(Context ctx) {
        try {
            String email = ctx.pathParam("email");
            boolean exists = authService.existsByEmail(email);

            ctx.status(200).json(Map.of(
                    "success", true,
                    "exists", exists
            ));

        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "success", false,
                    "error", "Error: " + e.getMessage()
            ));
        }
    }

    /**
     * GET /api/auth/me
     * Obtener datos del usuario actual (simulado - sin JWT aún)
     * En producción esto validaría el token JWT
     */
    public void me(Context ctx) {
        try {
            // Por ahora, esto es solo un ejemplo
            // En producción, aquí validarías el token JWT del header

            ctx.status(200).json(Map.of(
                    "success", true,
                    "message", "Endpoint para obtener usuario autenticado. Requiere implementar JWT."
            ));

        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "success", false,
                    "error", "Error: " + e.getMessage()
            ));
        }
    }
}