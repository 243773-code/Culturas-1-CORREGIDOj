package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.Producto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepository {

    public List<Producto> findAll() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT producto_id, categoria_id, estatus_id, nombre, descripcion, sku, " +
                "precio_base, fecha_creacion, stock_total, stock_minimo, punto_reorden, " +
                "ultima_actualizacion, img_url " +
                "FROM PRODUCTO";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                productos.add(mapResultSetToProducto(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar productos", e);
        }

        return productos;
    }

    public Producto findById(int productoId) {
        String sql = "SELECT producto_id, categoria_id, estatus_id, nombre, descripcion, sku, " +
                "precio_base, fecha_creacion, stock_total, stock_minimo, punto_reorden, " +
                "ultima_actualizacion, img_url " +
                "FROM PRODUCTO WHERE producto_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productoId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToProducto(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar producto", e);
        }

        return null;
    }

    public Producto findBySku(String sku) {
        String sql = "SELECT producto_id, categoria_id, estatus_id, nombre, descripcion, sku, " +
                "precio_base, fecha_creacion, stock_total, stock_minimo, punto_reorden, " +
                "ultima_actualizacion, img_url " +
                "FROM PRODUCTO WHERE sku = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sku);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToProducto(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar producto por SKU", e);
        }

        return null;
    }

    public List<Producto> findByCategoriaId(int categoriaId) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT producto_id, categoria_id, estatus_id, nombre, descripcion, sku, " +
                "precio_base, fecha_creacion, stock_total, stock_minimo, punto_reorden, " +
                "ultima_actualizacion, img_url " +
                "FROM PRODUCTO WHERE categoria_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, categoriaId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                productos.add(mapResultSetToProducto(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar productos por categoría", e);
        }

        return productos;
    }

    public Producto save(Producto producto) {
        String sql = "INSERT INTO PRODUCTO (" +
                "categoria_id, estatus_id, nombre, descripcion, sku, precio_base, " +
                "fecha_creacion, stock_total, stock_minimo, punto_reorden, " +
                "ultima_actualizacion, img_url" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, producto.getCategoriaId());
            pstmt.setInt(2, producto.getEstatusId());
            pstmt.setString(3, producto.getNombre());
            pstmt.setString(4, producto.getDescripcion());
            pstmt.setString(5, producto.getSku());
            pstmt.setBigDecimal(6, producto.getPrecioBase());
            pstmt.setTimestamp(7,
                    producto.getFechaCreacion() != null
                            ? Timestamp.valueOf(producto.getFechaCreacion())
                            : Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setInt(8, producto.getStockTotal());
            pstmt.setInt(9, producto.getStockMinimo());
            pstmt.setInt(10, producto.getPuntoReorden());
            pstmt.setTimestamp(11,
                    producto.getUltimaActualizacion() != null
                            ? Timestamp.valueOf(producto.getUltimaActualizacion())
                            : Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(12, producto.getImg_url());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    producto.setProductoId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar producto", e);
        }

        return producto;
    }

    public Producto update(Producto producto) {
        String sql = "UPDATE PRODUCTO SET " +
                "categoria_id = ?, estatus_id = ?, nombre = ?, descripcion = ?, sku = ?, " +
                "precio_base = ?, stock_total = ?, stock_minimo = ?, punto_reorden = ?, " +
                "ultima_actualizacion = ?, img_url = ? " +
                "WHERE producto_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, producto.getCategoriaId());
            pstmt.setInt(2, producto.getEstatusId());
            pstmt.setString(3, producto.getNombre());
            pstmt.setString(4, producto.getDescripcion());
            pstmt.setString(5, producto.getSku());
            pstmt.setBigDecimal(6, producto.getPrecioBase());
            pstmt.setInt(7, producto.getStockTotal());
            pstmt.setInt(8, producto.getStockMinimo());
            pstmt.setInt(9, producto.getPuntoReorden());
            pstmt.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(11, producto.getImg_url());
            pstmt.setInt(12, producto.getProductoId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar producto", e);
        }

        return producto;
    }

    public boolean deleteById(int productoId) {
        String sql = "DELETE FROM PRODUCTO WHERE producto_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productoId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar producto", e);
        }
    }

    public boolean existsBySku(String sku) {
        String sql = "SELECT COUNT(*) FROM PRODUCTO WHERE sku = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sku);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar SKU", e);
        }

        return false;
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM PRODUCTO";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar productos", e);
        }

        return 0;
    }

    private Producto mapResultSetToProducto(ResultSet rs) throws SQLException {
        return new Producto(
                rs.getInt("producto_id"),
                rs.getInt("categoria_id"),
                rs.getInt("estatus_id"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getString("sku"),
                rs.getBigDecimal("precio_base"),
                rs.getTimestamp("fecha_creacion") != null
                        ? rs.getTimestamp("fecha_creacion").toLocalDateTime()
                        : null,
                rs.getInt("stock_total"),
                rs.getInt("stock_minimo"),
                rs.getInt("punto_reorden"),
                rs.getTimestamp("ultima_actualizacion") != null
                        ? rs.getTimestamp("ultima_actualizacion").toLocalDateTime()
                        : null,
                rs.getString("img_url")
        );
    }
}