package org.example.services;

import org.example.models.Envio;
import org.example.repositories.EnvioRepository;

import java.util.List;

public class EnvioService {

    private final EnvioRepository envioRepository;

    public EnvioService() {
        this.envioRepository = new EnvioRepository();
    }

    public List<Envio> getAllEnvios() {
        return envioRepository.findAll();
    }

    public Envio getEnvioById(int envioId) {
        if (envioId <= 0) {
            throw new IllegalArgumentException("El ID del envío debe ser mayor a 0");
        }

        Envio envio = envioRepository.findById(envioId);

        if (envio == null) {
            throw new RuntimeException("Envío no encontrado con ID: " + envioId);
        }

        return envio;
    }

    public Envio getEnvioByPedidoId(int pedidoId) {
        if (pedidoId <= 0) {
            throw new IllegalArgumentException("El ID del pedido debe ser mayor a 0");
        }

        Envio envio = envioRepository.findByPedidoId(pedidoId);

        if (envio == null) {
            throw new RuntimeException("Envío no encontrado para el pedido ID: " + pedidoId);
        }

        return envio;
    }

    public Envio createEnvio(Envio envio) {
        validateEnvio(envio);
        return envioRepository.save(envio);
    }

    public Envio updateEnvio(int envioId, Envio envio) {
        Envio existingEnvio = envioRepository.findById(envioId);
        if (existingEnvio == null) {
            throw new RuntimeException("Envío no encontrado con ID: " + envioId);
        }

        validateEnvio(envio);

        envio.setEnvioId(envioId);
        return envioRepository.update(envio);
    }

    public boolean deleteEnvio(int envioId) {
        if (envioId <= 0) {
            throw new IllegalArgumentException("El ID del envío debe ser mayor a 0");
        }

        Envio envio = envioRepository.findById(envioId);
        if (envio == null) {
            throw new RuntimeException("Envío no encontrado con ID: " + envioId);
        }

        return envioRepository.deleteById(envioId);
    }

    public long countEnvios() {
        return envioRepository.count();
    }

    private void validateEnvio(Envio envio) {
        if (envio == null) {
            throw new IllegalArgumentException("El envío no puede ser nulo");
        }

        if (envio.getPedidoId() <= 0) {
            throw new IllegalArgumentException("El pedido es obligatorio");
        }

        if (envio.getDireccionId() <= 0) {
            throw new IllegalArgumentException("La dirección es obligatoria");
        }

        if (envio.getEstatusId() <= 0) {
            throw new IllegalArgumentException("El estatus es obligatorio");
        }
    }
}