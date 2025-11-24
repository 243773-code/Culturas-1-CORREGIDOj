package org.example.services;

import org.example.models.Rol;
import org.example.repositories.RolRepository;

import java.util.List;

/**
 * RolService - Capa de lógica de negocio para ROL
 * Valida reglas de negocio antes de acceder a la base de datos
 */
public class RolService {

    private final RolRepository rolRepository;

    public RolService() {
        this.rolRepository = new RolRepository();
    }

    /**
     * Obtener todos los roles
     */
    public List<Rol> getAllRoles() {
        return rolRepository.findAll();
    }

    /**
     * Obtener rol por ID
     */
    public Rol getRolById(int rolId) {
        if (rolId <= 0) {
            throw new IllegalArgumentException("El ID del rol debe ser mayor a 0");
        }

        Rol rol = rolRepository.findById(rolId);

        if (rol == null) {
            throw new RuntimeException("Rol no encontrado con ID: " + rolId);
        }

        return rol;
    }

    /**
     * Obtener rol por nombre
     */
    public Rol getRolByNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del rol no puede estar vacío");
        }

        Rol rol = rolRepository.findByNombre(nombre);

        if (rol == null) {
            throw new RuntimeException("Rol no encontrado con nombre: " + nombre);
        }

        return rol;
    }

    /**
     * Crear nuevo rol
     */
    public Rol createRol(Rol rol) {
        // Validaciones
        validateRol(rol);

        // Verificar que no exista un rol con el mismo nombre
        if (rolRepository.existsByNombre(rol.getNombre())) {
            throw new RuntimeException("Ya existe un rol con el nombre: " + rol.getNombre());
        }

        return rolRepository.save(rol);
    }

    /**
     * Actualizar rol existente
     */
    public Rol updateRol(int rolId, Rol rol) {
        // Validar que el rol exista
        Rol existingRol = rolRepository.findById(rolId);
        if (existingRol == null) {
            throw new RuntimeException("Rol no encontrado con ID: " + rolId);
        }

        // Validaciones
        validateRol(rol);

        // Verificar que no exista otro rol con el mismo nombre
        Rol rolWithSameName = rolRepository.findByNombre(rol.getNombre());
        if (rolWithSameName != null && rolWithSameName.getRolId() != rolId) {
            throw new RuntimeException("Ya existe otro rol con el nombre: " + rol.getNombre());
        }

        rol.setRolId(rolId);
        return rolRepository.update(rol);
    }

    /**
     * Eliminar rol por ID
     */
    public boolean deleteRol(int rolId) {
        if (rolId <= 0) {
            throw new IllegalArgumentException("El ID del rol debe ser mayor a 0");
        }

        // Verificar que el rol exista
        Rol rol = rolRepository.findById(rolId);
        if (rol == null) {
            throw new RuntimeException("Rol no encontrado con ID: " + rolId);
        }

        return rolRepository.deleteById(rolId);
    }

    /**
     * Verificar si existe un rol por nombre
     */
    public boolean existsByNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        return rolRepository.existsByNombre(nombre);
    }

    /**
     * Contar total de roles
     */
    public long countRoles() {
        return rolRepository.count();
    }

    /**
     * Validar datos del rol
     */
    private void validateRol(Rol rol) {
        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede ser nulo");
        }

        if (rol.getNombre() == null || rol.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del rol es obligatorio");
        }

        if (rol.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre del rol no puede exceder 50 caracteres");
        }

        if (rol.getDescripcion() != null && rol.getDescripcion().length() > 255) {
            throw new IllegalArgumentException("La descripción del rol no puede exceder 255 caracteres");
        }
    }
}