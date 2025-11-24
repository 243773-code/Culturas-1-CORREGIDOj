package org.example.services;

import org.example.models.Sucursal;
import org.example.repositories.SucursalRepository;

import java.util.List;

public class SucursalService {

    private final SucursalRepository sucursalRepository;

    public SucursalService() {
        this.sucursalRepository = new SucursalRepository();
    }

    public List<Sucursal> getAllSucursales() {
        return sucursalRepository.findAll();
    }

    public Sucursal getSucursalById(int sucursalId) {
        if (sucursalId <= 0) {
            throw new IllegalArgumentException("El ID de la sucursal debe ser mayor a 0");
        }

        Sucursal sucursal = sucursalRepository.findById(sucursalId);

        if (sucursal == null) {
            throw new RuntimeException("Sucursal no encontrada con ID: " + sucursalId);
        }

        return sucursal;
    }

    public List<Sucursal> getSucursalesByCiudad(String ciudad) {
        if (ciudad == null || ciudad.trim().isEmpty()) {
            throw new IllegalArgumentException("La ciudad no puede estar vacía");
        }

        return sucursalRepository.findByCiudad(ciudad);
    }

    public Sucursal createSucursal(Sucursal sucursal) {
        validateSucursal(sucursal);
        return sucursalRepository.save(sucursal);
    }

    public Sucursal updateSucursal(int sucursalId, Sucursal sucursal) {
        Sucursal existingSucursal = sucursalRepository.findById(sucursalId);
        if (existingSucursal == null) {
            throw new RuntimeException("Sucursal no encontrada con ID: " + sucursalId);
        }

        validateSucursal(sucursal);

        sucursal.setSucursalId(sucursalId);
        return sucursalRepository.update(sucursal);
    }

    public boolean deleteSucursal(int sucursalId) {
        if (sucursalId <= 0) {
            throw new IllegalArgumentException("El ID de la sucursal debe ser mayor a 0");
        }

        Sucursal sucursal = sucursalRepository.findById(sucursalId);
        if (sucursal == null) {
            throw new RuntimeException("Sucursal no encontrada con ID: " + sucursalId);
        }

        return sucursalRepository.deleteById(sucursalId);
    }

    public long countSucursales() {
        return sucursalRepository.count();
    }

    private void validateSucursal(Sucursal sucursal) {
        if (sucursal == null) {
            throw new IllegalArgumentException("La sucursal no puede ser nula");
        }

        if (sucursal.getNombre() == null || sucursal.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la sucursal es obligatorio");
        }

        if (sucursal.getNombre().length() > 100) {
            throw new IllegalArgumentException("El nombre de la sucursal no puede exceder 100 caracteres");
        }
    }
}



