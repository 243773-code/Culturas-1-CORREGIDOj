package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.DetallePedido;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ⚠️ IMPORTANTE: Esta clase asume que la tabla DETALLE_PEDIDO tiene el campo producto_id
 *
 * Si tu compañero NO lo ha agregado, debe ejecutar:
 *
 * ALTER TABLE DETALLE_PEDIDO
 * ADD COLUMN producto_id INT NOT NULL AFTER pedido_id,
 * ADD CONSTRAINT fk_detallepedido_producto
 *   FOREIGN KEY (producto_id) REFERENCES PRODUCTO(producto_id);
 */
public class DetallePedidoRepository {

    public List<DetallePedido> findAll() {
        List<DetallePedido> detalles = new ArrayList<>();
        // ⚠️ Incluye producto_id
        String sql = "SELECT detalle_pedido_id, pedido_id, producto_id, producto_talla_id, cantidad, precio_unitario, subtotal FROM DETALLE_PEDIDO";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                detalles.add(mapResultSetToDetallePedido(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar detalles de pedido", e);
        }

        return detalles;
    }

    public DetallePedido findById(int detallePedidoId) {
        String sql = "SELECT detalle_pedido_id, pedido_id, producto_id, producto_talla_id, cantidad, precio_unitario, subtotal FROM DETALLE_PEDIDO WHERE detalle_pedido_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, detallePedidoId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToDetallePedido(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar detalle de pedido", e);
        }

        return null;
    }

    public List<DetallePedido> findByPedidoId(int pedidoId) {
        List<DetallePedido> detalles = new ArrayList<>();
        String sql = "SELECT detalle_pedido_id, pedido_id, producto_id, producto_talla_id, cantidad, precio_unitario, subtotal FROM DETALLE_PEDIDO WHERE pedido_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pedidoId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                detalles.add(mapResultSetToDetallePedido(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar detalles por pedido", e);
        }

        return detalles;
    }

    public DetallePedido save(DetallePedido detalle) {
        // ⚠️ Incluye producto_id en INSERT
        String sql = "INSERT INTO DETALLE_PEDIDO (pedido_id, producto_id, producto_talla_id, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, detalle.getPedidoId());
            pstmt.setInt(3, detalle.getProductoTallaId());
            pstmt.setInt(4, detalle.getCantidad());
            pstmt.setBigDecimal(5, detalle.getPrecioUnitario());
            pstmt.setBigDecimal(6, detalle.getSubtotal());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    detalle.setDetallePedidoId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar detalle de pedido. ⚠️ Verifica que la tabla tenga el campo producto_id", e);
        }

        return detalle;
    }

    public DetallePedido update(DetallePedido detalle) {
        String sql = "UPDATE DETALLE_PEDIDO SET pedido_id = ?, producto_id = ?, producto_talla_id = ?, cantidad = ?, precio_unitario = ?, subtotal = ? WHERE detalle_pedido_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, detalle.getPedidoId());
            pstmt.setInt(3, detalle.getProductoTallaId());
            pstmt.setInt(4, detalle.getCantidad());
            pstmt.setBigDecimal(5, detalle.getPrecioUnitario());
            pstmt.setBigDecimal(6, detalle.getSubtotal());
            pstmt.setInt(7, detalle.getDetallePedidoId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar detalle de pedido", e);
        }

        return detalle;
    }

    public boolean deleteById(int detallePedidoId) {
        String sql = "DELETE FROM DETALLE_PEDIDO WHERE detalle_pedido_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, detallePedidoId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar detalle de pedido", e);
        }
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM DETALLE_PEDIDO";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar detalles de pedido", e);
        }

        return 0;
    }

    private DetallePedido mapResultSetToDetallePedido(ResultSet rs) throws SQLException {
        return new DetallePedido(
                rs.getInt("detalle_pedido_id"),
                rs.getInt("pedido_id"),
                rs.getInt("producto_talla_id"),
                rs.getInt("cantidad"),
                rs.getBigDecimal("precio_unitario"),
                rs.getBigDecimal("subtotal")
        );
    }
}