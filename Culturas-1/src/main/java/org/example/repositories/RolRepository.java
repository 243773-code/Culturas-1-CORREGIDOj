package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.Rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * RolRepository - Capa de acceso a datos para ROL
 * Operaciones CRUD usando JDBC puro
 */
public class RolRepository {

    /**
     * Buscar todos los roles
     */
    public List<Rol> findAll() {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT rol_id, nombre, descripcion FROM ROL ORDER BY rol_id";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Rol rol = new Rol(
                        rs.getInt("rol_id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
                roles.add(rol);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar roles: " + e.getMessage());
            throw new RuntimeException("Error al buscar roles", e);
        }

        return roles;
    }

    /**
     * Buscar rol por ID
     */
    public Rol findById(int rolId) {
        String sql = "SELECT rol_id, nombre, descripcion FROM ROL WHERE rol_id = ?";
        Rol rol = null;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, rolId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                rol = new Rol(
                        rs.getInt("rol_id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar rol por ID: " + e.getMessage());
            throw new RuntimeException("Error al buscar rol", e);
        }

        return rol;
    }

    /**
     * Buscar rol por nombre
     */
    public Rol findByNombre(String nombre) {
        String sql = "SELECT rol_id, nombre, descripcion FROM ROL WHERE nombre = ?";
        Rol rol = null;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                rol = new Rol(
                        rs.getInt("rol_id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar rol por nombre: " + e.getMessage());
            throw new RuntimeException("Error al buscar rol", e);
        }

        return rol;
    }

    /**
     * Crear nuevo rol
     */
    public Rol save(Rol rol) {
        String sql = "INSERT INTO ROL (nombre, descripcion) VALUES (?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, rol.getNombre());
            pstmt.setString(2, rol.getDescripcion());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear rol, ninguna fila afectada");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    rol.setRolId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Error al crear rol, no se obtuvo el ID");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al guardar rol: " + e.getMessage());
            throw new RuntimeException("Error al guardar rol", e);
        }

        return rol;
    }

    /**
     * Actualizar rol existente
     */
    public Rol update(Rol rol) {
        String sql = "UPDATE ROL SET nombre = ?, descripcion = ? WHERE rol_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, rol.getNombre());
            pstmt.setString(2, rol.getDescripcion());
            pstmt.setInt(3, rol.getRolId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al actualizar rol, no existe el ID: " + rol.getRolId());
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar rol: " + e.getMessage());
            throw new RuntimeException("Error al actualizar rol", e);
        }

        return rol;
    }

    /**
     * Eliminar rol por ID
     */
    public boolean deleteById(int rolId) {
        String sql = "DELETE FROM ROL WHERE rol_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, rolId);
            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar rol: " + e.getMessage());
            throw new RuntimeException("Error al eliminar rol", e);
        }
    }

    /**
     * Verificar si existe un rol por nombre
     */
    public boolean existsByNombre(String nombre) {
        String sql = "SELECT COUNT(*) FROM ROL WHERE nombre = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de rol: " + e.getMessage());
            throw new RuntimeException("Error al verificar rol", e);
        }

        return false;
    }

    /**
     * Contar total de roles
     */
    public long count() {
        String sql = "SELECT COUNT(*) FROM ROL";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            System.err.println("Error al contar roles: " + e.getMessage());
            throw new RuntimeException("Error al contar roles", e);
        }

        return 0;
    }
}