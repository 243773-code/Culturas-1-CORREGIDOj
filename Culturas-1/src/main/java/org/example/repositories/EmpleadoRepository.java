package org.example.repositories;


import org.example.config.DatabaseConfig;
import org.example.models.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoRepository {

    public List<Empleado> findAll() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT empleado_id, usuario_id, sucursal_id, puesto, fecha_contratacion FROM EMPLEADO";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                empleados.add(mapResultSetToEmpleado(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar empleados", e);
        }

        return empleados;
    }

    public Empleado findById(int empleadoId) {
        String sql = "SELECT empleado_id, usuario_id, sucursal_id, puesto, fecha_contratacion FROM EMPLEADO WHERE empleado_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, empleadoId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToEmpleado(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar empleado", e);
        }

        return null;
    }

    public Empleado findByUsuarioId(int usuarioId) {
        String sql = "SELECT empleado_id, usuario_id, sucursal_id, puesto, fecha_contratacion FROM EMPLEADO WHERE usuario_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToEmpleado(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar empleado por usuario", e);
        }

        return null;
    }

    public List<Empleado> findBySucursalId(int sucursalId) {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT empleado_id, usuario_id, sucursal_id, puesto, fecha_contratacion FROM EMPLEADO WHERE sucursal_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, sucursalId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                empleados.add(mapResultSetToEmpleado(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar empleados por sucursal", e);
        }

        return empleados;
    }

    public Empleado save(Empleado empleado) {
        String sql = "INSERT INTO EMPLEADO (usuario_id, sucursal_id, puesto, fecha_contratacion) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, empleado.getUsuarioId());
            pstmt.setInt(2, empleado.getSucursalId());
            pstmt.setString(3, empleado.getPuesto());
            pstmt.setDate(4, empleado.getFechaContratacion() != null ? Date.valueOf(empleado.getFechaContratacion()) : null);

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    empleado.setEmpleadoId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar empleado", e);
        }

        return empleado;
    }

    public Empleado update(Empleado empleado) {
        String sql = "UPDATE EMPLEADO SET usuario_id = ?, sucursal_id = ?, puesto = ?, fecha_contratacion = ? WHERE empleado_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, empleado.getUsuarioId());
            pstmt.setInt(2, empleado.getSucursalId());
            pstmt.setString(3, empleado.getPuesto());
            pstmt.setDate(4, empleado.getFechaContratacion() != null ? Date.valueOf(empleado.getFechaContratacion()) : null);
            pstmt.setInt(5, empleado.getEmpleadoId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar empleado", e);
        }

        return empleado;
    }

    public boolean deleteById(int empleadoId) {
        String sql = "DELETE FROM EMPLEADO WHERE empleado_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, empleadoId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar empleado", e);
        }
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM EMPLEADO";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar empleados", e);
        }

        return 0;
    }

    private Empleado mapResultSetToEmpleado(ResultSet rs) throws SQLException {
        return new Empleado(
                rs.getInt("empleado_id"),
                rs.getInt("usuario_id"),
                rs.getInt("sucursal_id"),
                rs.getString("puesto"),
                rs.getDate("fecha_contratacion") != null ? rs.getDate("fecha_contratacion").toLocalDate() : null
        );
    }
}
