package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.Pedido;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {

    public List<Pedido> findAll() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT pedido_id, usuario_id, direccion_id, fecha, total, metodo_pago, nota, estatus_id, tipo_entrega FROM PEDIDO ORDER BY fecha DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pedidos.add(mapResultSetToPedido(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar pedidos", e);
        }

        return pedidos;
    }

    public Pedido findById(int pedidoId) {
        String sql = "SELECT pedido_id, usuario_id, direccion_id, fecha, total, metodo_pago, nota, estatus_id, tipo_entrega FROM PEDIDO WHERE pedido_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pedidoId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToPedido(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar pedido", e);
        }

        return null;
    }

    public List<Pedido> findByUsuarioId(int usuarioId) {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT pedido_id, usuario_id, direccion_id, fecha, total, metodo_pago, nota, estatus_id, tipo_entrega FROM PEDIDO WHERE usuario_id = ? ORDER BY fecha DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                pedidos.add(mapResultSetToPedido(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar pedidos por usuario", e);
        }

        return pedidos;
    }

    public List<Pedido> findByEstatusId(int estatusId) {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT pedido_id, usuario_id, direccion_id, fecha, total, metodo_pago, nota, estatus_id, tipo_entrega FROM PEDIDO WHERE estatus_id = ? ORDER BY fecha DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, estatusId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                pedidos.add(mapResultSetToPedido(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar pedidos por estatus", e);
        }

        return pedidos;
    }

    public Pedido save(Pedido pedido) {
        String sql = "INSERT INTO PEDIDO (usuario_id, direccion_id, fecha, total, metodo_pago, nota, estatus_id, tipo_entrega) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, pedido.getUsuarioId());
            pstmt.setInt(2, pedido.getDireccionId());
            pstmt.setTimestamp(3, pedido.getFecha() != null ? Timestamp.valueOf(pedido.getFecha()) : Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setBigDecimal(4, pedido.getTotal());
            pstmt.setString(5, pedido.getMetodoPago());
            pstmt.setString(6, pedido.getNota());
            pstmt.setInt(7, pedido.getEstatusId());
            pstmt.setString(8, pedido.getTipoEntrega());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pedido.setPedidoId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar pedido", e);
        }

        return pedido;
    }

    public Pedido update(Pedido pedido) {
        String sql = "UPDATE PEDIDO SET usuario_id = ?, direccion_id = ?, fecha = ?, total = ?, metodo_pago = ?, nota = ?, estatus_id = ?, tipo_entrega = ? WHERE pedido_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pedido.getUsuarioId());
            pstmt.setInt(2, pedido.getDireccionId());
            pstmt.setTimestamp(3, pedido.getFecha() != null ? Timestamp.valueOf(pedido.getFecha()) : Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setBigDecimal(4, pedido.getTotal());
            pstmt.setString(5, pedido.getMetodoPago());
            pstmt.setString(6, pedido.getNota());
            pstmt.setInt(7, pedido.getEstatusId());
            pstmt.setString(8, pedido.getTipoEntrega());
            pstmt.setInt(9, pedido.getPedidoId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar pedido", e);
        }

        return pedido;
    }

    public boolean deleteById(int pedidoId) {
        String sql = "DELETE FROM PEDIDO WHERE pedido_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pedidoId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar pedido", e);
        }
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM PEDIDO";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar pedidos", e);
        }

        return 0;
    }

    private Pedido mapResultSetToPedido(ResultSet rs) throws SQLException {
        return new Pedido(
                rs.getInt("pedido_id"),
                rs.getInt("usuario_id"),
                rs.getInt("direccion_id"),
                rs.getTimestamp("fecha") != null ? rs.getTimestamp("fecha").toLocalDateTime() : null,
                rs.getBigDecimal("total"),
                rs.getString("metodo_pago"),
                rs.getString("nota"),
                rs.getInt("estatus_id"),
                rs.getString("tipo_entrega")
        );
    }
}