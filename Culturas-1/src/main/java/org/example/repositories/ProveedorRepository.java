package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.Proveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorRepository {

    public List<Proveedor> findAll() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT proveedor_id, nombre, contacto, telefono, email, direccion FROM PROVEEDOR";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                proveedores.add(mapResultSetToProveedor(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar proveedores", e);
        }

        return proveedores;
    }

    public Proveedor findById(int proveedorId) {
        String sql = "SELECT proveedor_id, nombre, contacto, telefono, email, direccion FROM PROVEEDOR WHERE proveedor_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, proveedorId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToProveedor(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar proveedor", e);
        }

        return null;
    }

    public Proveedor findByNombre(String nombre) {
        String sql = "SELECT proveedor_id, nombre, contacto, telefono, email, direccion FROM PROVEEDOR WHERE nombre = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToProveedor(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar proveedor", e);
        }

        return null;
    }

    public Proveedor save(Proveedor proveedor) {
        String sql = "INSERT INTO PROVEEDOR (nombre, contacto, telefono, email, direccion) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, proveedor.getNombre());
            pstmt.setString(2, proveedor.getContacto());
            pstmt.setString(3, proveedor.getTelefono());
            pstmt.setString(4, proveedor.getEmail());
            pstmt.setString(5, proveedor.getDireccion());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    proveedor.setProveedorId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar proveedor", e);
        }

        return proveedor;
    }

    public Proveedor update(Proveedor proveedor) {
        String sql = "UPDATE PROVEEDOR SET nombre = ?, contacto = ?, telefono = ?, email = ?, direccion = ? WHERE proveedor_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, proveedor.getNombre());
            pstmt.setString(2, proveedor.getContacto());
            pstmt.setString(3, proveedor.getTelefono());
            pstmt.setString(4, proveedor.getEmail());
            pstmt.setString(5, proveedor.getDireccion());
            pstmt.setInt(6, proveedor.getProveedorId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar proveedor", e);
        }

        return proveedor;
    }

    public boolean deleteById(int proveedorId) {
        String sql = "DELETE FROM PROVEEDOR WHERE proveedor_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, proveedorId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar proveedor", e);
        }
    }

    public boolean existsByNombre(String nombre) {
        String sql = "SELECT COUNT(*) FROM PROVEEDOR WHERE nombre = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar proveedor", e);
        }

        return false;
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM PROVEEDOR";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar proveedores", e);
        }

        return 0;
    }

    private Proveedor mapResultSetToProveedor(ResultSet rs) throws SQLException {
        return new Proveedor(
                rs.getInt("proveedor_id"),
                rs.getString("nombre"),
                rs.getString("contacto"),
                rs.getString("telefono"),
                rs.getString("email"),
                rs.getString("direccion")
        );
    }
}