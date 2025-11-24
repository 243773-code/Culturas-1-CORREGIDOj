package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.HistorialPrecio;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HistorialPrecioRepository {

    public List<HistorialPrecio> findAll() {
        List<HistorialPrecio> historial = new ArrayList<>();
        String sql = "SELECT historial_id, producto_id, precio_anterior, precio_nuevo, fecha_cambio FROM HISTORIAL_PRECIO ORDER BY fecha_cambio DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                historial.add(mapResultSetToHistorialPrecio(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar historial de precios", e);
        }

        return historial;
    }

    public HistorialPrecio findById(int historialId) {
        String sql = "SELECT historial_id, producto_id, precio_anterior, precio_nuevo, fecha_cambio FROM HISTORIAL_PRECIO WHERE historial_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, historialId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToHistorialPrecio(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar historial de precio", e);
        }

        return null;
    }

    public List<HistorialPrecio> findByProductoId(int productoId) {
        List<HistorialPrecio> historial = new ArrayList<>();
        String sql = "SELECT historial_id, producto_id, precio_anterior, precio_nuevo, fecha_cambio FROM HISTORIAL_PRECIO WHERE producto_id = ? ORDER BY fecha_cambio DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productoId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                historial.add(mapResultSetToHistorialPrecio(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar historial de precios por producto", e);
        }

        return historial;
    }

    public HistorialPrecio save(HistorialPrecio historialPrecio) {
        String sql = "INSERT INTO HISTORIAL_PRECIO (producto_id, precio_anterior, precio_nuevo, fecha_cambio) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, historialPrecio.getProductoId());
            pstmt.setBigDecimal(2, historialPrecio.getPrecioAnterior());
            pstmt.setBigDecimal(3, historialPrecio.getPrecioNuevo());
            pstmt.setTimestamp(4, historialPrecio.getFechaCambio() != null ? Timestamp.valueOf(historialPrecio.getFechaCambio()) : Timestamp.valueOf(LocalDateTime.now()));

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    historialPrecio.setHistorialId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar historial de precio", e);
        }

        return historialPrecio;
    }

    public HistorialPrecio update(HistorialPrecio historialPrecio) {
        String sql = "UPDATE HISTORIAL_PRECIO SET producto_id = ?, precio_anterior = ?, precio_nuevo = ?, fecha_cambio = ? WHERE historial_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, historialPrecio.getProductoId());
            pstmt.setBigDecimal(2, historialPrecio.getPrecioAnterior());
            pstmt.setBigDecimal(3, historialPrecio.getPrecioNuevo());
            pstmt.setTimestamp(4, historialPrecio.getFechaCambio() != null ? Timestamp.valueOf(historialPrecio.getFechaCambio()) : Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setInt(5, historialPrecio.getHistorialId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar historial de precio", e);
        }

        return historialPrecio;
    }

    public boolean deleteById(int historialId) {
        String sql = "DELETE FROM HISTORIAL_PRECIO WHERE historial_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, historialId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar historial de precio", e);
        }
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM HISTORIAL_PRECIO";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar historial de precios", e);
        }

        return 0;
    }

    private HistorialPrecio mapResultSetToHistorialPrecio(ResultSet rs) throws SQLException {
        HistorialPrecio h = new HistorialPrecio();

        h.setHistorialId(rs.getInt("historial_id"));
        h.setProductoId(rs.getInt("producto_id"));

        h.setPrecioAnterior(rs.getBigDecimal("precio_anterior"));
        h.setPrecioNuevo(rs.getBigDecimal("precio_nuevo"));

        Timestamp tsCambio = rs.getTimestamp("fecha_cambio");
        if (tsCambio != null) {
            h.setFechaCambio(tsCambio.toLocalDateTime());
        }
        return h;
    }
}