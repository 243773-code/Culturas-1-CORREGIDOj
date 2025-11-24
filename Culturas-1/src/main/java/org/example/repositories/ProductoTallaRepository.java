package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.ProductoTalla;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductoTallaRepository {

    public List<ProductoTalla> findAll() {
        List<ProductoTalla> productoTallas = new ArrayList<>();
        String sql = "SELECT producto_talla_id, producto_id, talla_id, cantidad, updated_at FROM PRODUCTO_TALLA";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                productoTallas.add(mapResultSetToProductoTalla(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar producto-tallas", e);
        }

        return productoTallas;
    }

    public ProductoTalla findById(int productoTallaId) {
        String sql = "SELECT producto_talla_id, producto_id, talla_id, cantidad, updated_at FROM PRODUCTO_TALLA WHERE producto_talla_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productoTallaId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToProductoTalla(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar producto-talla", e);
        }

        return null;
    }

    public List<ProductoTalla> findByProductoId(int productoId) {
        List<ProductoTalla> productoTallas = new ArrayList<>();
        String sql = "SELECT producto_talla_id, producto_id, talla_id, cantidad, updated_at FROM PRODUCTO_TALLA WHERE producto_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productoId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                productoTallas.add(mapResultSetToProductoTalla(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar producto-tallas por producto", e);
        }

        return productoTallas;
    }

    public ProductoTalla save(ProductoTalla productoTalla) {
        String sql = "INSERT INTO PRODUCTO_TALLA (producto_id, talla_id, cantidad, updated_at) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, productoTalla.getProductoId());
            pstmt.setInt(2, productoTalla.getTallaId());
            pstmt.setInt(3, productoTalla.getCantidad());
            pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    productoTalla.setProductoTallaId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar producto-talla", e);
        }

        return productoTalla;
    }

    public ProductoTalla update(ProductoTalla productoTalla) {
        String sql = "UPDATE PRODUCTO_TALLA SET producto_id = ?, talla_id = ?, cantidad = ?, updated_at = ? WHERE producto_talla_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productoTalla.getProductoId());
            pstmt.setInt(2, productoTalla.getTallaId());
            pstmt.setInt(3, productoTalla.getCantidad());
            pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setInt(5, productoTalla.getProductoTallaId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar producto-talla", e);
        }

        return productoTalla;
    }

    public boolean deleteById(int productoTallaId) {
        String sql = "DELETE FROM PRODUCTO_TALLA WHERE producto_talla_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productoTallaId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar producto-talla", e);
        }
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM PRODUCTO_TALLA";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar producto-tallas", e);
        }

        return 0;
    }

    private ProductoTalla mapResultSetToProductoTalla(ResultSet rs) throws SQLException {
        return new ProductoTalla(
                rs.getInt("producto_talla_id"),
                rs.getInt("producto_id"),
                rs.getInt("talla_id"),
                rs.getInt("cantidad"),
                rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null
        );
    }
}