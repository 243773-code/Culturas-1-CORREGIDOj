package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.DetalleAdquisicion;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleAdquisicionRepository {

    public List<DetalleAdquisicion> findAll() {
        List<DetalleAdquisicion> detalles = new ArrayList<>();
        String sql = "SELECT detalle_id, adquisicion_id, producto_talla_id, cantidad, precio_unitario, subtotal FROM DETALLE_ADQUISICION";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                detalles.add(mapResultSetToDetalleAdquisicion(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar detalles de adquisición", e);
        }

        return detalles;
    }

    public DetalleAdquisicion findById(int detalleId) {
        String sql = "SELECT detalle_id, adquisicion_id, producto_talla_id, cantidad, precio_unitario, subtotal FROM DETALLE_ADQUISICION WHERE detalle_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, detalleId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToDetalleAdquisicion(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar detalle de adquisición", e);
        }

        return null;
    }

    public List<DetalleAdquisicion> findByAdquisicionId(int adquisicionId) {
        List<DetalleAdquisicion> detalles = new ArrayList<>();
        String sql = "SELECT detalle_id, adquisicion_id, producto_talla_id, cantidad, precio_unitario, subtotal FROM DETALLE_ADQUISICION WHERE adquisicion_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, adquisicionId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                detalles.add(mapResultSetToDetalleAdquisicion(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar detalles por adquisición", e);
        }

        return detalles;
    }

    public DetalleAdquisicion save(DetalleAdquisicion detalle) {
        String sql = "INSERT INTO DETALLE_ADQUISICION (adquisicion_id, producto_talla_id, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, detalle.getAdquisicionId());
            pstmt.setInt(2, detalle.getProductoTallaId());
            pstmt.setInt(3, detalle.getCantidad());
            pstmt.setBigDecimal(4, detalle.getPrecioUnitario());
            pstmt.setBigDecimal(5, detalle.getSubtotal());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    detalle.setDetalleId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar detalle de adquisición", e);
        }

        return detalle;
    }

    public DetalleAdquisicion update(DetalleAdquisicion detalle) {
        String sql = "UPDATE DETALLE_ADQUISICION SET adquisicion_id = ?, producto_talla_id = ?, cantidad = ?, precio_unitario = ?, subtotal = ? WHERE detalle_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, detalle.getAdquisicionId());
            pstmt.setInt(2, detalle.getProductoTallaId());
            pstmt.setInt(3, detalle.getCantidad());
            pstmt.setBigDecimal(4, detalle.getPrecioUnitario());
            pstmt.setBigDecimal(5, detalle.getSubtotal());
            pstmt.setInt(6, detalle.getDetalleId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar detalle de adquisición", e);
        }

        return detalle;
    }

    public boolean deleteById(int detalleId) {
        String sql = "DELETE FROM DETALLE_ADQUISICION WHERE detalle_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, detalleId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar detalle de adquisición", e);
        }
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM DETALLE_ADQUISICION";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar detalles de adquisición", e);
        }

        return 0;
    }

    private DetalleAdquisicion mapResultSetToDetalleAdquisicion(ResultSet rs) throws SQLException {
        return new DetalleAdquisicion(
                rs.getInt("detalle_id"),
                rs.getInt("adquisicion_id"),
                rs.getInt("producto_talla_id"),
                rs.getInt("cantidad"),
                rs.getBigDecimal("precio_unitario"),
                rs.getBigDecimal("subtotal")
        );
    }
}