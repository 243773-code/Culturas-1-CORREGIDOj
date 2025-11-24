package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CategoriaRepository - Capa de acceso a datos para CATEGORIA
 */
public class CategoriaRepository {

    public List<Categoria> findAll() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT categoria_id, nombre, descripcion FROM CATEGORIA ORDER BY categoria_id";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getInt("categoria_id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
                categorias.add(categoria);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar categorías", e);
        }

        return categorias;
    }

    public Categoria findById(int categoriaId) {
        String sql = "SELECT categoria_id, nombre, descripcion FROM CATEGORIA WHERE categoria_id = ?";
        Categoria categoria = null;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, categoriaId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                categoria = new Categoria(
                        rs.getInt("categoria_id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar categoría", e);
        }

        return categoria;
    }

    public Categoria findByNombre(String nombre) {
        String sql = "SELECT categoria_id, nombre, descripcion FROM CATEGORIA WHERE nombre = ?";
        Categoria categoria = null;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                categoria = new Categoria(
                        rs.getInt("categoria_id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar categoría", e);
        }

        return categoria;
    }

    public Categoria save(Categoria categoria) {
        String sql = "INSERT INTO CATEGORIA (nombre, descripcion) VALUES (?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, categoria.getNombre());
            pstmt.setString(2, categoria.getDescripcion());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    categoria.setCategoriaId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar categoría", e);
        }

        return categoria;
    }

    public Categoria update(Categoria categoria) {
        String sql = "UPDATE CATEGORIA SET nombre = ?, descripcion = ? WHERE categoria_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, categoria.getNombre());
            pstmt.setString(2, categoria.getDescripcion());
            pstmt.setInt(3, categoria.getCategoriaId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar categoría", e);
        }

        return categoria;
    }

    public boolean deleteById(int categoriaId) {
        String sql = "DELETE FROM CATEGORIA WHERE categoria_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, categoriaId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar categoría", e);
        }
    }

    public boolean existsByNombre(String nombre) {
        String sql = "SELECT COUNT(*) FROM CATEGORIA WHERE nombre = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar categoría", e);
        }

        return false;
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM CATEGORIA";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar categorías", e);
        }

        return 0;
    }
}