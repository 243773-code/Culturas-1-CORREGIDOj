package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.Talla;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TallaRepository {

    public List<Talla> findAll() {
        List<Talla> tallas = new ArrayList<>();
        String sql = "SELECT talla_id, codigo, nombre, orden FROM TALLA ORDER BY orden";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Talla talla = new Talla(
                        rs.getInt("talla_id"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getInt("orden")
                );
                tallas.add(talla);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar tallas", e);
        }

        return tallas;
    }

    public Talla findById(int tallaId) {
        String sql = "SELECT talla_id, codigo, nombre, orden FROM TALLA WHERE talla_id = ?";
        Talla talla = null;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, tallaId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                talla = new Talla(
                        rs.getInt("talla_id"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getInt("orden")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar talla", e);
        }

        return talla;
    }

    public Talla findByCodigo(String codigo) {
        String sql = "SELECT talla_id, codigo, nombre, orden FROM TALLA WHERE codigo = ?";
        Talla talla = null;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                talla = new Talla(
                        rs.getInt("talla_id"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getInt("orden")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar talla", e);
        }

        return talla;
    }

    public Talla save(Talla talla) {
        String sql = "INSERT INTO TALLA (codigo, nombre, orden) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, talla.getCodigo());
            pstmt.setString(2, talla.getNombre());
            pstmt.setInt(3, talla.getOrden());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    talla.setTallaId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar talla", e);
        }

        return talla;
    }

    public Talla update(Talla talla) {
        String sql = "UPDATE TALLA SET codigo = ?, nombre = ?, orden = ? WHERE talla_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, talla.getCodigo());
            pstmt.setString(2, talla.getNombre());
            pstmt.setInt(3, talla.getOrden());
            pstmt.setInt(4, talla.getTallaId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar talla", e);
        }

        return talla;
    }

    public boolean deleteById(int tallaId) {
        String sql = "DELETE FROM TALLA WHERE talla_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, tallaId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar talla", e);
        }
    }

    public boolean existsByCodigo(String codigo) {
        String sql = "SELECT COUNT(*) FROM TALLA WHERE codigo = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar talla", e);
        }

        return false;
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM TALLA";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar tallas", e);
        }

        return 0;
    }
}