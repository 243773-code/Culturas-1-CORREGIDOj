package org.example.services;

import org.example.models.Adquisicion;
import org.example.repositories.AdquisicionRepository;

import java.math.BigDecimal;
import java.util.List;

public class AdquisicionService {

    private final AdquisicionRepository adquisicionRepository;

    public AdquisicionService() {
        this.adquisicionRepository = new AdquisicionRepository();
    }

    public List<Adquisicion> getAllAdquisiciones() {
        return adquisicionRepository.findAll();
    }

    public Adquisicion getAdquisicionById(int adquisicionId) {
        if (adquisicionId <= 0) {
            throw new IllegalArgumentException("El ID de la adquisición debe ser mayor a 0");
        }

        Adquisicion adquisicion = adquisicionRepository.findById(adquisicionId);

        if (adquisicion == null) {
            throw new RuntimeException("Adquisición no encontrada con ID: " + adquisicionId);
        }

        return adquisicion;
    }

    public List<Adquisicion> getAdquisicionesByProveedorId(int proveedorId) {
        if (proveedorId <= 0) {
            throw new IllegalArgumentException("El ID del proveedor debe ser mayor a 0");
        }

        return adquisicionRepository.findByProveedorId(proveedorId);
    }

    public List<Adquisicion> getAdquisicionesByEmpleadoId(int empleadoId) {
        if (empleadoId <= 0) {
            throw new IllegalArgumentException("El ID del empleado debe ser mayor a 0");
        }

        return adquisicionRepository.findByEmpleadoId(empleadoId);
    }

    public Adquisicion createAdquisicion(Adquisicion adquisicion) {
        validateAdquisicion(adquisicion);
        return adquisicionRepository.save(adquisicion);
    }

    public Adquisicion updateAdquisicion(int adquisicionId, Adquisicion adquisicion) {
        Adquisicion existingAdquisicion = adquisicionRepository.findById(adquisicionId);
        if (existingAdquisicion == null) {
            throw new RuntimeException("Adquisición no encontrada con ID: " + adquisicionId);
        }

        validateAdquisicion(adquisicion);

        adquisicion.setAdquisicionId(adquisicionId);
        return adquisicionRepository.update(adquisicion);
    }

    public boolean deleteAdquisicion(int adquisicionId) {
        if (adquisicionId <= 0) {
            throw new IllegalArgumentException("El ID de la adquisición debe ser mayor a 0");
        }

        Adquisicion adquisicion = adquisicionRepository.findById(adquisicionId);
        if (adquisicion == null) {
            throw new RuntimeException("Adquisición no encontrada con ID: " + adquisicionId);
        }

        return adquisicionRepository.deleteById(adquisicionId);
    }

    public long countAdquisiciones() {
        return adquisicionRepository.count();
    }

    private void validateAdquisicion(Adquisicion adquisicion) {
        if (adquisicion == null) {
            throw new IllegalArgumentException("La adquisición no puede ser nula");
        }

        if (adquisicion.getProveedorId() <= 0) {
            throw new IllegalArgumentException("El proveedor es obligatorio");
        }

        if (adquisicion.getEmpleadoId() <= 0) {
            throw new IllegalArgumentException("El empleado es obligatorio");
        }

        if (adquisicion.getTotal() == null || adquisicion.getTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El total debe ser mayor o igual a 0");
        }

        if (adquisicion.getEstatusId() <= 0) {
            throw new IllegalArgumentException("El estatus es obligatorio");
        }
    }
}