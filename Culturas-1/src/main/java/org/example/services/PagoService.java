package org.example.services;

import org.example.models.Pago;
import org.example.repositories.PagoRepository;

import java.math.BigDecimal;
import java.util.List;

public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService() {
        this.pagoRepository = new PagoRepository();
    }

    public List<Pago> getAllPagos() {
        return pagoRepository.findAll();
    }

    public Pago getPagoById(int pagoId) {
        if (pagoId <= 0) {
            throw new IllegalArgumentException("El ID del pago debe ser mayor a 0");
        }

        Pago pago = pagoRepository.findById(pagoId);

        if (pago == null) {
            throw new RuntimeException("Pago no encontrado con ID: " + pagoId);
        }

        return pago;
    }

    public List<Pago> getPagosByPedidoId(int pedidoId) {
        if (pedidoId <= 0) {
            throw new IllegalArgumentException("El ID del pedido debe ser mayor a 0");
        }

        return pagoRepository.findByPedidoId(pedidoId);
    }

    public Pago createPago(Pago pago) {
        validatePago(pago);
        return pagoRepository.save(pago);
    }

    public Pago updatePago(int pagoId, Pago pago) {
        Pago existingPago = pagoRepository.findById(pagoId);
        if (existingPago == null) {
            throw new RuntimeException("Pago no encontrado con ID: " + pagoId);
        }

        validatePago(pago);

        pago.setPagoId(pagoId);
        return pagoRepository.update(pago);
    }

    public boolean deletePago(int pagoId) {
        if (pagoId <= 0) {
            throw new IllegalArgumentException("El ID del pago debe ser mayor a 0");
        }

        Pago pago = pagoRepository.findById(pagoId);
        if (pago == null) {
            throw new RuntimeException("Pago no encontrado con ID: " + pagoId);
        }

        return pagoRepository.deleteById(pagoId);
    }

    public long countPagos() {
        return pagoRepository.count();
    }

    private void validatePago(Pago pago) {
        if (pago == null) {
            throw new IllegalArgumentException("El pago no puede ser nulo");
        }

        if (pago.getPedidoId() <= 0) {
            throw new IllegalArgumentException("El pedido es obligatorio");
        }

        if (pago.getMetodoPago() == null || pago.getMetodoPago().trim().isEmpty()) {
            throw new IllegalArgumentException("El método de pago es obligatorio");
        }

        if (pago.getMonto() == null || pago.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }

        if (pago.getEstatusId() <= 0) {
            throw new IllegalArgumentException("El estatus es obligatorio");
        }
    }
}