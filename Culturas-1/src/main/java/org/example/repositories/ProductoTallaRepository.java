package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.ProductoTalla;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductoTallaRepository {

    private ProductoTalla mapRow(ResultSet rs) throws SQLException {
        ProductoTalla pt = new ProductoTalla();
        pt.setProductoTallaId(rs.getInt("producto_talla_id"));
        pt.setProductoId(rs.getInt("producto_id"));
        pt.setTallaId(rs.getInt("talla_id"));
        pt.setCantidad(rs.getInt("cantidad"));

        Timestamp ts = rs.getTimestamp("updated_at");
        if (ts != null) {
            pt.setUpdatedAt(ts.toLocalDateTime());
        }

        return pt;
    }

    public List<ProductoTalla> findAll() {
        String sql = "SELECT producto_talla_id, producto_id, talla_id, cantidad, updated_at " +
                "FROM PRODUCTO_TALLA";
        List<ProductoTalla> result = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                result.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener producto_tallas", e);
        }

        return result;
    }

    public ProductoTalla findById(int id) {
        String sql = "SELECT producto_talla_id, producto_id, talla_id, cantidad, updated_at " +
                "FROM PRODUCTO_TALLA WHERE producto_talla_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar producto_talla", e);
        }
    }

    public ProductoTalla save(ProductoTalla pt) {
        String sql = "INSERT INTO PRODUCTO_TALLA (producto_id, talla_id, cantidad, updated_at) " +
                "VALUES (?, ?, ?, NOW())";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, pt.getProductoId());
            pstmt.setInt(2, pt.getTallaId());
            pstmt.setInt(3, pt.getCantidad());

            pstmt.executeUpdate();

            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    pt.setProductoTallaId(keys.getInt(1));
                }
            }

            pt.setUpdatedAt(LocalDateTime.now());
            return pt;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar producto_talla", e);
        }
    }

    public ProductoTalla update(ProductoTalla pt) {
        String sql = "UPDATE PRODUCTO_TALLA " +
                "SET producto_id = ?, talla_id = ?, cantidad = ?, updated_at = NOW() " +
                "WHERE producto_talla_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pt.getProductoId());
            pstmt.setInt(2, pt.getTallaId());
            pstmt.setInt(3, pt.getCantidad());
            pstmt.setInt(4, pt.getProductoTallaId());

            pstmt.executeUpdate();
            pt.setUpdatedAt(LocalDateTime.now());
            return pt;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar producto_talla", e);
        }
    }

    public boolean updateCantidad(int id, int cantidad) {
        String sql = "UPDATE PRODUCTO_TALLA " +
                "SET cantidad = ?, updated_at = NOW() " +
                "WHERE producto_talla_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cantidad);
            pstmt.setInt(2, id);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar cantidad de producto_talla", e);
        }
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM PRODUCTO_TALLA WHERE producto_talla_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar producto_talla", e);
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
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar producto_tallas", e);
        }
    }
}
