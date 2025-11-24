package org.example.services;

import org.example.models.Direccion;
import org.example.repositories.DireccionRepository;

import java.util.List;

public class DireccionService {

    private final DireccionRepository direccionRepository;

    public DireccionService() {
        this.direccionRepository = new DireccionRepository();
    }

    public List<Direccion> getAllDirecciones() {
        return direccionRepository.findAll();
    }

    public Direccion getDireccionById(int direccionId) {
        if (direccionId <= 0) {
            throw new IllegalArgumentException("El ID de la dirección debe ser mayor a 0");
        }

        Direccion direccion = direccionRepository.findById(direccionId);

        if (direccion == null) {
            throw new RuntimeException("Dirección no encontrada con ID: " + direccionId);
        }

        return direccion;
    }

    public List<Direccion> getDireccionesByUsuarioId(int usuarioId) {
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("El ID del usuario debe ser mayor a 0");
        }

        return direccionRepository.findByUsuarioId(usuarioId);
    }

    public Direccion createDireccion(Direccion direccion) {
        validateDireccion(direccion);
        return direccionRepository.save(direccion);
    }

    public Direccion updateDireccion(int direccionId, Direccion direccion) {
        Direccion existingDireccion = direccionRepository.findById(direccionId);
        if (existingDireccion == null) {
            throw new RuntimeException("Dirección no encontrada con ID: " + direccionId);
        }

        validateDireccion(direccion);

        direccion.setDireccionId(direccionId);
        return direccionRepository.update(direccion);
    }

    public boolean deleteDireccion(int direccionId) {
        if (direccionId <= 0) {
            throw new IllegalArgumentException("El ID de la dirección debe ser mayor a 0");
        }

        Direccion direccion = direccionRepository.findById(direccionId);
        if (direccion == null) {
            throw new RuntimeException("Dirección no encontrada con ID: " + direccionId);
        }

        return direccionRepository.deleteById(direccionId);
    }

    public long countDirecciones() {
        return direccionRepository.count();
    }

    private void validateDireccion(Direccion direccion) {
        if (direccion == null) {
            throw new IllegalArgumentException("La dirección no puede ser nula");
        }

        if (direccion.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("El usuario de la dirección es obligatorio");
        }

        if (direccion.getCalle() == null || direccion.getCalle().trim().isEmpty()) {
            throw new IllegalArgumentException("La calle es obligatoria");
        }

        if (direccion.getCiudad() == null || direccion.getCiudad().trim().isEmpty()) {
            throw new IllegalArgumentException("La ciudad es obligatoria");
        }
    }
}