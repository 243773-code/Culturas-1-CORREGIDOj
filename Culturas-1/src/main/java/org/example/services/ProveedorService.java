package org.example.services;

import org.example.models.Proveedor;
import org.example.repositories.ProveedorRepository;

import java.util.List;

public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService() {
        this.proveedorRepository = new ProveedorRepository();
    }

    public List<Proveedor> getAllProveedores() {
        return proveedorRepository.findAll();
    }

    public Proveedor getProveedorById(int proveedorId) {
        if (proveedorId <= 0) {
            throw new IllegalArgumentException("El ID del proveedor debe ser mayor a 0");
        }

        Proveedor proveedor = proveedorRepository.findById(proveedorId);

        if (proveedor == null) {
            throw new RuntimeException("Proveedor no encontrado con ID: " + proveedorId);
        }

        return proveedor;
    }

    public Proveedor getProveedorByNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del proveedor no puede estar vacío");
        }

        Proveedor proveedor = proveedorRepository.findByNombre(nombre);

        if (proveedor == null) {
            throw new RuntimeException("Proveedor no encontrado con nombre: " + nombre);
        }

        return proveedor;
    }

    public Proveedor createProveedor(Proveedor proveedor) {
        validateProveedor(proveedor);

        if (proveedorRepository.existsByNombre(proveedor.getNombre())) {
            throw new RuntimeException("Ya existe un proveedor con el nombre: " + proveedor.getNombre());
        }

        return proveedorRepository.save(proveedor);
    }

    public Proveedor updateProveedor(int proveedorId, Proveedor proveedor) {
        Proveedor existingProveedor = proveedorRepository.findById(proveedorId);
        if (existingProveedor == null) {
            throw new RuntimeException("Proveedor no encontrado con ID: " + proveedorId);
        }

        validateProveedor(proveedor);

        Proveedor proveedorWithSameName = proveedorRepository.findByNombre(proveedor.getNombre());
        if (proveedorWithSameName != null && proveedorWithSameName.getProveedorId() != proveedorId) {
            throw new RuntimeException("Ya existe otro proveedor con el nombre: " + proveedor.getNombre());
        }

        proveedor.setProveedorId(proveedorId);
        return proveedorRepository.update(proveedor);
    }

    public boolean deleteProveedor(int proveedorId) {
        if (proveedorId <= 0) {
            throw new IllegalArgumentException("El ID del proveedor debe ser mayor a 0");
        }

        Proveedor proveedor = proveedorRepository.findById(proveedorId);
        if (proveedor == null) {
            throw new RuntimeException("Proveedor no encontrado con ID: " + proveedorId);
        }

        return proveedorRepository.deleteById(proveedorId);
    }

    public long countProveedores() {
        return proveedorRepository.count();
    }

    private void validateProveedor(Proveedor proveedor) {
        if (proveedor == null) {
            throw new IllegalArgumentException("El proveedor no puede ser nulo");
        }

        if (proveedor.getNombre() == null || proveedor.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del proveedor es obligatorio");
        }

        if (proveedor.getNombre().length() > 100) {
            throw new IllegalArgumentException("El nombre del proveedor no puede exceder 100 caracteres");
        }

        if (proveedor.getEmail() != null && !proveedor.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("El email del proveedor no es válido");
        }
    }
}
