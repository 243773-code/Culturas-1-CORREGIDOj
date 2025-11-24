package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.DetallePedido;
import org.example.services.DetallePedidoService;

import java.util.List;
import java.util.Map;

public class DetallePedidoController {

    private final DetallePedidoService detallePedidoService;
    private final Gson gson;

    public DetallePedidoController() {
        this.detallePedidoService = new DetallePedidoService();
        this.gson = new Gson();
    }

    public void getAllDetallesPedido(Context ctx) {
        try {
            List<DetallePedido> detalles = detallePedidoService.getAllDetallesPedido();
            ctx.status(200).json(Map.of("success", true, "data", detalles, "count", detalles.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getDetallePedidoById(Context ctx) {
        try {
            int detallePedidoId = Integer.parseInt(ctx.pathParam("id"));
            DetallePedido detalle = detallePedidoService.getDetallePedidoById(detallePedidoId);
            ctx.status(200).json(Map.of("success", true, "data", detalle));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getDetallesByPedidoId(Context ctx) {
        try {
            int pedidoId = Integer.parseInt(ctx.pathParam("pedidoId"));
            List<DetallePedido> detalles = detallePedidoService.getDetallesByPedidoId(pedidoId);
            ctx.status(200).json(Map.of("success", true, "data", detalles, "count", detalles.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void createDetallePedido(Context ctx) {
        try {
            DetallePedido detalle = ctx.bodyAsClass(DetallePedido.class);
            DetallePedido created = detallePedidoService.createDetallePedido(detalle);
            ctx.status(201).json(Map.of("success", true, "message", "Detalle creado exitosamente", "data", created));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void updateDetallePedido(Context ctx) {
        try {
            int detallePedidoId = Integer.parseInt(ctx.pathParam("id"));
            DetallePedido detalle = ctx.bodyAsClass(DetallePedido.class);
            DetallePedido updated = detallePedidoService.updateDetallePedido(detallePedidoId, detalle);
            ctx.status(200).json(Map.of("success", true, "message", "Detalle actualizado exitosamente", "data", updated));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void deleteDetallePedido(Context ctx) {
        try {
            int detallePedidoId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = detallePedidoService.deleteDetallePedido(detallePedidoId);
            if (deleted) {
                ctx.status(200).json(Map.of("success", true, "message", "Detalle eliminado exitosamente"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void countDetallesPedido(Context ctx) {
        try {
            long count = detallePedidoService.countDetallesPedido();
            ctx.status(200).json(Map.of("success", true, "count", count));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }
}