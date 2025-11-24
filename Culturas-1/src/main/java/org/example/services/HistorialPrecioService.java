package org.example.services;

import org.example.models.HistorialPrecio;
import org.example.repositories.HistorialPrecioRepository;

import java.math.BigDecimal;
import java.util.List;

public class HistorialPrecioService {

    private final HistorialPrecioRepository historialPrecioRepository;

    public HistorialPrecioService() {
        this.historialPrecioRepository = new HistorialPrecioRepository();
    }

    public List<HistorialPrecio> getAllHistorialPrecios() {
        return historialPrecioRepository.findAll();
    }

    public HistorialPrecio getHistorialPrecioById(int historialId) {
        if (historialId <= 0) {
            throw new IllegalArgumentException("El ID del historial debe ser mayor a 0");
        }

        HistorialPrecio historial = historialPrecioRepository.findById(historialId);

        if (historial == null) {
            throw new RuntimeException("Historial de precio no encontrado con ID: " + historialId);
        }

        return historial;
    }

    public List<HistorialPrecio> getHistorialPreciosByProductoId(int productoId) {
        if (productoId <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser mayor a 0");
        }

        return historialPrecioRepository.findByProductoId(productoId);
    }

    public HistorialPrecio createHistorialPrecio(HistorialPrecio historialPrecio) {
        validateHistorialPrecio(historialPrecio);
        return historialPrecioRepository.save(historialPrecio);
    }

    public HistorialPrecio updateHistorialPrecio(int historialId, HistorialPrecio historialPrecio) {
        HistorialPrecio existing = historialPrecioRepository.findById(historialId);
        if (existing == null) {
            throw new RuntimeException("Historial de precio no encontrado con ID: " + historialId);
        }

        validateHistorialPrecio(historialPrecio);

        historialPrecio.setHistorialId(historialId);
        return historialPrecioRepository.update(historialPrecio);
    }

    public boolean deleteHistorialPrecio(int historialId) {
        if (historialId <= 0) {
            throw new IllegalArgumentException("El ID del historial debe ser mayor a 0");
        }

        HistorialPrecio historial = historialPrecioRepository.findById(historialId);
        if (historial == null) {
            throw new RuntimeException("Historial de precio no encontrado con ID: " + historialId);
        }

        return historialPrecioRepository.deleteById(historialId);
    }

    public long countHistorialPrecios() {
        return historialPrecioRepository.count();
    }

    private void validateHistorialPrecio(HistorialPrecio historialPrecio) {
        if (historialPrecio == null) {
            throw new IllegalArgumentException("El historial de precio no puede ser nulo");
        }

        if (historialPrecio.getProductoId() <= 0) {
            throw new IllegalArgumentException("El producto es obligatorio");
        }

        if (historialPrecio.getPrecioAnterior() == null || historialPrecio.getPrecioAnterior().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio anterior debe ser mayor o igual a 0");
        }

        if (historialPrecio.getPrecioNuevo() == null || historialPrecio.getPrecioNuevo().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio nuevo debe ser mayor o igual a 0");
        }
    }
}