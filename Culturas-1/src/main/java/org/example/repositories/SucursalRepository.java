package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.Sucursal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SucursalRepository {

    public List<Sucursal> findAll() {
        List<Sucursal> sucursales = new ArrayList<>();
        String sql = "SELECT sucursal_id, nombre, direccion, ciudad, telefono FROM SUCURSAL";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                sucursales.add(mapResultSetToSucursal(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar sucursales", e);
        }

        return sucursales;
    }

    public Sucursal findById(int sucursalId) {
        String sql = "SELECT sucursal_id, nombre, direccion, ciudad, telefono FROM SUCURSAL WHERE sucursal_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, sucursalId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToSucursal(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar sucursal", e);
        }

        return null;
    }

    public List<Sucursal> findByCiudad(String ciudad) {
        List<Sucursal> sucursales = new ArrayList<>();
        String sql = "SELECT sucursal_id, nombre, direccion, ciudad, telefono FROM SUCURSAL WHERE ciudad = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ciudad);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                sucursales.add(mapResultSetToSucursal(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar sucursales por ciudad", e);
        }

        return sucursales;
    }

    public Sucursal save(Sucursal sucursal) {
        String sql = "INSERT INTO SUCURSAL (nombre, direccion, ciudad, telefono) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, sucursal.getNombre());
            pstmt.setString(2, sucursal.getDireccion());
            pstmt.setString(3, sucursal.getCiudad());
            pstmt.setString(4, sucursal.getTelefono());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    sucursal.setSucursalId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar sucursal", e);
        }

        return sucursal;
    }

    public Sucursal update(Sucursal sucursal) {
        String sql = "UPDATE SUCURSAL SET nombre = ?, direccion = ?, ciudad = ?, telefono = ? WHERE sucursal_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sucursal.getNombre());
            pstmt.setString(2, sucursal.getDireccion());
            pstmt.setString(3, sucursal.getCiudad());
            pstmt.setString(4, sucursal.getTelefono());
            pstmt.setInt(5, sucursal.getSucursalId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar sucursal", e);
        }

        return sucursal;
    }

    public boolean deleteById(int sucursalId) {
        String sql = "DELETE FROM SUCURSAL WHERE sucursal_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, sucursalId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar sucursal", e);
        }
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM SUCURSAL";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar sucursales", e);
        }

        return 0;
    }

    private Sucursal mapResultSetToSucursal(ResultSet rs) throws SQLException {
        return new Sucursal(
                rs.getInt("sucursal_id"),
                rs.getString("nombre"),
                rs.getString("direccion"),
                rs.getString("ciudad"),
                rs.getString("telefono")
        );
    }
}