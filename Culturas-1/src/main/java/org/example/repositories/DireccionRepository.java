package org.example.repositories;

import org.example.config.DatabaseConfig;
import org.example.models.Direccion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DireccionRepository {

    public List<Direccion> findAll() {
        List<Direccion> direcciones = new ArrayList<>();
        String sql = "SELECT direccion_id, usuario_id, calle, numero, colonia, ciudad, estado, codigo_postal, pais, tipo FROM DIRECCION";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                direcciones.add(mapResultSetToDireccion(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar direcciones", e);
        }

        return direcciones;
    }

    public Direccion findById(int direccionId) {
        String sql = "SELECT direccion_id, usuario_id, calle, numero, colonia, ciudad, estado, codigo_postal, pais, tipo FROM DIRECCION WHERE direccion_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, direccionId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToDireccion(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar dirección", e);
        }

        return null;
    }

    public List<Direccion> findByUsuarioId(int usuarioId) {
        List<Direccion> direcciones = new ArrayList<>();
        String sql = "SELECT direccion_id, usuario_id, calle, numero, colonia, ciudad, estado, codigo_postal, pais, tipo FROM DIRECCION WHERE usuario_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                direcciones.add(mapResultSetToDireccion(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar direcciones por usuario", e);
        }

        return direcciones;
    }

    public Direccion save(Direccion direccion) {
        String sql = "INSERT INTO DIRECCION (usuario_id, calle, numero, colonia, ciudad, estado, codigo_postal, pais, tipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, direccion.getUsuarioId());
            pstmt.setString(2, direccion.getCalle());
            pstmt.setString(3, direccion.getNumero());
            pstmt.setString(4, direccion.getColonia());
            pstmt.setString(5, direccion.getCiudad());
            pstmt.setString(6, direccion.getEstado());
            pstmt.setString(7, direccion.getCodigoPostal());
            pstmt.setString(8, direccion.getPais());
            pstmt.setString(9, direccion.getTipo());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    direccion.setDireccionId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar dirección", e);
        }

        return direccion;
    }

    public Direccion update(Direccion direccion) {
        String sql = "UPDATE DIRECCION SET usuario_id = ?, calle = ?, numero = ?, colonia = ?, ciudad = ?, estado = ?, codigo_postal = ?, pais = ?, tipo = ? WHERE direccion_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, direccion.getUsuarioId());
            pstmt.setString(2, direccion.getCalle());
            pstmt.setString(3, direccion.getNumero());
            pstmt.setString(4, direccion.getColonia());
            pstmt.setString(5, direccion.getCiudad());
            pstmt.setString(6, direccion.getEstado());
            pstmt.setString(7, direccion.getCodigoPostal());
            pstmt.setString(8, direccion.getPais());
            pstmt.setString(9, direccion.getTipo());
            pstmt.setInt(10, direccion.getDireccionId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar dirección", e);
        }

        return direccion;
    }

    public boolean deleteById(int direccionId) {
        String sql = "DELETE FROM DIRECCION WHERE direccion_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, direccionId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar dirección", e);
        }
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM DIRECCION";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar direcciones", e);
        }

        return 0;
    }

    private Direccion mapResultSetToDireccion(ResultSet rs) throws SQLException {
        return new Direccion(
                rs.getInt("direccion_id"),
                rs.getInt("usuario_id"),
                rs.getString("calle"),
                rs.getString("numero"),
                rs.getString("colonia"),
                rs.getString("ciudad"),
                rs.getString("estado"),
                rs.getString("codigo_postal"),
                rs.getString("pais"),
                rs.getString("tipo")
        );
    }
}