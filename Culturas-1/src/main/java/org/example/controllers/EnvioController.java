package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.Envio;
import org.example.services.EnvioService;

import java.util.List;
import java.util.Map;

public class EnvioController {

    private final EnvioService envioService;
    private final Gson gson;

    public EnvioController() {
        this.envioService = new EnvioService();
        this.gson = new Gson();
    }

    public void getAllEnvios(Context ctx) {
        try {
            List<Envio> envios = envioService.getAllEnvios();
            ctx.status(200).json(Map.of("success", true, "data", envios, "count", envios.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getEnvioById(Context ctx) {
        try {
            int envioId = Integer.parseInt(ctx.pathParam("id"));
            Envio envio = envioService.getEnvioById(envioId);
            ctx.status(200).json(Map.of("success", true, "data", envio));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getEnvioByPedidoId(Context ctx) {
        try {
            int pedidoId = Integer.parseInt(ctx.pathParam("pedidoId"));
            Envio envio = envioService.getEnvioByPedidoId(pedidoId);
            ctx.status(200).json(Map.of("success", true, "data", envio));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void createEnvio(Context ctx) {
        try {
            Envio envio = ctx.bodyAsClass(Envio.class);
            Envio created = envioService.createEnvio(envio);
            ctx.status(201).json(Map.of("success", true, "message", "Envío creado exitosamente", "data", created));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void updateEnvio(Context ctx) {
        try {
            int envioId = Integer.parseInt(ctx.pathParam("id"));
            Envio envio = ctx.bodyAsClass(Envio.class);
            Envio updated = envioService.updateEnvio(envioId, envio);
            ctx.status(200).json(Map.of("success", true, "message", "Envío actualizado exitosamente", "data", updated));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void deleteEnvio(Context ctx) {
        try {
            int envioId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = envioService.deleteEnvio(envioId);
            if (deleted) {
                ctx.status(200).json(Map.of("success", true, "message", "Envío eliminado exitosamente"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void countEnvios(Context ctx) {
        try {
            long count = envioService.countEnvios();
            ctx.status(200).json(Map.of("success", true, "count", count));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }
}