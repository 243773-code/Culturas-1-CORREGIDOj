package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.Estatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstatusRepository {

    public List<Estatus> findAll() {
        List<Estatus> estatuses = new ArrayList<>();
        String sql = "SELECT estatus_id, nombre, descripcion, tabla, orden FROM ESTATUS ORDER BY orden";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                estatuses.add(mapResultSetToEstatus(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar estatus", e);
        }

        return estatuses;
    }

    public Estatus findById(int estatusId) {
        String sql = "SELECT estatus_id, nombre, descripcion, tabla, orden FROM ESTATUS WHERE estatus_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, estatusId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToEstatus(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar estatus", e);
        }

        return null;
    }

    public Estatus findByNombre(String nombre) {
        String sql = "SELECT estatus_id, nombre, descripcion, tabla, orden FROM ESTATUS WHERE nombre = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToEstatus(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar estatus", e);
        }

        return null;
    }

    public List<Estatus> findByTabla(String tabla) {
        List<Estatus> estatuses = new ArrayList<>();
        String sql = "SELECT estatus_id, nombre, descripcion, tabla, orden FROM ESTATUS WHERE tabla = ? ORDER BY orden";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tabla);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                estatuses.add(mapResultSetToEstatus(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar estatus por tabla", e);
        }

        return estatuses;
    }

    public Estatus save(Estatus estatus) {
        String sql = "INSERT INTO ESTATUS (nombre, descripcion, tabla, orden) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, estatus.getNombre());
            pstmt.setString(2, estatus.getDescripcion());
            pstmt.setString(3, estatus.getTabla());
            pstmt.setInt(4, estatus.getOrden());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    estatus.setEstatusId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar estatus", e);
        }

        return estatus;
    }

    public Estatus update(Estatus estatus) {
        String sql = "UPDATE ESTATUS SET nombre = ?, descripcion = ?, tabla = ?, orden = ? WHERE estatus_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, estatus.getNombre());
            pstmt.setString(2, estatus.getDescripcion());
            pstmt.setString(3, estatus.getTabla());
            pstmt.setInt(4, estatus.getOrden());
            pstmt.setInt(5, estatus.getEstatusId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar estatus", e);
        }

        return estatus;
    }

    public boolean deleteById(int estatusId) {
        String sql = "DELETE FROM ESTATUS WHERE estatus_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, estatusId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar estatus", e);
        }
    }

    public boolean existsByNombre(String nombre) {
        String sql = "SELECT COUNT(*) FROM ESTATUS WHERE nombre = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar estatus", e);
        }

        return false;
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM ESTATUS";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar estatus", e);
        }

        return 0;
    }

    private Estatus mapResultSetToEstatus(ResultSet rs) throws SQLException {
        return new Estatus(
                rs.getInt("estatus_id"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getString("tabla"),
                rs.getInt("orden")
        );
    }
}