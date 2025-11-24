package org.example.controllers;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.example.models.Pago;
import org.example.services.PagoService;

import java.util.List;
import java.util.Map;

public class PagoController {

    private final PagoService pagoService;
    private final Gson gson;

    public PagoController() {
        this.pagoService = new PagoService();
        this.gson = new Gson();
    }

    public void getAllPagos(Context ctx) {
        try {
            List<Pago> pagos = pagoService.getAllPagos();
            ctx.status(200).json(Map.of("success", true, "data", pagos, "count", pagos.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getPagoById(Context ctx) {
        try {
            int pagoId = Integer.parseInt(ctx.pathParam("id"));
            Pago pago = pagoService.getPagoById(pagoId);
            ctx.status(200).json(Map.of("success", true, "data", pago));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("success", false, "error", "ID inválido"));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void getPagosByPedidoId(Context ctx) {
        try {
            int pedidoId = Integer.parseInt(ctx.pathParam("pedidoId"));
            List<Pago> pagos = pagoService.getPagosByPedidoId(pedidoId);
            ctx.status(200).json(Map.of("success", true, "data", pagos, "count", pagos.size()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void createPago(Context ctx) {
        try {
            Pago pago = ctx.bodyAsClass(Pago.class);
            Pago created = pagoService.createPago(pago);
            ctx.status(201).json(Map.of("success", true, "message", "Pago creado exitosamente", "data", created));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void updatePago(Context ctx) {
        try {
            int pagoId = Integer.parseInt(ctx.pathParam("id"));
            Pago pago = ctx.bodyAsClass(Pago.class);
            Pago updated = pagoService.updatePago(pagoId, pago);
            ctx.status(200).json(Map.of("success", true, "message", "Pago actualizado exitosamente", "data", updated));
        } catch (RuntimeException e) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void deletePago(Context ctx) {
        try {
            int pagoId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = pagoService.deletePago(pagoId);
            if (deleted) {
                ctx.status(200).json(Map.of("success", true, "message", "Pago eliminado exitosamente"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }

    public void countPagos(Context ctx) {
        try {
            long count = pagoService.countPagos();
            ctx.status(200).json(Map.of("success", true, "count", count));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("success", false, "error", "Error: " + e.getMessage()));
        }
    }
}