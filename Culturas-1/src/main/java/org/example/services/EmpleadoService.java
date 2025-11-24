package org.example.services;

import org.example.models.Empleado;
import org.example.repositories.EmpleadoRepository;

import java.util.List;

public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService() {
        this.empleadoRepository = new EmpleadoRepository();
    }

    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    public Empleado getEmpleadoById(int empleadoId) {
        if (empleadoId <= 0) {
            throw new IllegalArgumentException("El ID del empleado debe ser mayor a 0");
        }

        Empleado empleado = empleadoRepository.findById(empleadoId);

        if (empleado == null) {
            throw new RuntimeException("Empleado no encontrado con ID: " + empleadoId);
        }

        return empleado;
    }

    public Empleado getEmpleadoByUsuarioId(int usuarioId) {
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("El ID del usuario debe ser mayor a 0");
        }

        Empleado empleado = empleadoRepository.findByUsuarioId(usuarioId);

        if (empleado == null) {
            throw new RuntimeException("Empleado no encontrado para el usuario ID: " + usuarioId);
        }

        return empleado;
    }

    public List<Empleado> getEmpleadosBySucursalId(int sucursalId) {
        if (sucursalId <= 0) {
            throw new IllegalArgumentException("El ID de la sucursal debe ser mayor a 0");
        }

        return empleadoRepository.findBySucursalId(sucursalId);
    }

    public Empleado createEmpleado(Empleado empleado) {
        validateEmpleado(empleado);
        return empleadoRepository.save(empleado);
    }

    public Empleado updateEmpleado(int empleadoId, Empleado empleado) {
        Empleado existingEmpleado = empleadoRepository.findById(empleadoId);
        if (existingEmpleado == null) {
            throw new RuntimeException("Empleado no encontrado con ID: " + empleadoId);
        }

        validateEmpleado(empleado);

        empleado.setEmpleadoId(empleadoId);
        return empleadoRepository.update(empleado);
    }

    public boolean deleteEmpleado(int empleadoId) {
        if (empleadoId <= 0) {
            throw new IllegalArgumentException("El ID del empleado debe ser mayor a 0");
        }

        Empleado empleado = empleadoRepository.findById(empleadoId);
        if (empleado == null) {
            throw new RuntimeException("Empleado no encontrado con ID: " + empleadoId);
        }

        return empleadoRepository.deleteById(empleadoId);
    }

    public long countEmpleados() {
        return empleadoRepository.count();
    }

    private void validateEmpleado(Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("El empleado no puede ser nulo");
        }

        if (empleado.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("El usuario del empleado es obligatorio");
        }

        if (empleado.getSucursalId() <= 0) {
            throw new IllegalArgumentException("La sucursal del empleado es obligatoria");
        }

        if (empleado.getPuesto() == null || empleado.getPuesto().trim().isEmpty()) {
            throw new IllegalArgumentException("El puesto del empleado es obligatorio");
        }

        if (empleado.getPuesto().length() > 50) {
            throw new IllegalArgumentException("El puesto no puede exceder 50 caracteres");
        }
    }
}