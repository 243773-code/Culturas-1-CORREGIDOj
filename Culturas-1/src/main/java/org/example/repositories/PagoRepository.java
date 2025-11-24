package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.Pago;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PagoRepository {

    public List<Pago> findAll() {
        List<Pago> pagos = new ArrayList<>();
        String sql = "SELECT pago_id, pedido_id, metodo_pago, monto, fecha, estatus_id FROM PAGO ORDER BY fecha DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pagos.add(mapResultSetToPago(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar pagos", e);
        }

        return pagos;
    }

    public Pago findById(int pagoId) {
        String sql = "SELECT pago_id, pedido_id, metodo_pago, monto, fecha, estatus_id FROM PAGO WHERE pago_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pagoId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToPago(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar pago", e);
        }

        return null;
    }

    public List<Pago> findByPedidoId(int pedidoId) {
        List<Pago> pagos = new ArrayList<>();
        String sql = "SELECT pago_id, pedido_id, metodo_pago, monto, fecha, estatus_id FROM PAGO WHERE pedido_id = ? ORDER BY fecha DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pedidoId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                pagos.add(mapResultSetToPago(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar pagos por pedido", e);
        }

        return pagos;
    }

    public Pago save(Pago pago) {
        String sql = "INSERT INTO PAGO (pedido_id, metodo_pago, monto, fecha, estatus_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, pago.getPedidoId());
            pstmt.setString(2, pago.getMetodoPago());
            pstmt.setBigDecimal(3, pago.getMonto());
            pstmt.setTimestamp(4, pago.getFecha() != null ? Timestamp.valueOf(pago.getFecha()) : Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setInt(5, pago.getEstatusId());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pago.setPagoId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar pago", e);
        }

        return pago;
    }

    public Pago update(Pago pago) {
        String sql = "UPDATE PAGO SET pedido_id = ?, metodo_pago = ?, monto = ?, fecha = ?, estatus_id = ? WHERE pago_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pago.getPedidoId());
            pstmt.setString(2, pago.getMetodoPago());
            pstmt.setBigDecimal(3, pago.getMonto());
            pstmt.setTimestamp(4, pago.getFecha() != null ? Timestamp.valueOf(pago.getFecha()) : Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setInt(5, pago.getEstatusId());
            pstmt.setInt(6, pago.getPagoId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar pago", e);
        }

        return pago;
    }

    public boolean deleteById(int pagoId) {
        String sql = "DELETE FROM PAGO WHERE pago_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pagoId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar pago", e);
        }
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM PAGO";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar pagos", e);
        }

        return 0;
    }

    private Pago mapResultSetToPago(ResultSet rs) throws SQLException {

        Pago p = new Pago();

        // pago_id
        p.setPagoId(rs.getInt("pago_id"));

        // pedido_id
        p.setPedidoId(rs.getInt("pedido_id"));

        // fecha → convertir timestamp a LocalDateTime
        Timestamp tsFecha = rs.getTimestamp("fecha");
        if (tsFecha != null) {
            p.setFecha(tsFecha.toLocalDateTime());
        }

        // monto
        p.setMonto(rs.getBigDecimal("monto"));

        // metodo_pago → en tu modelo se llama "metodo"
        p.setMetodo(rs.getString("metodo_pago"));

        // estatus_id
        p.setEstatusId(rs.getInt("estatus_id"));

        return p;
    }
}