package org.example.models;

import java.time.LocalDate;

public class Empleado {
    private Integer empleadoId;
    private Integer usuarioId;
    private Integer sucursalId;
    private String puesto;
    private LocalDate fechaContratacion;

    // Constructor vacío
    public Empleado() {
    }

    // Constructor con todos los parámetros
    public Empleado(Integer empleadoId, Integer usuarioId, Integer sucursalId, String puesto, LocalDate fechaContratacion) {
        this.empleadoId = empleadoId;
        this.usuarioId = usuarioId;
        this.sucursalId = sucursalId;
        this.puesto = puesto;
        this.fechaContratacion = fechaContratacion;
    }

    // Constructor sin ID (para inserciones)
    public Empleado(Integer usuarioId, Integer sucursalId, String puesto, LocalDate fechaContratacion) {
        this.usuarioId = usuarioId;
        this.sucursalId = sucursalId;
        this.puesto = puesto;
        this.fechaContratacion = fechaContratacion;
    }


    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public Integer getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Integer sucursalId) {
        this.sucursalId = sucursalId;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "empleadoId=" + empleadoId +
                ", usuarioId=" + usuarioId +
                ", sucursalId=" + sucursalId +
                ", puesto='" + puesto + '\'' +
                ", fechaContratacion=" + fechaContratacion +
                '}';
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empleado that = (Empleado) o;
        return empleadoId != null && empleadoId.equals(that.empleadoId);
    }


    @Override
    public int hashCode() {
        return empleadoId != null ? empleadoId.hashCode() : 0;
    }

}