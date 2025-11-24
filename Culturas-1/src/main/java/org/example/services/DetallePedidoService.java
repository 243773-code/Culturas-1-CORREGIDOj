package org.example.services;

import org.example.models.DetallePedido;
import org.example.repositories.DetallePedidoRepository;

import java.math.BigDecimal;
import java.util.List;

public class DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;

    public DetallePedidoService() {
        this.detallePedidoRepository = new DetallePedidoRepository();
    }

    public List<DetallePedido> getAllDetallesPedido() {
        return detallePedidoRepository.findAll();
    }

    public DetallePedido getDetallePedidoById(int detallePedidoId) {
        if (detallePedidoId <= 0) {
            throw new IllegalArgumentException("El ID del detalle debe ser mayor a 0");
        }

        DetallePedido detalle = detallePedidoRepository.findById(detallePedidoId);

        if (detalle == null) {
            throw new RuntimeException("Detalle de pedido no encontrado con ID: " + detallePedidoId);
        }

        return detalle;
    }

    public List<DetallePedido> getDetallesByPedidoId(int pedidoId) {
        if (pedidoId <= 0) {
            throw new IllegalArgumentException("El ID del pedido debe ser mayor a 0");
        }

        return detallePedidoRepository.findByPedidoId(pedidoId);
    }

    public DetallePedido createDetallePedido(DetallePedido detalle) {
        validateDetalle(detalle);
        // Calcular subtotal automáticamente
        BigDecimal subtotal = detalle.getPrecioUnitario().multiply(new BigDecimal(detalle.getCantidad()));
        detalle.setSubtotal(subtotal);
        return detallePedidoRepository.save(detalle);
    }

    public DetallePedido updateDetallePedido(int detallePedidoId, DetallePedido detalle) {
        DetallePedido existingDetalle = detallePedidoRepository.findById(detallePedidoId);
        if (existingDetalle == null) {
            throw new RuntimeException("Detalle de pedido no encontrado con ID: " + detallePedidoId);
        }

        validateDetalle(detalle);

        // Recalcular subtotal
        BigDecimal subtotal = detalle.getPrecioUnitario().multiply(new BigDecimal(detalle.getCantidad()));
        detalle.setSubtotal(subtotal);

        detalle.setDetallePedidoId(detallePedidoId);
        return detallePedidoRepository.update(detalle);
    }

    public boolean deleteDetallePedido(int detallePedidoId) {
        if (detallePedidoId <= 0) {
            throw new IllegalArgumentException("El ID del detalle debe ser mayor a 0");
        }

        DetallePedido detalle = detallePedidoRepository.findById(detallePedidoId);
        if (detalle == null) {
            throw new RuntimeException("Detalle de pedido no encontrado con ID: " + detallePedidoId);
        }

        return detallePedidoRepository.deleteById(detallePedidoId);
    }

    public long countDetallesPedido() {
        return detallePedidoRepository.count();
    }

    private void validateDetalle(DetallePedido detalle) {
        if (detalle == null) {
            throw new IllegalArgumentException("El detalle no puede ser nulo");
        }

        if (detalle.getPedidoId() <= 0) {
            throw new IllegalArgumentException("El pedido es obligatorio");
        }

        if (detalle.getProductoTallaId() <= 0) {
            throw new IllegalArgumentException("El producto-talla es obligatorio");
        }

        if (detalle.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        if (detalle.getPrecioUnitario() == null || detalle.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio unitario debe ser mayor a 0");
        }
    }
}