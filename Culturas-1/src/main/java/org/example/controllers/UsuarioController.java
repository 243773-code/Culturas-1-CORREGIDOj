package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.Usuario;
import org.example.services.UsuarioService;

import java.util.List;
import java.util.Map;

public class UsuarioController {

    private final UsuarioService usuarioService;
    private final Gson gson;

    public UsuarioController() {
        this.usuarioService = new UsuarioService();
        this.gson = new Gson();
    }

    public void getAllUsuarios(Context ctx) {
        try {
            List<Usuario> usuarios = usuarioService.getAllUsuarios();
            // No enviar passwords en la respuesta
            usuarios.forEach(u -> u.setPassword("***"));
            ctx.status(200).json(Map.of("success", true, "data", usuarios, "count", usuarios.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getUsuarioById(Context ctx) {
        try {
            int usuarioId = Integer.parseInt(ctx.pathParam("id"));
            Usuario usuario = usuarioService.getUsuarioById(usuarioId);
            usuario.setPassword("***"); // No enviar password
            ctx.status(200).json(Map.of("success", true, "data", usuario));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getUsuarioByEmail(Context ctx) {
        try {
            String email = ctx.pathParam("email");
            Usuario usuario = usuarioService.getUsuarioByEmail(email);
            usuario.setPassword("***");
            ctx.status(200).json(Map.of("success", true, "data", usuario));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getUsuariosByRolId(Context ctx) {
        try {
            int rolId = Integer.parseInt(ctx.pathParam("rolId"));
            List<Usuario> usuarios = usuarioService.getUsuariosByRolId(rolId);
            usuarios.forEach(u -> u.setPassword("***"));
            ctx.status(200).json(Map.of("success", true, "data", usuarios, "count", usuarios.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void createUsuario(Context ctx) {
        try {
            Usuario usuario = ctx.bodyAsClass(Usuario.class);
            Usuario created = usuarioService.createUsuario(usuario);
            created.setPassword("***");
            ctx.status(201).json(Map.of("success", true, "message", "Usuario creado exitosamente", "data", created));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("success", false, "error", e.getMessage()));
        } catch (RuntimeException e) {
            ctx.status(409).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void updateUsuario(Context ctx) {
        try {
            int usuarioId = Integer.parseInt(ctx.pathParam("id"));
            Usuario usuario = ctx.bodyAsClass(Usuario.class);
            Usuario updated = usuarioService.updateUsuario(usuarioId, usuario);
            updated.setPassword("***");
            ctx.status(200).json(Map.of("success", true, "message", "Usuario actualizado exitosamente", "data", updated));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void deleteUsuario(Context ctx) {
        try {
            int usuarioId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = usuarioService.deleteUsuario(usuarioId);
            if (deleted) {
                ctx.status(200).json(Map.of("success", true, "message", "Usuario eliminado exitosamente"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void countUsuarios(Context ctx) {
        try {
            long count = usuarioService.countUsuarios();
            ctx.status(200).json(Map.of("success", true, "count", count));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }
}