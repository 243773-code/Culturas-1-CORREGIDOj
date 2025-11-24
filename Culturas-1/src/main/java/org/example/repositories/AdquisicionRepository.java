package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.Adquisicion;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdquisicionRepository {

    public List<Adquisicion> findAll() {
        List<Adquisicion> adquisiciones = new ArrayList<>();
        String sql = "SELECT adquisicion_id, proveedor_id, empleado_id, fecha, total, estatus_id FROM ADQUISICION ORDER BY fecha DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                adquisiciones.add(mapResultSetToAdquisicion(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar adquisiciones", e);
        }

        return adquisiciones;
    }

    public Adquisicion findById(int adquisicionId) {
        String sql = "SELECT adquisicion_id, proveedor_id, empleado_id, fecha, total, estatus_id FROM ADQUISICION WHERE adquisicion_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, adquisicionId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToAdquisicion(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar adquisición", e);
        }

        return null;
    }

    public List<Adquisicion> findByProveedorId(int proveedorId) {
        List<Adquisicion> adquisiciones = new ArrayList<>();
        String sql = "SELECT adquisicion_id, proveedor_id, empleado_id, fecha, total, estatus_id FROM ADQUISICION WHERE proveedor_id = ? ORDER BY fecha DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, proveedorId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                adquisiciones.add(mapResultSetToAdquisicion(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar adquisiciones por proveedor", e);
        }

        return adquisiciones;
    }

    public List<Adquisicion> findByEmpleadoId(int empleadoId) {
        List<Adquisicion> adquisiciones = new ArrayList<>();
        String sql = "SELECT adquisicion_id, proveedor_id, empleado_id, fecha, total, estatus_id FROM ADQUISICION WHERE empleado_id = ? ORDER BY fecha DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, empleadoId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                adquisiciones.add(mapResultSetToAdquisicion(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar adquisiciones por empleado", e);
        }

        return adquisiciones;
    }

    public Adquisicion save(Adquisicion adquisicion) {
        String sql = "INSERT INTO ADQUISICION (proveedor_id, empleado_id, fecha, total, estatus_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, adquisicion.getProveedorId());
            pstmt.setInt(2, adquisicion.getEmpleadoId());
            pstmt.setTimestamp(3, adquisicion.getFecha() != null ? Timestamp.valueOf(adquisicion.getFecha()) : Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setBigDecimal(4, adquisicion.getTotal());
            pstmt.setInt(5, adquisicion.getEstatusId());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    adquisicion.setAdquisicionId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar adquisición", e);
        }

        return adquisicion;
    }

    public Adquisicion update(Adquisicion adquisicion) {
        String sql = "UPDATE ADQUISICION SET proveedor_id = ?, empleado_id = ?, fecha = ?, total = ?, estatus_id = ? WHERE adquisicion_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, adquisicion.getProveedorId());
            pstmt.setInt(2, adquisicion.getEmpleadoId());
            pstmt.setTimestamp(3, adquisicion.getFecha() != null ? Timestamp.valueOf(adquisicion.getFecha()) : Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setBigDecimal(4, adquisicion.getTotal());
            pstmt.setInt(5, adquisicion.getEstatusId());
            pstmt.setInt(6, adquisicion.getAdquisicionId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar adquisición", e);
        }

        return adquisicion;
    }

    public boolean deleteById(int adquisicionId) {
        String sql = "DELETE FROM ADQUISICION WHERE adquisicion_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, adquisicionId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar adquisición", e);
        }
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM ADQUISICION";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar adquisiciones", e);
        }

        return 0;
    }

    private Adquisicion mapResultSetToAdquisicion(ResultSet rs) throws SQLException {
        return new Adquisicion(
                rs.getInt("adquisicion_id"),
                rs.getInt("proveedor_id"),
                rs.getInt("empleado_id"),
                rs.getTimestamp("fecha") != null ? rs.getTimestamp("fecha").toLocalDateTime() : null,
                rs.getBigDecimal("total"),
                rs.getInt("estatus_id")
        );
    }
}