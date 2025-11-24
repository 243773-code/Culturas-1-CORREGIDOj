package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.ImagenProducto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImagenProductoRepository {

    public List<ImagenProducto> findAll() {
        List<ImagenProducto> imagenes = new ArrayList<>();
        String sql = "SELECT imagen_id, producto_id, url FROM IMAGEN_PRODUCTO";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                imagenes.add(mapResultSetToImagenProducto(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar imágenes", e);
        }

        return imagenes;
    }

    public ImagenProducto findById(int imagenId) {
        String sql = "SELECT imagen_id, producto_id, url FROM IMAGEN_PRODUCTO WHERE imagen_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, imagenId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToImagenProducto(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar imagen", e);
        }

        return null;
    }

    public List<ImagenProducto> findByProductoId(int productoId) {
        List<ImagenProducto> imagenes = new ArrayList<>();
        String sql = "SELECT imagen_id, producto_id, url FROM IMAGEN_PRODUCTO WHERE producto_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productoId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                imagenes.add(mapResultSetToImagenProducto(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar imágenes por producto", e);
        }

        return imagenes;
    }

    public ImagenProducto save(ImagenProducto imagen) {
        String sql = "INSERT INTO IMAGEN_PRODUCTO (producto_id, url) VALUES (?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, imagen.getProductoId());
            pstmt.setString(2, imagen.getUrl());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    imagen.setImagenId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar imagen", e);
        }

        return imagen;
    }

    public ImagenProducto update(ImagenProducto imagen) {
        String sql = "UPDATE IMAGEN_PRODUCTO SET producto_id = ?, url = ? WHERE imagen_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, imagen.getProductoId());
            pstmt.setString(2, imagen.getUrl());
            pstmt.setInt(3, imagen.getImagenId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar imagen", e);
        }

        return imagen;
    }

    public boolean deleteById(int imagenId) {
        String sql = "DELETE FROM IMAGEN_PRODUCTO WHERE imagen_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, imagenId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar imagen", e);
        }
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM IMAGEN_PRODUCTO";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar imágenes", e);
        }

        return 0;
    }

    private ImagenProducto mapResultSetToImagenProducto(ResultSet rs) throws SQLException {
        return new ImagenProducto(
                rs.getInt("imagen_id"),
                rs.getInt("producto_id"),
                rs.getString("url")
        );
    }
}
