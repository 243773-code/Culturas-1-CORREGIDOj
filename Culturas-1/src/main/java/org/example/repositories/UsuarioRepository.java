package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.Usuario;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    public List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT usuario_id, rol_id, nombre, apellido, email, password, telefono, fecha_registro FROM USUARIO";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usuarios.add(mapResultSetToUsuario(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuarios", e);
        }

        return usuarios;
    }

    public Usuario findById(int usuarioId) {
        String sql = "SELECT usuario_id, rol_id, nombre, apellido, email, password, telefono, fecha_registro FROM USUARIO WHERE usuario_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUsuario(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario", e);
        }

        return null;
    }

    public Usuario findByEmail(String email) {
        String sql = "SELECT usuario_id, rol_id, nombre, apellido, email, password, telefono, fecha_registro FROM USUARIO WHERE email = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUsuario(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por email", e);
        }

        return null;
    }

    public List<Usuario> findByRolId(int rolId) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT usuario_id, rol_id, nombre, apellido, email, password, telefono, fecha_registro FROM USUARIO WHERE rol_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, rolId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                usuarios.add(mapResultSetToUsuario(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuarios por rol", e);
        }

        return usuarios;
    }

    public Usuario save(Usuario usuario) {
        String sql = "INSERT INTO USUARIO (rol_id, nombre, apellido, email, password, telefono, fecha_registro) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, usuario.getRolId());
            pstmt.setString(2, usuario.getNombre());
            pstmt.setString(3, usuario.getApellido());
            pstmt.setString(4, usuario.getEmail());
            pstmt.setString(5, usuario.getPassword());
            pstmt.setString(6, usuario.getTelefono());

            Timestamp timestamp = usuario.getFechaRegistro() != null ?
                    Timestamp.valueOf(usuario.getFechaRegistro()) :
                    Timestamp.valueOf(LocalDateTime.now());
            pstmt.setTimestamp(7, timestamp);

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setUsuarioId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar usuario", e);
        }

        return usuario;
    }

    public Usuario update(Usuario usuario) {
        String sql = "UPDATE USUARIO SET rol_id = ?, nombre = ?, apellido = ?, email = ?, password = ?, telefono = ? WHERE usuario_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuario.getRolId());
            pstmt.setString(2, usuario.getNombre());
            pstmt.setString(3, usuario.getApellido());
            pstmt.setString(4, usuario.getEmail());
            pstmt.setString(5, usuario.getPassword());
            pstmt.setString(6, usuario.getTelefono());
            pstmt.setInt(7, usuario.getUsuarioId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario", e);
        }

        return usuario;
    }

    public boolean deleteById(int usuarioId) {
        String sql = "DELETE FROM USUARIO WHERE usuario_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar usuario", e);
        }
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM USUARIO WHERE email = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar email", e);
        }

        return false;
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM USUARIO";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar usuarios", e);
        }

        return 0;
    }

    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("usuario_id"),
                rs.getInt("rol_id"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("telefono"),
                rs.getTimestamp("fecha_registro") != null ? rs.getTimestamp("fecha_registro").toLocalDateTime() : null
        );
    }
}