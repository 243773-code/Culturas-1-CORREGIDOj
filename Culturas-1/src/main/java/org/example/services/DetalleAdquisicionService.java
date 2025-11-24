package org.example.services;

import org.example.models.DetalleAdquisicion;
import org.example.repositories.DetalleAdquisicionRepository;

import java.math.BigDecimal;
import java.util.List;

public class DetalleAdquisicionService {

    private final DetalleAdquisicionRepository detalleAdquisicionRepository;

    public DetalleAdquisicionService() {
        this.detalleAdquisicionRepository = new DetalleAdquisicionRepository();
    }

    public List<DetalleAdquisicion> getAllDetallesAdquisicion() {
        return detalleAdquisicionRepository.findAll();
    }

    public DetalleAdquisicion getDetalleAdquisicionById(int detalleId) {
        if (detalleId <= 0) {
            throw new IllegalArgumentException("El ID del detalle debe ser mayor a 0");
        }

        DetalleAdquisicion detalle = detalleAdquisicionRepository.findById(detalleId);

        if (detalle == null) {
            throw new RuntimeException("Detalle de adquisición no encontrado con ID: " + detalleId);
        }

        return detalle;
    }

    public List<DetalleAdquisicion> getDetallesByAdquisicionId(int adquisicionId) {
        if (adquisicionId <= 0) {
            throw new IllegalArgumentException("El ID de la adquisición debe ser mayor a 0");
        }

        return detalleAdquisicionRepository.findByAdquisicionId(adquisicionId);
    }

    public DetalleAdquisicion createDetalleAdquisicion(DetalleAdquisicion detalle) {
        validateDetalle(detalle);
        // Calcular subtotal automáticamente
        BigDecimal subtotal = detalle.getPrecioUnitario().multiply(new BigDecimal(detalle.getCantidad()));
        detalle.setSubtotal(subtotal);
        return detalleAdquisicionRepository.save(detalle);
    }

    public DetalleAdquisicion updateDetalleAdquisicion(int detalleId, DetalleAdquisicion detalle) {
        DetalleAdquisicion existingDetalle = detalleAdquisicionRepository.findById(detalleId);
        if (existingDetalle == null) {
            throw new RuntimeException("Detalle de adquisición no encontrado con ID: " + detalleId);
        }

        validateDetalle(detalle);

        // Recalcular subtotal
        BigDecimal subtotal = detalle.getPrecioUnitario().multiply(new BigDecimal(detalle.getCantidad()));
        detalle.setSubtotal(subtotal);

        detalle.setDetalleId(detalleId);
        return detalleAdquisicionRepository.update(detalle);
    }

    public boolean deleteDetalleAdquisicion(int detalleId) {
        if (detalleId <= 0) {
            throw new IllegalArgumentException("El ID del detalle debe ser mayor a 0");
        }

        DetalleAdquisicion detalle = detalleAdquisicionRepository.findById(detalleId);
        if (detalle == null) {
            throw new RuntimeException("Detalle de adquisición no encontrado con ID: " + detalleId);
        }

        return detalleAdquisicionRepository.deleteById(detalleId);
    }

    public long countDetallesAdquisicion() {
        return detalleAdquisicionRepository.count();
    }

    private void validateDetalle(DetalleAdquisicion detalle) {
        if (detalle == null) {
            throw new IllegalArgumentException("El detalle no puede ser nulo");
        }

        if (detalle.getAdquisicionId() <= 0) {
            throw new IllegalArgumentException("La adquisición es obligatoria");
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