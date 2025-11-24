package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.Pedido;
import org.example.services.PedidoService;

import java.util.List;
import java.util.Map;

public class PedidoController {

    private final PedidoService pedidoService;
    private final Gson gson;

    public PedidoController() {
        this.pedidoService = new PedidoService();
        this.gson = new Gson();
    }

    public void getAllPedidos(Context ctx) {
        try {
            List<Pedido> pedidos = pedidoService.getAllPedidos();
            ctx.status(200).json(Map.of("success", true, "data", pedidos, "count", pedidos.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getPedidoById(Context ctx) {
        try {
            int pedidoId = Integer.parseInt(ctx.pathParam("id"));
            Pedido pedido = pedidoService.getPedidoById(pedidoId);
            ctx.status(200).json(Map.of("success", true, "data", pedido));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getPedidosByUsuarioId(Context ctx) {
        try {
            int usuarioId = Integer.parseInt(ctx.pathParam("usuarioId"));
            List<Pedido> pedidos = pedidoService.getPedidosByUsuarioId(usuarioId);
            ctx.status(200).json(Map.of("success", true, "data", pedidos, "count", pedidos.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getPedidosByEstatusId(Context ctx) {
        try {
            int estatusId = Integer.parseInt(ctx.pathParam("estatusId"));
            List<Pedido> pedidos = pedidoService.getPedidosByEstatusId(estatusId);
            ctx.status(200).json(Map.of("success", true, "data", pedidos, "count", pedidos.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void createPedido(Context ctx) {
        try {
            Pedido pedido = ctx.bodyAsClass(Pedido.class);
            Pedido created = pedidoService.createPedido(pedido);
            ctx.status(201).json(Map.of("success", true, "message", "Pedido creado exitosamente", "data", created));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void updatePedido(Context ctx) {
        try {
            int pedidoId = Integer.parseInt(ctx.pathParam("id"));
            Pedido pedido = ctx.bodyAsClass(Pedido.class);
            Pedido updated = pedidoService.updatePedido(pedidoId, pedido);
            ctx.status(200).json(Map.of("success", true, "message", "Pedido actualizado exitosamente", "data", updated));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void deletePedido(Context ctx) {
        try {
            int pedidoId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = pedidoService.deletePedido(pedidoId);
            if (deleted) {
                ctx.status(200).json(Map.of("success", true, "message", "Pedido eliminado exitosamente"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void countPedidos(Context ctx) {
        try {
            long count = pedidoService.countPedidos();
            ctx.status(200).json(Map.of("success", true, "count", count));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }
}