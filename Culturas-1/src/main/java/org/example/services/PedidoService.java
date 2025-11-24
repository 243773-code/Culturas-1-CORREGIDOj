package org.example.services;

import org.example.models.Pedido;
import org.example.repositories.PedidoRepository;

import java.math.BigDecimal;
import java.util.List;

public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService() {
        this.pedidoRepository = new PedidoRepository();
    }

    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido getPedidoById(int pedidoId) {
        if (pedidoId <= 0) {
            throw new IllegalArgumentException("El ID del pedido debe ser mayor a 0");
        }

        Pedido pedido = pedidoRepository.findById(pedidoId);

        if (pedido == null) {
            throw new RuntimeException("Pedido no encontrado con ID: " + pedidoId);
        }

        return pedido;
    }

    public List<Pedido> getPedidosByUsuarioId(int usuarioId) {
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("El ID del usuario debe ser mayor a 0");
        }

        return pedidoRepository.findByUsuarioId(usuarioId);
    }

    public List<Pedido> getPedidosByEstatusId(int estatusId) {
        if (estatusId <= 0) {
            throw new IllegalArgumentException("El ID del estatus debe ser mayor a 0");
        }

        return pedidoRepository.findByEstatusId(estatusId);
    }

    public Pedido createPedido(Pedido pedido) {
        validatePedido(pedido);
        return pedidoRepository.save(pedido);
    }

    public Pedido updatePedido(int pedidoId, Pedido pedido) {
        Pedido existingPedido = pedidoRepository.findById(pedidoId);
        if (existingPedido == null) {
            throw new RuntimeException("Pedido no encontrado con ID: " + pedidoId);
        }

        validatePedido(pedido);

        pedido.setPedidoId(pedidoId);
        return pedidoRepository.update(pedido);
    }

    public boolean deletePedido(int pedidoId) {
        if (pedidoId <= 0) {
            throw new IllegalArgumentException("El ID del pedido debe ser mayor a 0");
        }

        Pedido pedido = pedidoRepository.findById(pedidoId);
        if (pedido == null) {
            throw new RuntimeException("Pedido no encontrado con ID: " + pedidoId);
        }

        return pedidoRepository.deleteById(pedidoId);
    }

    public long countPedidos() {
        return pedidoRepository.count();
    }

    private void validatePedido(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo");
        }

        if (pedido.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }

        if (pedido.getDireccionId() <= 0) {
            throw new IllegalArgumentException("La dirección es obligatoria");
        }

        if (pedido.getTotal() == null || pedido.getTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El total debe ser mayor o igual a 0");
        }

        if (pedido.getEstatusId() <= 0) {
            throw new IllegalArgumentException("El estatus es obligatorio");
        }

        if (pedido.getTipoEntrega() == null || pedido.getTipoEntrega().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de entrega es obligatorio");
        }
    }
}