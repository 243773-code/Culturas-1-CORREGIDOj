package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.Envio;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EnvioRepository {

    public List<Envio> findAll() {
        List<Envio> envios = new ArrayList<>();
        String sql = "SELECT envio_id, pedido_id, direccion_id, fecha_envio, " +
                "fecha_estimada, fecha_entrega, estatus_id " +
                "FROM ENVIO ORDER BY fecha_envio DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                envios.add(mapResultSetToEnvio(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar envíos", e);
        }

        return envios;
    }

    public Envio findById(int envioId) {
        String sql = "SELECT envio_id, pedido_id, direccion_id, fecha_envio, " +
                "fecha_estimada, fecha_entrega, estatus_id " +
                "FROM ENVIO WHERE envio_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, envioId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEnvio(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar envío", e);
        }

        return null;
    }

    public Envio findByPedidoId(int pedidoId) {
        String sql = "SELECT envio_id, pedido_id, direccion_id, fecha_envio, " +
                "fecha_estimada, fecha_entrega, estatus_id " +
                "FROM ENVIO WHERE pedido_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pedidoId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEnvio(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar envío por pedido", e);
        }

        return null;
    }

    // ======================
    // INSERT
    // ======================
    public Envio save(Envio envio) {
        String sql = "INSERT INTO ENVIO " +
                "(pedido_id, direccion_id, fecha_envio, fecha_estimada, fecha_entrega, estatus_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // 1) pedido_id
            pstmt.setInt(1, envio.getPedidoId());

            // 2) direccion_id
            pstmt.setInt(2, envio.getDireccionId());

            // 3) fecha_envio (si viene null usamos ahora)
            LocalDateTime fechaEnvio = envio.getFechaEnvio() != null
                    ? envio.getFechaEnvio()
                    : LocalDateTime.now();
            pstmt.setTimestamp(3, Timestamp.valueOf(fechaEnvio));

            // 4) fecha_estimada (puede ser null)
            if (envio.getFechaEstimada() != null) {
                pstmt.setTimestamp(4, Timestamp.valueOf(envio.getFechaEstimada()));
            } else {
                pstmt.setTimestamp(4, null);
            }

            // 5) fecha_entrega (puede ser null)
            if (envio.getFechaEntrega() != null) {
                pstmt.setTimestamp(5, Timestamp.valueOf(envio.getFechaEntrega()));
            } else {
                pstmt.setTimestamp(5, null);
            }

            // 6) estatus_id
            pstmt.setInt(6, envio.getEstatusId());

            pstmt.executeUpdate();

            // recuperar ID generado
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    envio.setEnvioId(generatedKeys.getInt(1));
                }
            }

            return envio;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar envío", e);
        }
    }

    // ======================
    // UPDATE
    // ======================
    public Envio update(Envio envio) {
        String sql = "UPDATE ENVIO " +
                "SET pedido_id = ?, direccion_id = ?, fecha_envio = ?, " +
                "fecha_estimada = ?, fecha_entrega = ?, estatus_id = ? " +
                "WHERE envio_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 1) pedido_id
            pstmt.setInt(1, envio.getPedidoId());

            // 2) direccion_id
            pstmt.setInt(2, envio.getDireccionId());

            // 3) fecha_envio (si viene null usamos ahora)
            LocalDateTime fechaEnvio = envio.getFechaEnvio() != null
                    ? envio.getFechaEnvio()
                    : LocalDateTime.now();
            pstmt.setTimestamp(3, Timestamp.valueOf(fechaEnvio));

            // 4) fecha_estimada
            if (envio.getFechaEstimada() != null) {
                pstmt.setTimestamp(4, Timestamp.valueOf(envio.getFechaEstimada()));
            } else {
                pstmt.setTimestamp(4, null);
            }

            // 5) fecha_entrega
            if (envio.getFechaEntrega() != null) {
                pstmt.setTimestamp(5, Timestamp.valueOf(envio.getFechaEntrega()));
            } else {
                pstmt.setTimestamp(5, null);
            }

            // 6) estatus_id
            pstmt.setInt(6, envio.getEstatusId());

            // 7) envio_id
            pstmt.setInt(7, envio.getEnvioId());

            pstmt.executeUpdate();

            return envio;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar envío", e);
        }
    }

    // ======================
    // DELETE
    // ======================
    public boolean deleteById(int envioId) {
        String sql = "DELETE FROM ENVIO WHERE envio_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, envioId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar envío", e);
        }
    }

    // ======================
    // COUNT
    // ======================
    public long count() {
        String sql = "SELECT COUNT(*) FROM ENVIO";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar envíos", e);
        }

        return 0;
    }

    // ======================
    // MAPEADOR ResultSet -> Envio
    // ======================
    private Envio mapResultSetToEnvio(ResultSet rs) throws SQLException {

        Timestamp tsEnvio     = rs.getTimestamp("fecha_envio");
        Timestamp tsEstimada  = rs.getTimestamp("fecha_estimada");
        Timestamp tsEntrega   = rs.getTimestamp("fecha_entrega");

        LocalDateTime fechaEnvio    = tsEnvio != null    ? tsEnvio.toLocalDateTime()    : null;
        LocalDateTime fechaEstimada = tsEstimada != null ? tsEstimada.toLocalDateTime() : null;
        LocalDateTime fechaEntrega  = tsEntrega != null  ? tsEntrega.toLocalDateTime()  : null;

        return new Envio(
                rs.getInt("envio_id"),
                rs.getInt("pedido_id"),
                rs.getInt("direccion_id"),
                fechaEnvio,
                fechaEstimada,
                fechaEntrega,
                rs.getInt("estatus_id")
        );
    }
}