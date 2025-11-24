package org.example.services;

import org.example.models.Estatus;
import org.example.repositories.EstatusRepository;

import java.util.List;

public class EstatusService {

    private final EstatusRepository estatusRepository;

    public EstatusService() {
        this.estatusRepository = new EstatusRepository();
    }

    public List<Estatus> getAllEstatus() {
        return estatusRepository.findAll();
    }

    public Estatus getEstatusById(int estatusId) {
        if (estatusId <= 0) {
            throw new IllegalArgumentException("El ID del estatus debe ser mayor a 0");
        }

        Estatus estatus = estatusRepository.findById(estatusId);

        if (estatus == null) {
            throw new RuntimeException("Estatus no encontrado con ID: " + estatusId);
        }

        return estatus;
    }

    public Estatus getEstatusByNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del estatus no puede estar vacío");
        }

        Estatus estatus = estatusRepository.findByNombre(nombre);

        if (estatus == null) {
            throw new RuntimeException("Estatus no encontrado con nombre: " + nombre);
        }

        return estatus;
    }

    public List<Estatus> getEstatusByTabla(String tabla) {
        if (tabla == null || tabla.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la tabla no puede estar vacío");
        }

        return estatusRepository.findByTabla(tabla);
    }

    public Estatus createEstatus(Estatus estatus) {
        validateEstatus(estatus);

        if (estatusRepository.existsByNombre(estatus.getNombre())) {
            throw new RuntimeException("Ya existe un estatus con el nombre: " + estatus.getNombre());
        }

        return estatusRepository.save(estatus);
    }

    public Estatus updateEstatus(int estatusId, Estatus estatus) {
        Estatus existingEstatus = estatusRepository.findById(estatusId);
        if (existingEstatus == null) {
            throw new RuntimeException("Estatus no encontrado con ID: " + estatusId);
        }

        validateEstatus(estatus);

        Estatus estatusWithSameName = estatusRepository.findByNombre(estatus.getNombre());
        if (estatusWithSameName != null && estatusWithSameName.getEstatusId() != estatusId) {
            throw new RuntimeException("Ya existe otro estatus con el nombre: " + estatus.getNombre());
        }

        estatus.setEstatusId(estatusId);
        return estatusRepository.update(estatus);
    }

    public boolean deleteEstatus(int estatusId) {
        if (estatusId <= 0) {
            throw new IllegalArgumentException("El ID del estatus debe ser mayor a 0");
        }

        Estatus estatus = estatusRepository.findById(estatusId);
        if (estatus == null) {
            throw new RuntimeException("Estatus no encontrado con ID: " + estatusId);
        }

        return estatusRepository.deleteById(estatusId);
    }

    public long countEstatus() {
        return estatusRepository.count();
    }

    private void validateEstatus(Estatus estatus) {
        if (estatus == null) {
            throw new IllegalArgumentException("El estatus no puede ser nulo");
        }

        if (estatus.getNombre() == null || estatus.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del estatus es obligatorio");
        }

        if (estatus.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre del estatus no puede exceder 50 caracteres");
        }
    }
}